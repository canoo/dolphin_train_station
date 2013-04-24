package com.canoo.codecamp.dolphinpi;

import org.opendolphin.core.server.EventBus;
import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;

import groovyx.gpars.dataflow.DataflowQueue;

public class ApplicationDirector extends DolphinServerAction {

	//static to be accessible by other "sessions"
	public static EventBus eventBus = new EventBus();

	private final DataflowQueue valueQueue;

	public ApplicationDirector() {
		valueQueue = new DataflowQueue();
		eventBus.subscribe(valueQueue);
	}

	public void registerIn(ActionRegistry registry) {
		// register all your actions here.
		getServerDolphin().register(new AdminActions(valueQueue));
		getServerDolphin().register(new BoardActions(valueQueue));
	}
}
