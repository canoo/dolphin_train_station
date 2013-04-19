package com.canoo.codecamp.dolphinpi.swingbaseddisplay

import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientModelStore
import org.opendolphin.core.client.ClientPresentationModel
import org.opendolphin.core.client.comm.ClientConnector
import org.opendolphin.core.client.comm.HttpClientConnector
import org.opendolphin.core.client.comm.UiThreadHandler
import org.opendolphin.core.comm.JsonCodec

import javax.swing.*

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*

public class SwingBoardStarter {

	public static void main(String[] args) {
		ClientDolphin clientDolphin = new ClientDolphin();
		clientDolphin.setClientModelStore(new ClientModelStore(clientDolphin));

		ClientConnector connector = createConnector(clientDolphin);
		connector.uiThreadHandler = { todo -> SwingUtilities.invokeLater { todo() } } as UiThreadHandler

		clientDolphin.setClientConnector(connector);

		initializePresentationModels(clientDolphin)

		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				new DepartureBoardFrame(clientDolphin).createAndShow()
			}
		});

	}

	private static void initializePresentationModels(ClientDolphin clientDolphin) {
		(0..4).each {
			Map<String, Object> attributeMap = [:]
			ALL_ATTRIBUTES.each { attr -> attributeMap[attr] = null }
			clientDolphin.presentationModel(pmId(TYPE_DEPARTURE_ON_BOARD, it), TYPE_DEPARTURE_ON_BOARD, attributeMap)
		}
	}

	private static ClientConnector createConnector(ClientDolphin clientDolphin) {
		//running real client server mode.
		HttpClientConnector connector = new HttpClientConnector(clientDolphin, "http://localhost:8080/appContext/applicationServlet/");
		connector.setCodec(new JsonCodec());
		return connector;
	}

}
