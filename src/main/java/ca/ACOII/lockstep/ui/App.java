package ca.ACOII.lockstep.ui;

import ca.ACOII.lockstep.Game;
import ca.ACOII.lockstep.engine.*;
import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class App extends Application {
    FloorPlanView mapPane;
    Game game = new Game(false);
    Definitions def = new Definitions();
    //TODO: Update when more actions are added
    private static final List<String> ACTIONS = List.of(
            "MOVE", "WAIT", "PICK LOCK", "CRACK SAFE", "GRAB"
    );
    Target target;
    ListView<String> actionList;

    Map<String, List<Action>> plans = new HashMap<>();

    VBox executionPane;
    public static void main(String[] args) {launch(args);}
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane rootStack = new BorderPane();

        target = def.targets.getFirst();

        game.setMoney(100);
        game.recruit("thief", "Thief A");

        plans.put(game.getRoster().getFirst().getData().getName(), new ArrayList<>());

        rootStack.setCenter(mapPane());
        rootStack.setLeft(actionBuilderPane());
        executionPane = executeQueuePane();
        rootStack.setRight(executionPane);

        stage.setScene(new Scene(rootStack, 800, 600));
        stage.setTitle("Lockstep");
        stage.setResizable(false);
        stage.show();
    }

    private VBox mapPane() {
        VBox pane = new VBox();

        FloorPlanView plan = new FloorPlanView(target, game);
        mapPane = plan;
        ScrollPane scroll = new ScrollPane(plan);
        scroll.setPannable(true);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        pane.getChildren().add(scroll);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }
    private VBox actionBuilderPane() {
        VBox actionSelectionPane = new VBox(10);

        Label title = new Label("Add Action");
        actionSelectionPane.getChildren().add(title);

        ComboBox<String> actionTypes = new ComboBox<>();
        actionTypes.getItems().addAll(ACTIONS);
        actionTypes.setOnAction(e -> {
            if (actionSelectionPane.getChildren().size() == 4) {
                actionSelectionPane.getChildren().remove(2);
            }
            actionSelectionPane.getChildren().add(2, formFor(actionTypes.getValue()));
        });
        actionTypes.getSelectionModel().selectFirst();
        actionSelectionPane.getChildren().add(actionTypes);
        actionSelectionPane.getChildren().add(formFor(actionTypes.getValue()));

        Button add = new Button("Add Action");
        add.setOnAction(e -> {
            String value = actionTypes.getValue();
            VBox pane = ((VBox) actionSelectionPane.getChildren().get(2));;
            Action act = null;
            switch (value) {
                case "MOVE" -> {
                    act = new MoveAction(((ComboBox<String>) pane.getChildren().get(1)).getSelectionModel().getSelectedItem(), 1);
                }
                case "WAIT" -> {
                    act = new WaitAction((Integer) ((Spinner<?>) pane.getChildren().get(1)).getValue());
                }
                case "PICK LOCK" -> {
                    Door door = target.findDoor(((ComboBox<String>) pane.getChildren().get(1)).getSelectionModel().getSelectedItem());
                    //TODO: Add consideration for skill level
                    act = new PickLockAction(door.getId(), door.getLock().getPickDifficulty());
                }
                case "CRACK SAFE" -> {
                    SafeContainer safe = (SafeContainer) target.findContainer(((ComboBox<String>) pane.getChildren().get(1)).getSelectionModel().getSelectedItem());
                    act = new CrackAction(safe.getId(), safe.getCrackDifficulty());
                    //TODO: Add consideration for skill level
                }
                case "GRAB" -> {
                    LootItem item = def.findLoot(((ComboBox<String>) pane.getChildren().get(1)).getSelectionModel().getSelectedItem());
                    act = new GrabAction(item);
                }
            }
            //TODO: Change to get selected crew member
            plans.get(game.getRoster().getFirst().getData().getName()).add(act);
            resetActionList();
        });
        actionSelectionPane.getChildren().add(add);

        actionSelectionPane.setAlignment(Pos.CENTER);
        actionSelectionPane.setPrefWidth(220);
        actionSelectionPane.setMinWidth(220);
        actionSelectionPane.setMaxWidth(220);

        return actionSelectionPane;
    }

    private VBox formFor(String value) {
        return switch (value) {
            case "MOVE" -> buildMoveForm();
            case "WAIT" -> buildWaitForm();
            case "PICK LOCK" -> buildPickLockForm();
            case "CRACK SAFE" -> buildCrackSafeForm();
            case "GRAB" -> buildGrabForm();
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

    private VBox buildMoveForm() {
        //TODO: Add functionality
        Label label = new Label("Destination: ");
        ComboBox<String> destSelect = new ComboBox<>();
        List<String> ids = new ArrayList<>();
        for (Room r : target.getRooms()) {
            ids.add(r.getId());
        }
        destSelect.getItems().addAll(ids);
        destSelect.getSelectionModel().selectFirst();

        VBox box = new VBox();
        box.getChildren().addAll(label, destSelect);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private VBox buildWaitForm() {
        Label label = new Label("Duration:");
        Spinner<Integer> spinner = new Spinner<>(1, 99, 1);

        VBox box = new VBox();
        box.getChildren().addAll(label, spinner);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private VBox buildPickLockForm() {
        Label label = new Label("Door:");
        ComboBox<String> doorSelect = new ComboBox<>();

        List<String> ids = new ArrayList<>();
        for (Door d : target.getDoors()) {
            if (d.getLock() != null) ids.add(d.getId());
        }
        doorSelect.getItems().addAll(ids);
        doorSelect.getSelectionModel().selectFirst();

        VBox box = new VBox();
        box.getChildren().addAll(label, doorSelect);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private VBox buildCrackSafeForm() {
        Label label = new Label("Container:");
        ComboBox<String> containerSelect = new ComboBox<>();

        List<String> ids = new ArrayList<>();
        for (Room r : target.getRooms()) {
            for (LootContainer c : r.getRoomContainers()) {
                if (c instanceof SafeContainer safe) {
                    if (safe.getCrackDifficulty() > 0) ids.add(safe.getId());
                }
            }
        }
        containerSelect.getItems().addAll(ids);
        containerSelect.getSelectionModel().selectFirst();

        VBox box = new VBox();
        box.getChildren().addAll(label, containerSelect);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private VBox buildGrabForm() {
        Label label = new Label("Item:");
        ComboBox<String> itemSelect = new ComboBox<>();

        List<String> ids = new ArrayList<>();
        target.getRooms().getFirst().getRoomLoot();
        //TODO: Fix for items with appearance variance (chance in room.getLoot map < 1.0)
        for (Room r : target.getRooms()) {
            ids.addAll(r.getRoomLoot().keySet());
            for (LootContainer c : r.getRoomContainers()) {
                ids.addAll(c.getContents());
            }
        }
        itemSelect.getItems().addAll(ids);
        itemSelect.getSelectionModel().selectFirst();

        VBox box = new VBox();
        box.getChildren().addAll(label, itemSelect);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private VBox executeQueuePane() {
        actionList = new ListView<>();

        Button btn = new Button("Run Heist");
        btn.setOnAction(e -> {
            resetPlan();
            //TODO: Change to be dependent on current plan
            //((Button) e.getSource()).setDisable(true);

            List<Action> actions = plans.get(game.getRoster().getFirst().getData().getName());
            if (actions.isEmpty()) {
                System.out.println("Build a plan first!");
                //((Button) e.getSource()).setDisable(false);
                return;
            }

            game.runHeist(target, Map.of(game.getRoster().getFirst(), new ActionQueue(actions)));

            plans = new HashMap<>();
            plans.put(game.getRoster().getFirst().getData().getName(), new ArrayList<>());
            resetActionList();

            HeistPlayer player = new HeistPlayer(mapPane, game.getLog(), target);
            player.play();

        });

        VBox box = new VBox();

        return new VBox(actionList, btn);
    }

    private void resetActionList() {
        actionList = new ListView<>();
        for (Action act : plans.get(game.getRoster().getFirst().getData().getName())) {
            if (act instanceof MoveAction move) {
                actionList.getItems().add("MOVE to " + move.getDestinationRoom());
            }
            if (act instanceof WaitAction wait) {
                actionList.getItems().add("WAIT " + wait.getDuration());
            }
            if (act instanceof PickLockAction pick) {
                actionList.getItems().add("PICK LOCK door " + pick.getDoorId());
            }
            if (act instanceof CrackAction crack) {
                actionList.getItems().add("CRACK SAFE safe " + crack.getContainerId());
            }
            if (act instanceof GrabAction grab) {
                actionList.getItems().add("GARB item " + grab.getItem().getName());
            }
        }
        executionPane.getChildren().removeFirst();
        executionPane.getChildren().addFirst(actionList);
    }

    private void resetPlan() {
        mapPane.reset(target);
    }
}
