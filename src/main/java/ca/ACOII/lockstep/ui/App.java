package ca.ACOII.lockstep.ui;

import ca.ACOII.lockstep.Game;
import ca.ACOII.lockstep.engine.*;
import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.Door;
import ca.ACOII.lockstep.model.Room;
import ca.ACOII.lockstep.model.Target;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class App extends Application {

    Game game = new Game(false);

    public static void main(String[] args) {launch(args);}
    @Override
    public void start(Stage stage) throws Exception {
        StackPane rootStack = new StackPane();

        Definitions def = new Definitions();
        Target t = def.targets.getFirst();

        game.setMoney(100);
        game.recruit("thief", "Thief A");
        game.recruit("thief", "Thief B");

        FloorPlanView plan = new FloorPlanView(t, game);

        BorderPane center = new BorderPane();
        center.setCenter(plan);
        Button btn = new Button("Run Heist");
        btn.setOnAction(e -> {
            ((Button) e.getSource()).setDisable(true);

            Target target = game.getAvailableTargets().getFirst();

            List<Action> actions = new ArrayList<>();
            actions.add(new MoveAction("LOBBY", 1 ));
            actions.add(new PickLockAction("d3", 2 ));
            actions.add(new MoveAction("VAULT", 1 ));
            actions.add(new CrackAction("Vault Safe", 3));
            actions.add(new GrabAction(def.findLoot("safe_contents")));
            actions.add(new GrabAction(def.findLoot("candelabra_b")));
            actions.add(new MoveAction("LOBBY", 1 ));
            actions.add(new MoveAction("OUTSIDE", 1 ));

            game.runHeist(target, Map.of(game.getRoster().getFirst(), new ActionQueue(actions)));

            HeistPlayer player = new HeistPlayer(plan, game.getLog(), t);
            player.play();

        });
        Button btn2 = new Button("Test Move");
        btn2.setOnAction(e -> {
            plan.moveActor("Thief A", "LOBBY", t);
        });

        HBox bottomPane = new HBox(btn, btn2);
        bottomPane.setPadding(new Insets(20));
        bottomPane.setAlignment(Pos.CENTER);
        center.setBottom(bottomPane);


        rootStack.setAlignment(Pos.CENTER);
        rootStack.getChildren().add(center);

        stage.setScene(new Scene(rootStack, 800, 600));
        stage.setTitle("Lockstep");
        stage.setResizable(false);
        stage.show();
    }

}
