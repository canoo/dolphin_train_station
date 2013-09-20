package com.canoo.codecamp.dolphinpi.VladBoard

import javafx.application.Application
import javafx.geometry.Rectangle2D
import javafx.scene.Parent
    import javafx.scene.Scene
    import javafx.scene.paint.Color
    import javafx.scene.paint.CycleMethod
    import javafx.scene.paint.LinearGradient
    import javafx.scene.paint.Stop
import javafx.stage.Screen
import javafx.stage.Stage
    import org.opendolphin.core.client.ClientDolphin

    import static com.canoo.codecamp.dolphinpi.BoardItemConstants.ATT.ALL
    import static com.canoo.codecamp.dolphinpi.BoardItemConstants.CMD.LONG_POLL
    import static com.canoo.codecamp.dolphinpi.BoardItemConstants.TYPE.BOARD_ITEM
    import static com.canoo.codecamp.dolphinpi.BoardItemConstants.pmId

    public class VladBoardApplication extends Application {
        public static ClientDolphin clientDolphin;

        public VladBoardApplication() {
        }

        @Override
        public void start(Stage stage) throws Exception {
            initializePresentationModels()
             //

            LinearGradient gradient = new LinearGradient(0, 0, 0, 79, false, CycleMethod.NO_CYCLE,
                    new Stop(0.16, Color.rgb(152,230,248)),
                    new Stop(0.58, Color.rgb(45,180,214)),
                    new Stop(0.45, Color.rgb(55,199,199)));


            stage.setTitle("JavaFX - Abfahren ab Olten");
            stage.setScene(new Scene(createStageRoot(), 1500, 618, gradient));
            stage.setResizable(false)
            stage.show();

            def longPoll = null
            longPoll = {
                clientDolphin.send LONG_POLL, longPoll
            }

            longPoll()
        }

        private static void initializePresentationModels () {
            (0..4).each {

                //PresentationModel pm = clientDolphin.presentationModel(pmId(TYPE_DEPARTURE_ON_BOARD, it), TYPE_DEPARTURE_ON_BOARD, ALL_ATTRIBUTES)

                Map<String, Object> attributeMap = [:]
                ALL.each {attr -> attributeMap[attr] = null}
                clientDolphin.presentationModel(pmId(BOARD_ITEM, it), BOARD_ITEM, attributeMap)
            }
        }

        private static Parent createStageRoot() {
            VladBoardViewFactory.createView(clientDolphin)
        }

    }


