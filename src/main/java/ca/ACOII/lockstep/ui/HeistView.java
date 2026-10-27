package ca.ACOII.lockstep.ui;

import ca.ACOII.lockstep.Game;
import ca.ACOII.lockstep.campaign.RosterEntry;
import ca.ACOII.lockstep.engine.*;
import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeistView extends BorderPane {

    FloorPlanView mapPane;
    Game game;
    Target target;
    List<RosterEntry> entries;
    App app;
    Map<String, List<Action>> plans;

    VBox executionPane;

    private static final List<String> ACTIONS = List.of(
            "MOVE", "WAIT", "PICK LOCK", "CRACK SAFE", "GRAB"
    );
    ListView<Action> actionList;

    public HeistView(Target target, List<RosterEntry> entries, App app) {
        this.game = app.game;
        this.target = target;
        this.entries = entries;
        this.app = app;
        plans = new HashMap<>();
        for (RosterEntry e : entries) {
            plans.put(e.getData().getName(), new ArrayList<>());
        }
        build();
    }

    private void build() {
        ComboBox<RosterEntry> heistCrew = new ComboBox<>();
        for (RosterEntry e : entries) {
            heistCrew.getItems().add(e);
            heistCrew.getSelectionModel().selectFirst();
        }
        this.setCenter(mapPane());
        this.setLeft(actionBuilderPane());
        executionPane = executeQueuePane();
        this.setRight(executionPane);

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
                    LootItem item = app.def.findLoot(((ComboBox<String>) pane.getChildren().get(1)).getSelectionModel().getSelectedItem());
                    act = new GrabAction(item);
                }
            }
            //TODO: Change to get selected crew member
            plans.get(game.getRoster().getFirst().getData().getName()).add(act);
            resetActionList();
        });
        actionSelectionPane.getChildren().add(add);

        actionSelectionPane.setAlignment(Pos.CENTER);
        actionSelectionPane.setPrefWidth(150);
        actionSelectionPane.setMinWidth(150);
        actionSelectionPane.setMaxWidth(150);

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

        Button returnBtn = new Button("Back");
        returnBtn.setOnAction(e -> {
            app.showScreen(Screen.CAMPAIGN);
        });
        Button executeBtn = new Button("Run Heist");
        executeBtn.setOnAction(e -> {
            mapPane.reset(target);
            returnBtn.setDisable(true);
            executeBtn.setDisable(true);

            List<Action> actions = plans.get(game.getRoster().getFirst().getData().getName());
            if (actions.isEmpty()) {
                System.out.println("Build a plan first!");
                returnBtn.setDisable(false);
                executeBtn.setDisable(false);
                return;
            }

            game.runHeist(target, Map.of(game.getRoster().getFirst(), new ActionQueue(actions)));

            plans = new HashMap<>();
            plans.put(game.getRoster().getFirst().getData().getName(), new ArrayList<>());
            resetActionList();

            HeistPlayer player = new HeistPlayer(mapPane, game.getLog(), target, new Runnable() {
                @Override
                public void run() {
                    returnBtn.setDisable(false);
                    returnBtn.setText("Continue");
                }
            });
            player.play();

        });

        return new VBox(actionList, executeBtn, returnBtn);
    }

    private void resetActionList() {
        actionList = new ListView<>();
        actionList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Action action, boolean empty) {
                super.updateItem(action, empty);
                if (empty || action == null) {
                    setText(null);
                } else {
                    if (action instanceof MoveAction move) {
                        setText("MOVE to " + move.getDestinationRoom());
                    }
                    if (action instanceof WaitAction wait) {
                        setText("WAIT " + wait.getDuration());
                    }
                    if (action instanceof PickLockAction pick) {
                        setText("PICK LOCK door " + pick.getDoorId());
                    }
                    if (action instanceof CrackAction crack) {
                        setText("CRACK SAFE safe " + crack.getContainerId());
                    }
                    if (action instanceof GrabAction grab) {
                        setText("GARB item " + grab.getItem().getName());
                    }
                }
            }
        });
        actionList.getItems().addAll(plans.get(game.getRoster().getFirst().getData().getName()));
        actionList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            plans.get(game.getRoster().getFirst().getData().getName()).remove(newVal);
            resetActionList();
        });
        executionPane.getChildren().removeFirst();
        executionPane.getChildren().addFirst(actionList);
    }
}
