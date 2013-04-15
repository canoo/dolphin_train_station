package com.canoo.codecamp.dolphinpi.swingbaseddisplay

import com.canoo.codecamp.dolphinpi.DeparturesBoardApplicationModel
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientModelStore
import org.opendolphin.core.client.comm.ClientConnector
import org.opendolphin.core.client.comm.HttpClientConnector
import org.opendolphin.core.client.comm.JavaFXUiThreadHandler
import org.opendolphin.core.client.comm.UiThreadHandler
import org.opendolphin.core.comm.JsonCodec

import javax.swing.SwingUtilities

public class Starter {
	public static void main(String[] args) {
		ClientDolphin clientDolphin = new ClientDolphin();
		clientDolphin.setClientModelStore(new ClientModelStore(clientDolphin));

		ClientConnector connector = createConnector(clientDolphin);
		connector.setUiThreadHandler(new UiThreadHandler() {
			@Override
			void executeInsideUiThread(Runnable runnable) {
				SwingUtilities.invokeLater(runnable.run())
			}
		});
		clientDolphin.setClientConnector(connector);

		DeparturesBoardApplicationModel departuresModel = new DeparturesBoardApplicationModel(clientDolphin: clientDolphin)
		final departureBoardUI = new DepartureBoardUI(clientDolphin, departuresModel)


		SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						departureBoardUI.start()
					}
				});
		departuresModel.initialize()
		departureBoardUI.doAllBindings()
	}

	private static ClientConnector createConnector(ClientDolphin clientDolphin) {
		//running real client server mode.
        HttpClientConnector connector = new HttpClientConnector(clientDolphin, "http://localhost:8080/appContext/applicationServlet/");
		connector.setCodec(new JsonCodec());
		return connector;
	}

}
