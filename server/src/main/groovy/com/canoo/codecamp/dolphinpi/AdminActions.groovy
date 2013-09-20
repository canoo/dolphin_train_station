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

import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.APPROACHING
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.DEPARTURE_TIME
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.DESTINATION
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.DRIVE_IN
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.DRIVE_OUT
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.FIRST_ONE
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.HAS_LEFT
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.IN_STATION
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
import static com.canoo.codecamp.dolphinpi.DepartureConstants.CMD.CHANGE
import static com.canoo.codecamp.dolphinpi.DepartureConstants.CMD.SAVE
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.TYPE.BOARD_ITEM
import static com.canoo.codecamp.dolphinpi.DepartureConstants.SPECIAL_ID.BUTTONS
import static com.canoo.codecamp.dolphinpi.DepartureConstants.SPECIAL_ID.DEPARTURES
import static com.canoo.codecamp.dolphinpi.DepartureConstants.SPECIAL_ID.EMPTY_DEPARTURE
import static com.canoo.codecamp.dolphinpi.DepartureConstants.SPECIAL_ID.SELECTED_DEPARTURE
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.APPROACHING
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.HAS_LEFT
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.IN_STATION
import static com.canoo.codecamp.dolphinpi.DepartureConstants.TYPE.DEPARTURE
import static com.canoo.codecamp.dolphinpi.DepartureConstants.pmId
import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.ATT.LANGUAGE
import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.ATT.REDO_DISABLED
import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.ATT.SELECTED_DEPARTURE_ID
import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.ATT.TOP_DEPARTURE_ON_BOARD
import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.ATT.UNDO_DISABLED
import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.TYPE.PRESENTATION_STATE

@SuppressWarnings("GroovyAssignabilityCheck")
class AdminActions extends DolphinServerAction {

    private static final String BOARD_RESOURCES = "BoardResources"
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

