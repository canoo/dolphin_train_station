package com.canoo.codecamp.dolphinpi

import groovyx.gpars.dataflow.DataflowQueue
import org.opendolphin.core.server.DTO
import org.opendolphin.core.server.ServerPresentationModel
import org.opendolphin.core.server.action.DolphinServerAction
import org.opendolphin.core.server.comm.ActionRegistry
import org.opendolphin.core.server.comm.CommandHandler
import org.opendolphin.core.server.comm.SimpleCommandHandler

import java.util.concurrent.TimeUnit

import static com.canoo.codecamp.dolphinpi.BoardItemConstants.ATT.POSITION
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.CMD.LONG_POLL
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.TYPE.BOARD_ITEM
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.pmId

@SuppressWarnings("GroovyAssignabilityCheck")
class BoardActions extends DolphinServerAction {

	private final DataflowQueue valueQueue

	private final CommandHandler longPollAction = new SimpleCommandHandler() {
		@Override
		void handleCommand() {
			DTO dto = valueQueue.getVal(60, TimeUnit.SECONDS)
			if (dto == null) return
			int positionOnBoard = dto.slots.find { it.propertyName == POSITION }.value

			ServerPresentationModel pm = getServerDolphin()[pmId(BOARD_ITEM, positionOnBoard)]
			pm.attributes.each { attr ->
				changeValue(attr, dto.slots.find { it.propertyName == attr.propertyName }.value)
			}
		}
	}

	BoardActions(DataflowQueue valueQueue) {
		this.valueQueue = valueQueue
	}

	public void registerIn(ActionRegistry registry) {
		registry.register(LONG_POLL, longPollAction)
	}

}
