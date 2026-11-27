package ca.ACOII.lockstep.ui;

import ca.ACOII.lockstep.Game;
import ca.ACOII.lockstep.campaign.RosterEntry;
import ca.ACOII.lockstep.engine.*;
import ca.ACOII.lockstep.model.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeistView extends BorderPane {

    FloorPlanView mapPane;
    HBox bottomPane;
    Game game;
    Target target;
    List<RosterEntry> entries;
    App app;
    Map<String, List<Action>> plans;

    VBox executionPane, bribePane;
    HBox logPane;
    ListView<String> logList;

    private static final List<String> ACTIONS = List.of(
            "MOVE", "WAIT", "HIDE", "PICK LOCK", "USE KEYCARD", "CRACK SAFE", "PICK CASE", "FORCE OPEN CONTAINER", "FORCE OPEN DOOR", "SMASH BLOCKAGE", "DISABLE SENSOR", "GRAB"
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
        HBox topPane = new HBox(20);

        executionPane = executeQueuePane();
        topPane.getChildren().addAll(actionBuilderPane(), mapPane(), executionPane);
        topPane.setPadding(new Insets(20));

        bottomPane = new HBox();
        logPane = executionLogPane();
        HBox.setHgrow(logPane, Priority.ALWAYS);
        bottomPane.getChildren().add(logPane);

        //Add bribe list - remove last child from bottomPane when running - but only if there are bribable guards
        bribePane = bribePane();
        if (bribePane != null) bottomPane.getChildren().add(bribePane);

        //Pane formatting
        topPane.setMaxWidth(Double.MAX_VALUE);
        bottomPane.setMaxWidth(Double.MAX_VALUE);



        this.setCenter(topPane);
        this.setBottom(bottomPane);


    }

    private HBox executionLogPane() {
        HBox pane = new HBox();
        pane.setPadding(new Insets(50, 10, 50, 10));

        logList = new ListView<>();
        logList.setMaxHeight(200);
        logList.setMaxWidth(Double.MAX_VALUE);

        pane.getChildren().add(logList);
        HBox.setHgrow(logList, Priority.ALWAYS);
        return pane;
    }

    private VBox bribePane() {
        VBox pane = new VBox();
        pane.setPadding(new Insets(50, 10, 50, 10));

        Label label = new Label("Bribed Guard:");
        pane.getChildren().add(label);

        ComboBox<Patrol> bribableGuards = new ComboBox<>();
        bribableGuards.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Patrol item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("Bribe Guard " + item.getId() + " (Cost " + item.getBribeCost() + ")");
                }
            }
        });
        bribableGuards.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Patrol item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Do not bribe");
                } else {
                    setText("Bribe Guard " + item.getId() + " (Cost " + item.getBribeCost() + ")");
                }
            }
        });
        bribableGuards.getItems().addAll(target.getPatrols().stream().filter(patrol -> patrol.getBribeCost() > 0).toList());
        if (bribableGuards.getItems().isEmpty()) {
            return null;
        }

        pane.getChildren().add(bribableGuards);
        pane.setAlignment(Pos.CENTER);
        pane.setMinWidth(200);
        return pane;
    }

    private VBox mapPane() {
        VBox pane = new VBox(10);

        mapPane = new FloorPlanView(game.getInstance(), entries, app.def);
        mapPane.setPadding(new Insets(20));
        ScrollPane scroll = new ScrollPane(mapPane);
        scroll.setPannable(true);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        pane.getChildren().add(scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
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

        ComboBox<String> actionTypes = new ComboBox<>();
        actionTypes.getItems().addAll(ACTIONS);
        actionTypes.setOnAction(e -> {
            if (actionSelectionPane.getChildren().size() == 5) {
                actionSelectionPane.getChildren().remove(3);
            }
            actionSelectionPane.getChildren().add(3, formFor(actionTypes.getValue()));
        });
        actionTypes.getSelectionModel().selectFirst();

        heistCrew.getSelectionModel().selectFirst();
        heistCrew.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            resetActionList(newVal);
            if (actionSelectionPane.getChildren().size() == 5) {
                actionSelectionPane.getChildren().remove(3);
            }
            actionSelectionPane.getChildren().add(3, formFor(actionTypes.getValue()));
        });
        actionSelectionPane.getChildren().add(heistCrew);

        Label title = new Label("Add Action");
        actionSelectionPane.getChildren().add(title);

        Button add = new Button("Add Action");
        add.setOnAction(e -> {
            String value = actionTypes.getValue();
            VBox pane = ((VBox) actionSelectionPane.getChildren().get(3));
            Action act = null;
            switch (value) {
                case "MOVE" -> {
                    act = new MoveAction(((ComboBox<String>) pane.getChildren().get(1)).getSelectionModel().getSelectedItem(), 1);
                }
                case "WAIT" -> {
                    act = new WaitAction((Integer) ((Spinner<?>) pane.getChildren().get(1)).getValue());
                }
                case "HIDE" -> {
                    act = new HideAction((Integer) ((Spinner<?>) pane.getChildren().get(1)).getValue());
                }
                case "PICK LOCK" -> {
                    Door door = target.findDoor(((ComboBox<String>) pane.getChildren().get(1)).getSelectionModel().getSelectedItem());
                    int dur = ActDurationCalculator.pickLockDuration(door, heistCrew.getSelectionModel().getSelectedItem());
                    act = new PickLockAction(door.getId(), dur);
                }
                case "USE KEYCARD" -> {
                    Door door = target.findDoor(((ComboBox<String>) pane.getChildren().get(1)).getSelectionModel().getSelectedItem());
                    act = new UseKeycardAction(door.getId(), ((KeycardLock) door.getLock()).getKeycardId(), 1);
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
                case "FORCE OPEN CONTAINER" -> {
                    ForceOpenContainer container = (ForceOpenContainer) target.findContainer((((ComboBox<String>) pane.getChildren().get(1)).getSelectionModel().getSelectedItem()));
                    int dur = ActDurationCalculator.forceOpenDuration(container, heistCrew.getSelectionModel().getSelectedItem());
                    act = new ForceOpenAction(container.getId(), dur);
                }
                case "FORCE OPEN DOOR" -> {
                    Door door = target.findDoor(((ComboBox<String>) pane.getChildren().get(1)).getSelectionModel().getSelectedItem());
                    int dur = ActDurationCalculator.forceDoorDuration(door, heistCrew.getSelectionModel().getSelectedItem());
                    act = new ForceDoorAction(door.getId(), dur);
                }
                case "SMASH BLOCKAGE" -> {
                    Blockage blockage = target.findBlockage(((ComboBox<String>) pane.getChildren().get(1)).getSelectionModel().getSelectedItem());
                    int dur = ActDurationCalculator.smashBlockageDuration(blockage, heistCrew.getSelectionModel().getSelectedItem());
                    act = new SmashAction(blockage.getId(), dur);
                }
                case "DISABLE SENSOR" -> {
                    Sensor sensor = target.findSensor(((ComboBox<String>) pane.getChildren().get(1)).getSelectionModel().getSelectedItem());
                    int dur = ActDurationCalculator.disableSensorDuration(sensor, heistCrew.getSelectionModel().getSelectedItem());
                    act = new DisableSensorAction(sensor.id, dur);
                }
                case "GRAB" -> {
                    Object object = ((ComboBox<Object>) pane.getChildren().get(1)).getSelectionModel().getSelectedItem();
                    if (object instanceof LootItem item) {
                        act = new GrabAction(item);
                    } else if (object instanceof Keycard keycard) {
                        act = new GrabAction(keycard);
                    }
                }
            }
            plans.get(heistCrew.getSelectionModel().getSelectedItem().getData().getName()).add(act);
            resetActionList(heistCrew.getSelectionModel().getSelectedItem());
        });

        actionTypes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> add.setDisable(!actionPossible));

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
            case "HIDE" -> buildHideForm();
            case "PICK LOCK" -> buildPickLockForm();
            case "USE KEYCARD" -> buildUseKeycardForm();
            case "CRACK SAFE" -> buildCrackSafeForm();
            case "PICK CASE" -> buildPickCaseForm();
            case "FORCE OPEN CONTAINER" -> buildForceOpenContainerForm();
            case "FORCE OPEN DOOR" -> buildForceOpenDoorForm();
            case "SMASH BLOCKAGE" -> buildSmashBlockageForm();
            case "DISABLE SENSOR" -> buildDisableSensorForm();
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

    private VBox buildHideForm() {
        Label label = new Label("Duration:");
        Spinner<Integer> spinner = new Spinner<>(1, heistCrew.getSelectionModel().getSelectedItem().getData().getHideDuration(), 1);

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
            if (d.getLock() != null && d.getLock() instanceof PickLock pick) {
                if (pick.getPickDifficulty() > 0) {
                    ids.add(d.getId());
                }
            }
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

    private VBox buildUseKeycardForm() {
        Label label = new Label("Door:");
        ComboBox<String> doorSelect = new ComboBox<>();

        List<String> ids = new ArrayList<>();
        for (Door d : target.getDoors()) {
            if (d.getLock() != null && d.getLock() instanceof KeycardLock keycard) {
                if (keycard.getKeycardId() != null) {
                    ids.add(d.getId());
                }
            }
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

    private VBox buildDisableSensorForm() {
        Label label = new Label("Sensor:");
        ComboBox<String> sensorSelect = new ComboBox<>();

        List<String> ids = new ArrayList<>();
        for (Door d : target.getDoors()) {
            if (d.getSensor() != null) ids.add(d.getSensor().id);
        }
        sensorSelect.getItems().addAll(ids);
        if (sensorSelect.getItems().isEmpty()) {
            sensorSelect.setDisable(true);
            actionPossible = false;
        }
        else {
            sensorSelect.getSelectionModel().selectFirst();
            actionPossible = true;
        }

        VBox box = new VBox();
        box.getChildren().addAll(label, sensorSelect);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private VBox buildGrabForm() {
        Label label = new Label("Item:");
        ComboBox<Object> itemSelect = new ComboBox<>();
        itemSelect.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else if (item instanceof LootItem){
                    setText(((LootItem) item).getName());
                } else if (item instanceof Keycard) {
                    setText(((Keycard) item).getName());
                }
            }
        });
        itemSelect.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else if (item instanceof LootItem){
                    setText(((LootItem) item).getName());
                } else if (item instanceof Keycard) {
                    setText(((Keycard) item).getName());
                }
            }
        });

        List<LootItem> items = new ArrayList<>();
        for (Room r : target.getRooms()) {
            for (String id : instance.getRoomLoot(r.getId())) {
                items.add(game.getCampaign().definitions.findLoot(id));
            }
            for (LootContainer c : r.getRoomContainers()) {
                for (String id : instance.getContainerLoot(c.getId())) {
                    items.add(game.def.findLoot(id));
                }
            }
        }
        itemSelect.getItems().addAll(items);
        List<Keycard> keycards = new ArrayList<>();
        for (Room r : target.getRooms()) {
            for (String card : instance.getRoomKeycards(r.getId())) {
                keycards.add(game.def.findKeycard(card));
            }
        }
        itemSelect.getItems().addAll(keycards);
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

    private VBox buildForceOpenContainerForm() {
        Label label = new Label("Container:");
        ComboBox<String> containerSelect = new ComboBox<>();

        List<String> ids = new ArrayList<>();
        for (Room r : target.getRooms()) {
            for (LootContainer c : r.getRoomContainers()) {
                if (c instanceof ForceOpenContainer container) {
                    if (container.getForceDifficulty() > 0) ids.add(container.getId());
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

    private VBox buildForceOpenDoorForm() {
        Label label = new Label("Door:");
        ComboBox<String> doorSelect = new ComboBox<>();

        List<String> ids = new ArrayList<>();
        for (Door d : target.getDoors()) {
            if (d.getLock() != null) {
                if (d.getLock() instanceof PickLock pick) {
                    if (pick.getForceDifficulty() > 0) ids.add(d.getId());
                }
            }
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

    private VBox buildSmashBlockageForm() {
        Label label = new Label("Blockage:");
        ComboBox<String> blockageSelect = new ComboBox<>();

        List<String> ids = new ArrayList<>();
        for (Blockage b : target.getBlockages()) {
            if (b.getBreakDifficulty() > 0) ids.add(b.getId());
        }
        blockageSelect.getItems().addAll(ids);
        if (blockageSelect.getItems().isEmpty()) {
            blockageSelect.setDisable(true);
            actionPossible = false;
        }
        else {
            blockageSelect.getSelectionModel().selectFirst();
            actionPossible = true;
        }

        VBox box = new VBox();
        box.getChildren().addAll(label, blockageSelect);
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
            for (RosterEntry entry : entries) {
                if (plans.get(entry.getData().getName()).isEmpty()) {
                    return;
                }
            }

            mapPane.reset(target);
            returnBtn.setDisable(true);
            executeBtn.setDisable(true);

            //Do a check for any bribed guard, then remove the bribe selection
            Patrol bribedGuard = bribePane != null ? ((ComboBox<Patrol>) (bribePane.getChildren().getLast())).getSelectionModel().getSelectedItem() : null;
            if (bribedGuard != null && bribedGuard.getBribeCost() > game.getStash().getMoney()) {
                return;
            }
            String bribedGuardId = bribedGuard == null ? null : bribedGuard.getId();
            if (bribedGuard != null) {
                mapPane.setBribedGuard(bribedGuardId);
                game.getLog().addEvent(new Event(0, "Bribed guard " + bribedGuardId + " for $" + bribedGuard.getBribeCost(), null, EventType.BRIBE));
            }
            bottomPane.getChildren().remove(bribePane);

            Map<RosterEntry, ActionQueue> map = new HashMap<>();
            for (RosterEntry entry : entries) {
                map.put(entry, new ActionQueue(plans.get(entry.getData().getName())));
            }

            game.runHeist(map, bribedGuardId);

            plans = new HashMap<>();
            for (RosterEntry entry : entries) {
                plans.put(entry.getData().getName(), new ArrayList<>());
            }
            heistCrew.getSelectionModel().selectFirst();
            resetActionList(heistCrew.getSelectionModel().getSelectedItem());

            HeistPlayer player = new HeistPlayer(mapPane, game.getLog(), target, () -> {
                returnBtn.setDisable(false);
                returnBtn.setText("Continue");
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
                    if (action instanceof HideAction hide) {
                        setText("HIDE " + hide.getDuration());
                    }
                    if (action instanceof PickLockAction pick) {
                        setText("PICK LOCK door " + pick.getDoorId());
                    }
                    if (action instanceof UseKeycardAction useKeycard) {
                        setText("USE KEYCARD " + game.def.findKeycard(useKeycard.getKeycardId()).getName() + " on door " + useKeycard.getDoorId());
                    }
                    if (action instanceof CrackAction crack) {
                        setText("CRACK SAFE " + crack.getContainerId());
                    }
                    if (action instanceof PickCaseAction pick) {
                        setText("PICK CASE " + pick.getContainerId());
                    }
                    if (action instanceof ForceOpenAction forceOpen) {
                        setText("FORCE OPEN " + forceOpen.getContainerId());
                    }
                    if (action instanceof ForceDoorAction forceDoor) {
                        setText("FORCE OPEN door " + forceDoor.getDoorId());
                    }
                    if (action instanceof SmashAction smash) {
                        setText("SMASH blockage " + smash.getBlockageId());
                    }
                    if (action instanceof DisableSensorAction disable) {
                        setText("DISABLE SENSOR " + disable.getSensorId());
                    }
                    if (action instanceof GrabAction grab) {
                        if (game.def.findLoot(grab.getItemId()) != null) {
                            setText("GRAB item " + game.def.findLoot(grab.getItemId()).getName());
                        } else {
                            setText("GRAB keycard " + game.def.findKeycard(grab.getItemId()).getName());
                        }
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
