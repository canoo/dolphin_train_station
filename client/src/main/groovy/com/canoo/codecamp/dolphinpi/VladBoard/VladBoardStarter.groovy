package com.canoo.codecamp.dolphinpi.VladBoard

import javafx.application.Application
    import org.opendolphin.core.client.ClientDolphin
    import org.opendolphin.core.client.ClientModelStore
    import org.opendolphin.core.client.comm.ClientConnector
    import org.opendolphin.core.client.comm.HttpClientConnector
    import org.opendolphin.core.client.comm.JavaFXUiThreadHandler
    import org.opendolphin.core.comm.JsonCodec

    public class VladBoardStarter {
        public static void main(String[] args) {
            ClientDolphin clientDolphin = new ClientDolphin();
            clientDolphin.setClientModelStore(new ClientModelStore(clientDolphin));

            ClientConnector connector = createConnector(clientDolphin);
            connector.setUiThreadHandler(new JavaFXUiThreadHandler());
            clientDolphin.setClientConnector(connector);


            VladBoardApplication.clientDolphin = clientDolphin;
            Application.launch(VladBoardApplication.class);
        }

        private static ClientConnector createConnector(ClientDolphin clientDolphin) {
            //running real client server mode.
            HttpClientConnector connector = new HttpClientConnector(clientDolphin, "http://localhost:8080/appContext/applicationServlet/");
            //	HttpClientConnector connector = new HttpClientConnector(clientDolphin, "http://10.175.4.198:8080/appContext/applicationServlet/");
            connector.setCodec(new JsonCodec());
            return connector;
        }

    }
