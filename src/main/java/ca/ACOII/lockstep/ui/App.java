package ca.ACOII.lockstep.ui;

import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.Door;
import ca.ACOII.lockstep.model.Room;
import ca.ACOII.lockstep.model.Target;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class App extends Application {

    public static void main(String[] args) {launch(args);}
    @Override
    public void start(Stage stage) throws Exception {
        StackPane rootStack = new StackPane();

        Definitions def = new Definitions();
        Target t = def.targets.getFirst();

        rootStack.getChildren().add(new FloorPlanView(t));

        stage.setScene(new Scene(rootStack, 800, 600));
        stage.setTitle("Lockstep");
        stage.setResizable(false);
        stage.show();
    }

}
