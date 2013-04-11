package com.canoo.codecamp.dolphinpi
import org.opendolphin.core.client.ClientDolphin

class MainView {
	static show(ClientDolphin clientDolphin) {

		start { app ->
			def sgb = delegate
			stage title:'DolphinPiApp', {
				scene(fill: BLACK, width: 650, height: 250) {
					hbox(padding: 60) {
						text(text: 'Open', font: '80pt sanserif') {
							fill linearGradient(endX: 0, stops: [PALEGREEN, SEAGREEN])
						}
						text(text: ' Dolphin', font: '80pt sanserif') {
							fill linearGradient(endX: 0, stops: [CYAN, DODGERBLUE])
							effect dropShadow(color: DODGERBLUE, radius: 25, spread: 0.25)
						}
					}
				}
			}
			primaryStage.show()
		}

/*
		final Group root = new Group();
		Scene scene = new Scene(root, 500, 200, Color.DODGERBLUE);

		final Text text = new Text(140, 120, "");
		text.setFont(Font.font("Calibri", 35));
		text.setFill(Color.WHITE);
		text.setEffect(new DropShadow());

		Button bt = new Button("Show current time");
		bt.setLayoutX(180);
		bt.setLayoutY(50);
		bt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			void handle(final ActionEvent inT) {
				text.setText("It is now: " + new SimpleDateFormat("hh:mm:ss").format(new Date()));
			}
		})

		root.getChildren().add(bt);
		root.getChildren().add(text);

		stage.setTitle("HelloWorld in JavaFX 2.0");
		stage.setScene(scene);
		stage.show();*/


	}
}
