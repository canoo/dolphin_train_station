package com.canoo.codecamp.dolphinpi

import groovyx.gpars.dataflow.DataflowQueue
import org.opendolphin.core.PresentationModel
import org.opendolphin.core.Tag
import org.opendolphin.core.comm.Command
import org.opendolphin.core.comm.ValueChangedCommand
import org.opendolphin.core.server.*
import org.opendolphin.core.server.action.DolphinServerAction
import org.opendolphin.core.server.comm.ActionRegistry
import org.opendolphin.core.server.comm.CommandHandler
import org.opendolphin.core.server.comm.SimpleCommandHandler

import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.DEPARTURE_TIME
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.DESTINATION
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.POSITION
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.STATUS
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.STOPOVERS
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.TRACK
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.TRAIN_NUMBER
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.getALL
import static com.canoo.codecamp.dolphinpi.DepartureConstants.CMD.INIT_SELECTED_DEPARTURE
import static com.canoo.codecamp.dolphinpi.DepartureConstants.CMD.MOVE_TO_TOP
import static com.canoo.codecamp.dolphinpi.DepartureConstants.CMD.PULL
import static com.canoo.codecamp.dolphinpi.DepartureConstants.CMD.REDO
import static com.canoo.codecamp.dolphinpi.DepartureConstants.CMD.UNDO
import static com.canoo.codecamp.dolphinpi.DepartureConstants.SPECIAL_ID.DEPARTURES
import static com.canoo.codecamp.dolphinpi.DepartureConstants.SPECIAL_ID.SELECTED_DEPARTURE
import static com.canoo.codecamp.dolphinpi.DepartureConstants.STATUS.APPROACHING
import static com.canoo.codecamp.dolphinpi.DepartureConstants.STATUS.HAS_LEFT
import static com.canoo.codecamp.dolphinpi.DepartureConstants.TYPE.DEPARTURE
import static com.canoo.codecamp.dolphinpi.DepartureConstants.pmId
import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.ATT.TOP_DEPARTURE_ON_BOARD
import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.TYPE.PRESENTATION_STATE

@SuppressWarnings("GroovyAssignabilityCheck")
class AdminActions extends DolphinServerAction {

    private final DataflowQueue valueQueue
    private final Deque<ValueChangedCommand> undoStack = new ArrayDeque<>();
    private final Deque<ValueChangedCommand> redoStack = new ArrayDeque<>();
    private final List<Integer> positionsOnBoard = [-1, -1, -1, -1, -1]
    ResourceBundle bundle = ResourceBundle.getBundle("BoardResources")

    // needed for proper undo/redo handling
    private ValueChangedCommand nextTripleToIgnore

    //all available Actions / CommandHandlers
    private final CommandHandler initSelectedDepartureAction = new SimpleCommandHandler() {
        @Override
        void handleCommand() {
            initAt SELECTED_DEPARTURE, DEPARTURE_TIME, null, '[0-9][0-9]:[0-9][0-9]', Tag.REGEX
            initAt SELECTED_DEPARTURE, DESTINATION, null, '.*', Tag.REGEX
            initAt SELECTED_DEPARTURE, TRAIN_NUMBER, null, '[A-Z]{2,3} [0-9]{1,4}', Tag.REGEX
            initAt SELECTED_DEPARTURE, TRACK, null, '[0-9]{0,2}', Tag.REGEX
            initAt SELECTED_DEPARTURE, STOPOVERS, null, '.*', Tag.REGEX

            initAt SELECTED_DEPARTURE, DEPARTURE_TIME, null, bundle.getString("DEPARTURE_TIME"), Tag.LABEL
            initAt SELECTED_DEPARTURE, DESTINATION, null, bundle.getString("DESTINATION"), Tag.LABEL
            initAt SELECTED_DEPARTURE, TRAIN_NUMBER, null, bundle.getString("TRAIN_NUMBER"), Tag.LABEL
            initAt SELECTED_DEPARTURE, TRACK, null, bundle.getString("TRACK"), Tag.LABEL
            initAt SELECTED_DEPARTURE, STOPOVERS, null, bundle.getString("STOPOVERS"), Tag.LABEL
            initAt SELECTED_DEPARTURE, STATUS, null, bundle.getString("STATUS"), Tag.LABEL

        }
    }

