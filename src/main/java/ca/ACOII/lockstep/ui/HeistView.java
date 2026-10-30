package ca.ACOII.lockstep.ui;

import ca.ACOII.lockstep.Game;
import ca.ACOII.lockstep.campaign.RosterEntry;
import ca.ACOII.lockstep.engine.*;
import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.awt.*;
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
    HBox logPane;
    ListView<String> logList;

    private static final List<String> ACTIONS = List.of(
            "MOVE", "WAIT", "PICK LOCK", "CRACK SAFE", "PICK CASE", "GRAB"
    );
    ComboBox<RosterEntry> heistCrew;
    ListView<Action> actionList;
    HeistInstance instance;
    boolean actionPossible;

    public HeistView(HeistInstance instance, List<RosterEntry> entries, App app) {
        this.game = app.game;
        this.instance = instance;
        this.target = instance.getTarget();
        this.entries = entries;
        this.app = app;
        plans = new HashMap<>();
        for (RosterEntry e : entries) {
            plans.put(e.getData().getName(), new ArrayList<>());
        }
        setPadding(new Insets(15));

        build();
    }

    private void build() {
        this.setCenter(mapPane());
        this.setLeft(actionBuilderPane());
        executionPane = executeQueuePane();
        this.setRight(executionPane);
        logPane = executionLogPane();
        this.setBottom(logPane);
        for (Node node : this.getChildren()) {
            if (node instanceof HBox h) h.setSpacing(20);
            if (node instanceof VBox v) v.setSpacing(20);
        }

    }

    private HBox executionLogPane() {
        HBox pane = new HBox();
        pane.setPadding(new Insets(50, 10, 50, 10));

        logList = new ListView<>();
        logList.setMaxHeight(200);
        logList.setMaxWidth(Double.MAX_VALUE);

        pane.getChildren().add(logList);
        pane.setAlignment(Pos.BOTTOM_CENTER);
        HBox.setHgrow(logList, Priority.ALWAYS);
        return pane;
    }

    private VBox mapPane() {
        VBox pane = new VBox(10);

        FloorPlanView plan = new FloorPlanView(game.getInstance(), game, entries, app.def);
        mapPane = plan;
        mapPane.setPadding(new Insets(20));
        ScrollPane scroll = new ScrollPane(mapPane);
        scroll.setPannable(true);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        pane.getChildren().add(scroll);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }

    private VBox actionBuilderPane() {
        VBox actionSelectionPane = new VBox(10);

        heistCrew = new ComboBox<>();
        heistCrew.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(RosterEntry entry, boolean empty) {
                super.updateItem(entry, empty);
                if (empty || entry == null) {
                    setText(null);
                } else {
                    setText(entry.getData().getName());
                }
            }
        });
        heistCrew.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(RosterEntry entry, boolean empty) {
                super.updateItem(entry, empty);
                if (empty || entry == null) {
                    setText(null);
                } else {
                    setText(entry.getData().getName());
                }
            }
        });
        for (RosterEntry e : entries) {
            heistCrew.getItems().add(e);
        }
        heistCrew.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            resetActionList(newVal);
        });
        heistCrew.getSelectionModel().selectFirst();
        actionSelectionPane.getChildren().add(heistCrew);

        Label title = new Label("Add Action");
        actionSelectionPane.getChildren().add(title);

        ComboBox<String> actionTypes = new ComboBox<>();
        actionTypes.getItems().addAll(ACTIONS);
        actionTypes.setOnAction(e -> {
            if (actionSelectionPane.getChildren().size() == 5) {
                actionSelectionPane.getChildren().remove(3);
            }
            actionSelectionPane.getChildren().add(3, formFor(actionTypes.getValue()));
        });
        actionTypes.getSelectionModel().selectFirst();

        Button add = new Button("Add Action");
        add.setOnAction(e -> {
            String value = actionTypes.getValue();
            VBox pane = ((VBox) actionSelectionPane.getChildren().get(3));;
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
                    int dur = ActDurationCalculator.pickLockDuration(door, heistCrew.getSelectionModel().getSelectedItem());
                    act = new PickLockAction(door.getId(), dur);
                }
                case "CRACK SAFE" -> {
                    SafeContainer safe = (SafeContainer) target.findContainer(((ComboBox<String>) pane.getChildren().get(1)).getSelectionModel().getSelectedItem());
                    int dur = ActDurationCalculator.crackDuration(safe, heistCrew.getSelectionModel().getSelectedItem());
                    act = new CrackAction(safe.getId(), dur);
                }
                case "PICK CASE" -> {
                    DisplayCaseContainer display = (DisplayCaseContainer) target.findContainer((((ComboBox<String>) pane.getChildren().get(1)).getSelectionModel().getSelectedItem()));
                    int dur = ActDurationCalculator.pickCaseDuration(display, heistCrew.getSelectionModel().getSelectedItem());
                    act = new PickCaseAction(display.getId(), dur);
                }
                case "GRAB" -> {
                    LootItem item = app.def.findLoot(((ComboBox<String>) pane.getChildren().get(1)).getSelectionModel().getSelectedItem());
                    act = new GrabAction(item);
                }
            }
            plans.get(heistCrew.getSelectionModel().getSelectedItem().getData().getName()).add(act);
            resetActionList(heistCrew.getSelectionModel().getSelectedItem());
        });

        actionTypes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            add.setDisable(!actionPossible);
        });

        actionSelectionPane.getChildren().add(actionTypes);
        actionSelectionPane.getChildren().add(formFor(actionTypes.getValue()));
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
            case "PICK CASE" -> buildPickCaseForm();
            case "GRAB" -> buildGrabForm();
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

    private VBox buildMoveForm() {
        Label label = new Label("Destination: ");
        ComboBox<String> destSelect = new ComboBox<>();
        List<String> ids = new ArrayList<>();
        for (Room r : target.getRooms()) {
            ids.add(r.getId());
        }
        destSelect.getItems().addAll(ids);
        destSelect.getSelectionModel().selectFirst();
        actionPossible = true;

        VBox box = new VBox();
        box.getChildren().addAll(label, destSelect);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private VBox buildWaitForm() {
        Label label = new Label("Duration:");
        Spinner<Integer> spinner = new Spinner<>(1, 99, 1);

        actionPossible = true;

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
        if (doorSelect.getItems().isEmpty()) {
            doorSelect.setDisable(true);
            actionPossible = false;
        }
        else {
            doorSelect.getSelectionModel().selectFirst();
            actionPossible = true;
        }

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
        if (containerSelect.getItems().isEmpty()) {
            containerSelect.setDisable(true);
            actionPossible = false;
        }
        else {
            containerSelect.getSelectionModel().selectFirst();
            actionPossible = true;
        }

        VBox box = new VBox();
        box.getChildren().addAll(label, containerSelect);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private VBox buildGrabForm() {
        Label label = new Label("Item:");
        ComboBox<String> itemSelect = new ComboBox<>();

        List<String> ids = new ArrayList<>();
        for (Room r : target.getRooms()) {
            ids.addAll(instance.getRoomLoot(r.getId()));
            for (LootContainer c : r.getRoomContainers()) {
                ids.addAll(instance.getContainerLoot(c.getId()));
            }
        }
        itemSelect.getItems().addAll(ids);
        if (itemSelect.getItems().isEmpty()) {
            itemSelect.setDisable(true);
            actionPossible = false;
        }
        else {
            itemSelect.getSelectionModel().selectFirst();
            actionPossible = true;
        }

        VBox box = new VBox();
        box.getChildren().addAll(label, itemSelect);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private VBox buildPickCaseForm() {
        Label label = new Label("Container:");
        ComboBox<String> containerSelect = new ComboBox<>();

        List<String> ids = new ArrayList<>();
        for (Room r : target.getRooms()) {
            for (LootContainer c : r.getRoomContainers()) {
                if (c instanceof DisplayCaseContainer display) {
                    if (display.getPickDifficulty() > 0) ids.add(display.getId());
                }
            }
        }
        containerSelect.getItems().addAll(ids);
        if (containerSelect.getItems().isEmpty()) {
            containerSelect.setDisable(true);
            actionPossible = false;
        }
        else {
            containerSelect.getSelectionModel().selectFirst();
            actionPossible = true;
        }

        VBox box = new VBox();
        box.getChildren().addAll(label, containerSelect);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private VBox executeQueuePane() {
        actionList = new ListView<>();

        HBox buttonBox = new HBox(10);

        Button returnBtn = new Button("Back");
        returnBtn.setOnAction(e -> {
            game.getCampaign().currentTargetId = null;
            app.showScreen(Screen.CAMPAIGN);
        });
        Button executeBtn = new Button("Run Heist");
        executeBtn.setOnAction(e -> {
            mapPane.reset(target);
            returnBtn.setDisable(true);
            executeBtn.setDisable(true);

            for (RosterEntry entry : entries) {
                if (plans.get(entry.getData().getName()).isEmpty()) {
                    System.out.println("Build a plan first!");
                    returnBtn.setDisable(false);
                    executeBtn.setDisable(false);
                    return;
                }
            }


            Map<RosterEntry, ActionQueue> map = new HashMap<>();
            for (RosterEntry entry : entries) {
                map.put(entry, new ActionQueue(plans.get(entry.getData().getName())));
            }
            game.runHeist(map);

            plans = new HashMap<>();
            for (RosterEntry entry : entries) {
                plans.put(entry.getData().getName(), new ArrayList<>());
            }
            heistCrew.getSelectionModel().selectFirst();
            resetActionList(heistCrew.getSelectionModel().getSelectedItem());

            HeistPlayer player = new HeistPlayer(mapPane, game.getLog(), target, new Runnable() {
                @Override
                public void run() {
                    returnBtn.setDisable(false);
                    returnBtn.setText("Continue");
                }
            }, game.getCampaign().maxStep, logList);
            player.play();

        });

        buttonBox.getChildren().addAll(executeBtn, returnBtn);
        buttonBox.setAlignment(Pos.CENTER);
        executionPane = new VBox(10, actionList, buttonBox);
        return executionPane;
    }

    private void resetActionList(RosterEntry entry) {
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
                    if (action instanceof PickCaseAction pick) {
                        setText("PICK CASE display case " + pick.getContainerId());
                    }
                    if (action instanceof GrabAction grab) {
                        setText("GRAB item " + grab.getItem().getName());
                    }

                }
            }
        });
        actionList.getItems().addAll(plans.get(entry.getData().getName()));
        actionList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            plans.get(entry.getData().getName()).remove(newVal);
            resetActionList(entry);
        });
        if (executionPane != null) {
            executionPane.getChildren().removeFirst();
            executionPane.getChildren().addFirst(actionList);
        }
    }
}
