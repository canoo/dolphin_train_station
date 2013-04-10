package com.canoo.codecamp.dolphinpi;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.layout.Pane;
import javafx.scene.layout.PaneBuilder;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Stage;
import org.opendolphin.binding.JFXBinder;
import org.opendolphin.core.PresentationModel;
import org.opendolphin.core.client.ClientAttribute;
import org.opendolphin.core.client.ClientDolphin;

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*;


public class Application extends javafx.application.Application {
    static ClientDolphin clientDolphin;

    private PresentationModel textAttributeModel;

    private Button button;
	private Label label;

    public Application() {
        textAttributeModel = clientDolphin.presentationModel(PM_APP,
			new ClientAttribute(ATT_ID, null),
			new ClientAttribute(ATT_Value, 1));
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Application Title");

        Pane root = setupStage();
        addClientSideAction();
        setupBinding();


        stage.setScene(new Scene(root, 300, 300));
        stage.setTitle(getClass().getName());
        stage.show();

    }

    private Pane setupStage() {
        return PaneBuilder.create().children(
                VBoxBuilder.create().children(
					button = ButtonBuilder.create()
                                .text("click me")
                                .build(),
					label = LabelBuilder.create().build()
				).build()
        ).build();
    }

    private void setupBinding() {
		JFXBinder.bind(ATT_Value).of(textAttributeModel).to("text").of(label);
    }

    private void addClientSideAction() {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
				clientDolphin.send(COMMAND_INCREASE);
//                clientDolphin.send(COMMAND_ID, new OnFinishedHandlerAdapter() {
//                    @Override
//                    public void onFinished(List<ClientPresentationModel> presentationModels) {
//                        textAttributeModel.getAt(ATT_ID).rebase();
//                    }
//                });
            }
      });
    }
}
