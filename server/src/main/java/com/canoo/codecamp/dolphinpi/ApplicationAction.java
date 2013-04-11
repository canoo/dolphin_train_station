package com.canoo.codecamp.dolphinpi;

import groovyx.gpars.dataflow.DataflowQueue;
import org.opendolphin.core.Attribute;
import org.opendolphin.core.Tag;
import org.opendolphin.core.comm.Command;
import org.opendolphin.core.comm.ValueChangedCommand;
import org.opendolphin.core.server.DTO;
import org.opendolphin.core.server.EventBus;
import org.opendolphin.core.server.ServerAttribute;
import org.opendolphin.core.server.Slot;
import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;
import org.opendolphin.core.server.comm.CommandHandler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*;

public class ApplicationAction extends DolphinServerAction {

	private static EventBus eventBus = new EventBus();
	private final DataflowQueue valueQueue = new DataflowQueue();

	private final int EVENT_PROVIDER_WAIT_MS = 500;
	private final int EVENT_CONSUMER_WAIT_MS = 5000;
	private int waitMillis = EVENT_CONSUMER_WAIT_MS;

	public void registerIn(ActionRegistry actionRegistry) {
//		actionRegistry.register(ValueChangedCommand.class, new CommandHandler<ValueChangedCommand>() {
//			@Override
//			public void handleCommand(final ValueChangedCommand command, final List<Command> response) {
//				Attribute attribute = getServerDolphin().getServerModelStore().findAttributeById(command.getAttributeId());
//				if(attribute != null){
//					if("sharedValue".equals(attribute.getQualifier())){
//						Object newValue = command.getNewValue();
//						eventBus.publish(valueQueue, newValue);
//						waitMillis = EVENT_PROVIDER_WAIT_MS;
//					}
//				}
//			}
//		});

//		actionRegistry.register("poll.value", new CommandHandler<Command>() {
//			@Override
//			public void handleCommand(final Command command, final List<Command> response) {
//				List<Attribute> attributeList = getServerDolphin().getServerModelStore().findAllAttributesByQualifier("sharedValue");
//				ServerAttribute attribute = null;
//				for(Attribute attr : attributeList){
//					if(attr.getTag() == Tag.VALUE){
//						attribute = (ServerAttribute) attr;
//					}
//				}
//				if(attribute == null) {
//					return;
//				}
//
//				try {
//					Object value = valueQueue.getVal(waitMillis, TimeUnit.MILLISECONDS);
//					Object lastValue = value;
//					while (null != value){
//						lastValue = value;
//						value = valueQueue.getVal(20, TimeUnit.MILLISECONDS);
//					}
//					if(null != value) {
//						waitMillis = EVENT_CONSUMER_WAIT_MS;
//						changeValue(attribute, lastValue);
//					}
//				} catch (InterruptedException e) {
//					throw new RuntimeException(e);
//				}
//
//			}
//		});



		actionRegistry.register(COMMAND_GET_ALL_DEPARTURES, new CommandHandler<Command>() {
			public void handleCommand(Command command, List<Command> response) {
				DTO dto = new DTO(
					new Slot(ATT_DEPARTURE_TIME, "07:20", TYPE_DEPARTURE+ATT_DEPARTURE_TIME),
					new Slot(ATT_TRAIN_NUMBER, "IC 809", TYPE_DEPARTURE+ATT_TRAIN_NUMBER),
					new Slot(ATT_DESTINATION, "Romanshorn", TYPE_DEPARTURE+ATT_DESTINATION),
					new Slot(ATT_STATUS, "abgefahren", TYPE_DEPARTURE+ATT_STATUS)
					);
				presentationModel("1", TYPE_DEPARTURE, dto);
			}
		});


	}
}