    private final CommandHandler getAllDeparturesAction = new SimpleCommandHandler() {
        @Override
        void handleCommand() {
            loadDepartureDTOs().eachWithIndex { dto, index ->
                presentationModel pmId(DEPARTURE, index), DEPARTURE, dto
            }
        }
    }

    private final CommandHandler moveToTopAction = new SimpleCommandHandler() {
        @Override
        void handleCommand() {
            updatePositionsOnBoard(getServerDolphin()[SELECTED_DEPARTURE][POSITION].value as int, 0)
            sendDepartureBoardEntries(0..4)
            changeValue getServerDolphin()[PRESENTATION_STATE][TOP_DEPARTURE_ON_BOARD], positionsOnBoard[0]
        }
    }

    private final CommandHandler undoAction = new SimpleCommandHandler() {
        @Override
        void handleCommand() {
            if (undoStack.isEmpty()) {
                return
            }
            ValueChangedCommand cmd = undoStack.pop()
            nextTripleToIgnore = new ValueChangedCommand(attributeId: cmd.attributeId, oldValue: cmd.newValue, newValue: cmd.oldValue)
            redoStack.push(cmd)
            changeValue(getServerDolphin().serverModelStore.findAttributeById(cmd.attributeId) as ServerAttribute, cmd.oldValue)
        }
    }

    private final CommandHandler redoAction = new SimpleCommandHandler() {
        @Override
        void handleCommand() {
            if (redoStack.isEmpty()) {
                return
            }
            ValueChangedCommand valueChangedCommand = redoStack.pop()
            nextTripleToIgnore = new ValueChangedCommand(attributeId: valueChangedCommand.attributeId, oldValue: valueChangedCommand.oldValue, newValue: valueChangedCommand.newValue)
            undoStack.push(valueChangedCommand)
            changeValue(getServerDolphin().serverModelStore.findAttributeById(valueChangedCommand.attributeId) as ServerAttribute, valueChangedCommand.newValue)
        }
    }

    private final CommandHandler undoRedoStackAction = new CommandHandler<ValueChangedCommand>() {

        @Override
        public void handleCommand(final ValueChangedCommand command, final List<Command> response) {
            PresentationModel selectedPM = getServerDolphin()[SELECTED_DEPARTURE]
            if (selectedPM && !selectedPM.findAttributeById(command.attributeId)) {
                if (hasToBeIgnored(nextTripleToIgnore, command)) {
                    nextTripleToIgnore = null
                } else {
                    undoStack.push(command)
                    redoStack.clear()
                }
            }
        }
    }

    private final CommandHandler pushBoardDeparturesOnQueueAction = new CommandHandler<ValueChangedCommand>() {

        @Override
        public void handleCommand(final ValueChangedCommand command, final List<Command> response) {
            int positionOnTopOfBoard = positionsOnBoard[0]
            if (positionOnTopOfBoard == -1) return

            def changedAttribute = getServerDolphin().serverModelStore.findAttributeById(command.attributeId)
            if (!changedAttribute?.qualifier?.startsWith(DEPARTURE)) return
            if (changedAttribute.tag != Tag.VALUE) return

            String pmId = pmIdFromQualifier(changedAttribute.qualifier)

            ServerPresentationModel modifiedPm = getServerDolphin()[pmId]
            int modifiedPmPosition = modifiedPm[POSITION].value as int

            if (modifiedPmPosition in positionsOnBoard) {
                if (changedAttribute.propertyName == STATUS && command.newValue == HAS_LEFT) {
                    int startOnBoard = positionsOnBoard.indexOf(modifiedPmPosition)
                    updatePositionsOnBoard(modifiedPmPosition, startOnBoard)
                    sendDepartureBoardEntries(startOnBoard..4)
                } else {
                    final toUpdate = positionsOnBoard.indexOf(modifiedPmPosition)
                    sendDepartureBoardEntries(toUpdate..toUpdate)
                }
                changeValue getServerDolphin()[PRESENTATION_STATE][TOP_DEPARTURE_ON_BOARD], positionsOnBoard[0]
            }
        }
    }

    AdminActions(DataflowQueue valueQueue) {
        this.valueQueue = valueQueue
    }

