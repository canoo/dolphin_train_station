package com.canoo.codecamp.dolphinpi.swingbaseddisplay

import com.canoo.codecamp.dolphinpi.BoardItemConstants
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientModelStore
import org.opendolphin.core.client.ClientPresentationModel
import org.opendolphin.core.client.comm.ClientConnector
import org.opendolphin.core.client.comm.HttpClientConnector
import org.opendolphin.core.client.comm.UiThreadHandler
import org.opendolphin.core.comm.JsonCodec

import javax.swing.*

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
			BoardItemConstants.ATT.ALL.each { attr -> attributeMap[attr] = null }
			clientDolphin.presentationModel(BoardItemConstants.pmId(BoardItemConstants.TYPE.BOARD_ITEM, it), BoardItemConstants.TYPE.BOARD_ITEM, attributeMap)
		}
	}

	private static ClientConnector createConnector(ClientDolphin clientDolphin) {
		//running real client server mode.
		HttpClientConnector connector = new HttpClientConnector(clientDolphin, "http://localhost:8080/appContext/applicationServlet/");
//		HttpClientConnector connector = new HttpClientConnector(clientDolphin, "http://100.110.52.69:8080/appContext/applicationServlet/");
		connector.setCodec(new JsonCodec());
		return connector;
	}

}
