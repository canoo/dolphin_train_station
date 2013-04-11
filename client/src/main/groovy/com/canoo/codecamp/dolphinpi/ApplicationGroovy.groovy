package com.canoo.codecamp.dolphinpi
import javafx.collections.FXCollections
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.scene.layout.PaneBuilder
import javafx.scene.layout.VBoxBuilder
import javafx.stage.Stage
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*

public class ApplicationGroovy extends javafx.application.Application {
    public static ClientDolphin clientDolphin;

	javafx.collections.ObservableList<ClientPresentationModel> allDepartures = FXCollections.observableArrayList()

	//def selectedBook = clientDolphin.presentationModel(SELECTED, ALL_ATTRIBUTES)


	public ApplicationGroovy() {
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Departures of Olten");

        Pane root = setupStage();
        addClientSideAction();



        setupBinding();


        stage.setScene(new Scene(root, 300, 300));
        stage.setTitle(getClass().getName());
        stage.show();

		clientDolphin.send COMMAND_GET_ALL_DEPARTURES,  { pms ->
			for (pm in pms) {
				println "pm: $pm"
				allDepartures << pm
			}
		}


    }

    private Pane setupStage() {
        return PaneBuilder.create().children(
                VBoxBuilder.create().children(
					MasterViewFactory.newMasterView(allDepartures)
				).build()
        ).build();
    }

    private void setupBinding() {
    }

    private void addClientSideAction() {
    }
}