    public void registerIn(ActionRegistry registry) {
        registry.register(INIT_SELECTED_DEPARTURE, initSelectedDepartureAction)
        registry.register(PULL, getAllDeparturesAction)
        registry.register(MOVE_TO_TOP, moveToTopAction)
        registry.register(ValueChangedCommand.class, undoRedoStackAction)
        registry.register(ValueChangedCommand.class, pushBoardDeparturesOnQueueAction)
        registry.register(UNDO, undoAction)
        registry.register(REDO, redoAction)
    }

    private static DTO createEmptyDepartureDTO(int positionOnBoard) {
        List<Slot> slots = []
        ALL.each { propertyName ->
            if (propertyName != POSITION) {
                slots << new Slot(propertyName, "")
            }
        }
        slots << new Slot(POSITION, positionOnBoard)

        new DTO(slots)
    }

    private static DTO createDepartureDTO(PresentationModel pm, int positionOnBoard) {
        List<Slot> slots = []
        ALL.each { propertyName ->
            if (propertyName != POSITION) {
                slots << new Slot(propertyName, pm[propertyName].value)
            }
        }
        slots << new Slot(POSITION, positionOnBoard)

        new DTO(slots)
    }

    private void sendDepartureBoardEntries(List<Integer> positions) {
        positions.each { idx ->
            int positionInList = positionsOnBoard[idx]
            DTO dto
            if (positionInList == -1) {
                dto = createEmptyDepartureDTO(idx)
            } else {
                dto = createDepartureDTO(pmAtPos(positionInList), idx)
            }
            ApplicationDirector.eventBus.publish valueQueue, dto
        }
    }

    private static boolean hasToBeIgnored(final ValueChangedCommand inNextTripleToIgnore, final ValueChangedCommand inValueChangedCommand) {
        return inNextTripleToIgnore != null &&
                inNextTripleToIgnore.attributeId == inValueChangedCommand.attributeId &&
                inNextTripleToIgnore.oldValue == inValueChangedCommand.oldValue &&
                inNextTripleToIgnore.newValue == inValueChangedCommand.newValue
    }

    private PresentationModel pmAtPos(int pos) {
        getServerDolphin()[pmId(DEPARTURE, pos)]
    }

    private PresentationModel nextModelOnBoard(int startPos) {
        int pos = startPos
        PresentationModel pm = pmAtPos(pos)

        while (pm != null && pm[STATUS].value == HAS_LEFT) {
            pos++
            pm = pmAtPos(pos)
        }

        pm
    }

    private void updatePositionsOnBoard(int firstPositionInList, int firstPositionOnBoard) {
        int nextPosInList = firstPositionInList
        for (int i = firstPositionOnBoard; i < 5; i++) {
            PresentationModel pm = nextModelOnBoard(nextPosInList)
            if (pm != null) {
                nextPosInList = pm[POSITION].value as Integer
                positionsOnBoard[i] = nextPosInList
                nextPosInList = nextPosInList + 1
            } else {
                positionsOnBoard[i] = -1
            }
        }
    }

    private static DTO createDeparture(id, departureTime, trainNumber, destination, stopOvers, track) {
        new DTO(
                createSlot(POSITION, id, id),
                createSlot(DEPARTURE_TIME, departureTime, id),
                createSlot(TRAIN_NUMBER, trainNumber, id),
                createSlot(DESTINATION, destination, id),
                createSlot(TRACK, track, id),
                createSlot(STOPOVERS, stopOvers, id),
                createSlot(STATUS, APPROACHING, id))
    }

    private static Slot createSlot(String propertyName, Object value, int id) {
        new Slot(propertyName, value, pmId(DEPARTURE, id) + '/' + propertyName)
    }

    private static String pmIdFromQualifier(String qualifier) {
        qualifier.split('/').first()
    }




    static List<DTO> loadDepartureDTOs() {

        def stream = AdminActions.class.getClassLoader().getResourceAsStream(DEPARTURES)
        List<DTO> dtos = []
        def i = 0
        stream.eachLine {
            String[] data = it.split(",")
            dtos.add(createDeparture(i++, data[0], data[1], data[2], data[3], data[4]))
        }
        dtos
    }

}
