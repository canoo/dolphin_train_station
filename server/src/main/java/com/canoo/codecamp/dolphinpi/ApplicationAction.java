package com.canoo.codecamp.dolphinpi;

import org.opendolphin.core.comm.Command;
import org.opendolphin.core.server.ServerAttribute;
import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;
import org.opendolphin.core.server.comm.CommandHandler;

import java.util.List;

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*;

public class ApplicationAction extends DolphinServerAction {
	public void registerIn(ActionRegistry actionRegistry) {
		actionRegistry.register(COMMAND_ID, new CommandHandler<Command>() {
			public void handleCommand(Command command, List<Command> response) {
				System.out.println("Server reached.");
			}
		});
		actionRegistry.register(ApplicationConstants.COMMAND_INCREASE, new CommandHandler<Command>() {
			public void handleCommand(Command command, List<Command> response) {
				ServerAttribute valueAttr = getServerDolphin().getAt(PM_APP).getAt(ATT_Value);
				changeValue(valueAttr, ((Integer) valueAttr.getValue()) + 1);
			}
		});

	}
}