            changeValue getServerDolphin()[BUTTONS][DRIVE_IN], bundle.getString("DRIVE_IN")
            changeValue getServerDolphin()[BUTTONS][DRIVE_OUT], bundle.getString("DRIVE_OUT")
            changeValue getServerDolphin()[BUTTONS][FIRST_ONE], bundle.getString("FIRST_ONE")
            changeValue getServerDolphin()[BUTTONS][APPROACHING], bundle.getString("APPROACHING")
            changeValue getServerDolphin()[BUTTONS][HAS_LEFT], bundle.getString("HAS_LEFT")
            changeValue getServerDolphin()[BUTTONS][IN_STATION], bundle.getString("IN_STATION")
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
                return;
            }
            ValueChangedCommand cmd = undoStack.pop()
            nextTripleToIgnore = new ValueChangedCommand(attributeId: cmd.attributeId, oldValue: cmd.newValue, newValue: cmd.oldValue)

            changeValue(getServerDolphin()[PRESENTATION_STATE][REDO_DISABLED], false)
            changeValue(getServerDolphin().serverModelStore.findAttributeById(cmd.attributeId) as ServerAttribute, cmd.oldValue)
            redoStack.push(cmd)
            if (undoStack.isEmpty()) {
                changeValue(getServerDolphin()[PRESENTATION_STATE][UNDO_DISABLED], true)
            }

        }
    }

    private final CommandHandler redoAction = new SimpleCommandHandler() {
        @Override
        void handleCommand() {
            if (redoStack.isEmpty()) {
                return
            }
            ValueChangedCommand valueChangedCommand = redoStack.pop()
            if (redoStack.isEmpty()) {
                changeValue(getServerDolphin()[PRESENTATION_STATE][REDO_DISABLED], true)
            }
            nextTripleToIgnore = new ValueChangedCommand(attributeId: valueChangedCommand.attributeId, oldValue: valueChangedCommand.oldValue, newValue: valueChangedCommand.newValue)
            undoStack.push(valueChangedCommand)
            changeValue(getServerDolphin()[PRESENTATION_STATE][UNDO_DISABLED], false)
            changeValue(getServerDolphin().serverModelStore.findAttributeById(valueChangedCommand.attributeId) as ServerAttribute, valueChangedCommand.newValue)
        }
    }

    private final CommandHandler saveAction = new SimpleCommandHandler() {
        @Override
        void handleCommand() {
            def writer = new FileWriter(System.getProperty("java.io.tmpdir") + DEPARTURES)
            def departures = getServerDolphin().serverModelStore.findAllPresentationModelsByType(DEPARTURE)
            departures.each {
                writer.write(
                        it[DEPARTURE_TIME].value + "," +
                                it[TRAIN_NUMBER].value + "," +
                                it[DESTINATION].value + "," +
                                it[STOPOVERS].value + "," +
                                it[TRACK].value + "," +
                                System.getProperty("line.separator")
                )
                writer.flush()

            }

        }
    }

    private final CommandHandler undoRedoStackAction = new CommandHandler<ValueChangedCommand>() {

        @Override
        public void handleCommand(final ValueChangedCommand command, final List<Command> response) {
            PresentationModel statePM = getServerDolphin()[PRESENTATION_STATE]
            PresentationModel selectedPM = getServerDolphin()[SELECTED_DEPARTURE]
            PresentationModel buttonsPM = getServerDolphin()[BUTTONS]
            PresentationModel emptyPM = getServerDolphin()[EMPTY_DEPARTURE]
            def attributeAffected = getServerDolphin().getModelStore().findAttributeById(command.getAttributeId());
            if (statePM != null) {

                def undoId = statePM[UNDO_DISABLED].getId()
                def redoId = statePM[REDO_DISABLED].getId()
                if (attributeAffected.getTag().compareTo(Tag.LABEL) == 0) return;
                if (command.getAttributeId() == undoId) return;
                if (selectedPM.findAttributeById(command.getAttributeId())) return;
                if (buttonsPM.findAttributeById(command.getAttributeId())) return;
                if (emptyPM.findAttributeById(command.getAttributeId())) return;
                if (command.getAttributeId() == redoId) return;

                if (hasToBeIgnored(nextTripleToIgnore, command)) {
                    nextTripleToIgnore = null
                } else {
                    undoStack.push(command)
                    changeValue getServerDolphin()[PRESENTATION_STATE][UNDO_DISABLED], false
                    redoStack.clear()
                    changeValue getServerDolphin()[PRESENTATION_STATE][REDO_DISABLED], true
                }

            }
        }
    }

    private final CommandHandler changeLanguage = new SimpleCommandHandler() {
        @Override
        void handleCommand() {
            PresentationModel PMS = getServerDolphin()[PRESENTATION_STATE]


            def bundle = ResourceBundle.getBundle(BOARD_RESOURCES, new Locale(PMS[LANGUAGE].value))
            changeValue getServerDolphin()[SELECTED_DEPARTURE][DEPARTURE_TIME, Tag.LABEL], bundle.getString("DEPARTURE_TIME")
            changeValue getServerDolphin()[SELECTED_DEPARTURE][DESTINATION, Tag.LABEL], bundle.getString("DESTINATION")
            changeValue getServerDolphin()[SELECTED_DEPARTURE][TRAIN_NUMBER, Tag.LABEL], bundle.getString("TRAIN_NUMBER")
            changeValue getServerDolphin()[SELECTED_DEPARTURE][TRACK, Tag.LABEL], bundle.getString("TRACK")
            changeValue getServerDolphin()[SELECTED_DEPARTURE][STOPOVERS, Tag.LABEL], bundle.getString("STOPOVERS")
            changeValue getServerDolphin()[SELECTED_DEPARTURE][STATUS, Tag.LABEL], bundle.getString("STATUS")


            changeValue getServerDolphin()[BUTTONS][DRIVE_IN], bundle.getString("DRIVE_IN")
            changeValue getServerDolphin()[BUTTONS][DRIVE_OUT], bundle.getString("DRIVE_OUT")
            changeValue getServerDolphin()[BUTTONS][FIRST_ONE], bundle.getString("FIRST_ONE")
            changeValue getServerDolphin()[BUTTONS][IN_STATION], bundle.getString("IN_STATION")
            changeValue getServerDolphin()[BUTTONS][HAS_LEFT], bundle.getString("HAS_LEFT")
            changeValue getServerDolphin()[BUTTONS][APPROACHING], bundle.getString("APPROACHING")


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
                }
                else {
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
        registry.register(CHANGE, changeLanguage)
        registry.register(UNDO, undoAction)
        registry.register(REDO, redoAction)
        registry.register(SAVE, saveAction)

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

    static ResourceBundle bundle2 = ResourceBundle.getBundle("BoardResources")

    private static DTO createDeparture(id, departureTime, trainNumber, destination, stopOvers, track) {
        new DTO(


                createSlot(POSITION, id, id),
                createSlot(DEPARTURE_TIME, departureTime, id),
                createSlot(TRAIN_NUMBER, trainNumber, id),
                createSlot(DESTINATION, destination, id),
                createSlot(TRACK, track, id),
                createSlot(STOPOVERS, stopOvers, id),
                createSlot(STATUS, bundle2.getString("APPROACHING"), id))
    }

    private static Slot createSlot(String propertyName, Object value, int id) {
        new Slot(propertyName, value, pmId(DEPARTURE, id) + '/' + propertyName)
    }

    private static String pmIdFromQualifier(String qualifier) {
        qualifier.split('/').first()
    }

    class DTOS {

        String DepartureTime
        String TrainNumber
        String Destination
        String Track
        String StopOvers

    }

    static List<DTO> loadDepartureDTOs() {


        List<DTO> dtos = []
        def file = new File(System.getProperty("java.io.tmpdir") + DEPARTURES)
        if (file.canExecute()) {
            def i = 0
            def reader = new FileReader(System.getProperty("java.io.tmpdir") + DEPARTURES)
            reader.eachLine {
                String[] data = it.split(",")

                def Departure = DTOS.newInstance()
                Departure.DepartureTime = data[0]
                Departure.TrainNumber = data[1]
                Departure.Destination = data[2]
                Departure.Track = data[3]
                Departure.StopOvers = data[4]


                dtos.add(createDeparture(i++, Departure.DepartureTime, Departure.TrainNumber, Departure.Destination, Departure.Track, Departure.StopOvers))
            }
            dtos
        } else {
            InputStream stream = AdminActions.class.getClassLoader().getResourceAsStream(DEPARTURES)
            def i = 0

            stream.eachLine {
                String[] data = it.split(",")

                def Departure = DTOS.newInstance()
                Departure.DepartureTime = data[0]
                Departure.TrainNumber = data[1]
                Departure.Destination = data[2]
                Departure.Track = data[3]
                Departure.StopOvers = data[4]


                dtos.add(createDeparture(i++, Departure.DepartureTime, Departure.TrainNumber, Departure.Destination, Departure.Track, Departure.StopOvers))
            }
            dtos


        }
    }

}

