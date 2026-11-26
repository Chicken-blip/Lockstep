package ca.ACOII.lockstep.ui;

import ca.ACOII.lockstep.campaign.RosterEntry;
import ca.ACOII.lockstep.engine.HeistInstance;
import ca.ACOII.lockstep.load.*;
import ca.ACOII.lockstep.model.*;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.*;


public class FloorPlanView extends Pane {
    private static final double ROOM_WIDTH = 75, ROOM_HEIGHT = 60;
    private static final int SLOT_NUM = 8;
    Map<String, Circle> actors;
    Map<String, List<String>> actorsPerRoom;
    Map<String, Rectangle> rooms;
    Map<String, Line> connections;
    Map<String, Circle> sensors;
    Map<String, Tooltip> roomTooltips;
    List<String> removedItemIds;
    Double[] offsetX = new Double[]{-20d, -10d, 10d, 20d, 20d, 10d, -10d, -20d};
    Double[] offsetY = new Double[]{-10d, -20d, -20d, -10d, 10d, 20d, 20d, 10d};
    Definitions def;
    HeistInstance instance;
    public FloorPlanView(HeistInstance instance, List<RosterEntry> entries, Definitions def) {
        super();
        this.def = def;
        this.instance = instance;
        actors = new HashMap<>();
        actorsPerRoom = new HashMap<>();
        rooms = new HashMap<>();
        connections = new HashMap<>();
        sensors = new HashMap<>();
        roomTooltips = new HashMap<>();
        removedItemIds = new ArrayList<>();
        for (Door d : instance.getTarget().getDoors()) {
            Line connection = new Line(instance.getTarget().findRoom(d.getRoomOrigin()).getX() + ROOM_WIDTH / 2,
                    instance.getTarget().findRoom(d.getRoomOrigin()).getY() + ROOM_HEIGHT / 2,
                    instance.getTarget().findRoom(d.getRoomDest()).getX() + ROOM_WIDTH / 2,
                    instance.getTarget().findRoom(d.getRoomDest()).getY() + ROOM_HEIGHT / 2);
            connection.setStroke(d.getLock() != null ? Color.RED : Color.BLACK);
            connection.setStrokeWidth(5);
            connection.setPickOnBounds(false);
            Tooltip tip = getDoorTooltip(d);
            Tooltip.install(connection, tip);
            connections.put(d.getId(), connection);
            this.getChildren().add(connection);

            if (d.getSensor() != null) {
                Circle circle = new Circle();
                Room roomA = instance.getTarget().findRoom(d.getRoomDest());
                Room roomB = instance.getTarget().findRoom(d.getRoomOrigin());

                circle.setCenterX(getXMidpoint(roomA, roomB));
                circle.setCenterY(getYMidpoint(roomA, roomB));
                circle.setRadius(5);
                circle.setFill(Color.YELLOW);

                Tooltip sensorTip = new Tooltip();
                sensorTip.setText("Sensor (" + d.getSensor().id + ")\nHack difficulty: " + d.getSensor().difficulty);
                sensorTip.setShowDelay(Duration.millis(50));
                Tooltip.install(circle, sensorTip);
                sensors.put(d.getSensor().getId(), circle);
                this.getChildren().add(circle);
            }
        }
        for (Blockage b : instance.getTarget().getBlockages()) {
            Line connection = new Line(instance.getTarget().findRoom(b.getRoomOrigin()).getX() + ROOM_WIDTH / 2,
                    instance.getTarget().findRoom(b.getRoomOrigin()).getY() + ROOM_HEIGHT / 2,
                    instance.getTarget().findRoom(b.getRoomDest()).getX() + ROOM_WIDTH / 2,
                    instance.getTarget().findRoom(b.getRoomDest()).getY() + ROOM_HEIGHT / 2);
            connection.setStroke(Color.BLACK);
            connection.setStrokeWidth(5);
            connection.getStrokeDashArray().addAll(15d, 10d);
            connection.setPickOnBounds(false);
            Tooltip tip = new Tooltip();
            tip.setText(b.getId() + (b.getBreakDifficulty() > 0 ? "\nSMASH - Difficulty: " + b.getBreakDifficulty() : "OPEN"));
            tip.setShowDelay(Duration.millis(50));
            Tooltip.install(connection, tip);
            connections.put(b.getId(), connection);
            this.getChildren().add(connection);

        }
        for (Room r : instance.getTarget().getRooms()) {
            Rectangle room = getRoomRectangle(instance, r);
            Label label = new Label(r.getId());
            label.setLayoutX(r.getX() + 5);
            label.setLayoutY(r.getY() + 7);
            label.setTextFill(Color.BLACK);
            label.setMouseTransparent(true);
            Tooltip roomContents = new Tooltip();
            roomContents.setText(getRoomTooltipContent(r.getId()).toString());
            roomContents.setShowDelay(Duration.millis(200));
            Tooltip.install(room, roomContents);
            rooms.put(r.getId(), room);
            roomTooltips.put(r.getId(), roomContents);
            this.getChildren().add(room);
            this.getChildren().add(label);
        }

        for (RosterEntry e : entries) {
            addActor(e.getData().getName(), instance.getTarget().getEntryRoom(), Color.BLACK, instance.getTarget());
        }
        for (Patrol p : instance.getTarget().getPatrols()) {
            addActor(p.getId(), p.getPatrolRoute().getFirst().getRoom(), Color.BLUE, instance.getTarget());
        }
        for (Staff s : instance.getTarget().getStaff()) {
            addActor(s.getId(), s.getPatrolRoute().getFirst().getRoom(), Color.GREEN, instance.getTarget());
        }


    }

    private Rectangle getRoomRectangle(HeistInstance instance, Room r) {
        Rectangle room = new Rectangle(r.getX(), r.getY(), ROOM_WIDTH, ROOM_HEIGHT);
        room.setStroke(Color.BLACK);
        if (Objects.equals(instance.getTarget().getEntryRoom(), r.getId()) && instance.getTarget().getExitRooms().contains(r.getId())) {
            room.setFill(Color.CYAN);
        } else if (Objects.equals(instance.getTarget().getEntryRoom(), r.getId())) {
            room.setFill(Color.MAGENTA);
        } else if (instance.getTarget().getExitRooms().contains(r.getId())) {
            room.setFill(Color.YELLOW);
        } else {
            room.setFill(Color.WHITE);
        }
        return room;
    }

    private Tooltip getDoorTooltip(Door d) {
        Tooltip tip = new Tooltip();
        StringBuilder txt = new StringBuilder(d.getId());
        if (d.getLock() != null) {
            txt.append(" (LOCKED)");
            if (d.getLock() instanceof PickLock) {
                txt.append((((PickLock) d.getLock()).getPickDifficulty() > 0 ? "\nPICK LOCK - Difficulty: " + ((PickLock) d.getLock()).getPickDifficulty() : ""));
                txt.append(((PickLock) d.getLock()).getForceDifficulty() > 0 ? "\nFORCE OPEN - Difficulty: " + ((PickLock) d.getLock()).getForceDifficulty() : "");
            } else if (d.getLock() instanceof KeycardLock) {
                txt.append((!((KeycardLock) d.getLock()).getKeycardId().isBlank() ? "\nKEYCARD LOCK - Keycard: " + def.findKeycard(((KeycardLock) d.getLock()).getKeycardId()).getName() : ""));
            }
        } else {
            txt.append(" (OPEN)");
        }
        tip.setText(txt.toString());
        tip.setShowDelay(Duration.millis(50));
        return tip;
    }

    private double getXMidpoint(Room roomA, Room roomB) {
        double Ax = roomA.getX() + ROOM_WIDTH / 2.0;
        double Bx = roomB.getX() + ROOM_WIDTH / 2.0;
        return (Ax + Bx)/2.0;
    }
    private double getYMidpoint(Room roomA, Room roomB) {
        double Ay = roomA.getY() + ROOM_HEIGHT / 2.0;
        double By = roomB.getY() + ROOM_HEIGHT / 2.0;
        return (Ay + By)/2.0;
    }

    private StringBuilder getRoomTooltipContent(String roomId) {
        Room r = instance.getTarget().findRoom(roomId);
        List<String> roomLoot, roomKeycards;
        Map<String, List<String>> containerLoot;
        roomLoot = new ArrayList<>();
        roomKeycards = new ArrayList<>();
        for (String id : instance.getRoomKeycards(roomId)) {
            if (!removedItemIds.contains(id)) roomKeycards.add(id);
        }
        for (String id : instance.getRoomLoot(roomId)) {
            if (!removedItemIds.contains(id)) roomLoot.add(id);
        }
        containerLoot = new HashMap<>();
        for (LootContainer container : r.getRoomContainers()) {
            List<String> containerIds = new ArrayList<>();
            for (String id : instance.getContainerLoot(container.getId())) {
                if (!removedItemIds.contains(id)) containerIds.add(id);
            }
            containerLoot.put(container.getId(), containerIds);
        }

        StringBuilder txt = new StringBuilder(r.getId() + (r.canHide() ? " - HAS COVER" : "") + "\n");
        if (roomLoot.isEmpty() && roomKeycards.isEmpty() && containerLoot.isEmpty()) {
            txt.append("(EMPTY)");
        } else if (roomLoot.isEmpty() && roomKeycards.isEmpty()) {
            for (LootContainer container : r.getRoomContainers()) {
                if (container instanceof SafeContainer safe) {
                    txt.append(safe.getId()).append(" [SAFE - Difficulty: ").append(safe.getCrackDifficulty()).append("]: ");
                }
                if (container instanceof DisplayCaseContainer display) {
                    txt.append(display.getId()).append(" [DISPLAY CASE - Difficulty: ").append(display.getPickDifficulty()).append("]: ");
                }
                if (container instanceof ForceOpenContainer forceOpen) {
                    txt.append(forceOpen.getId()).append(" [LOCKED CONTAINER - Difficulty: ").append(forceOpen.getForceDifficulty()).append("]: ");
                }
                if (containerLoot.get(container.getId()).isEmpty()) {
                    txt.append("(EMPTY)");
                } else {
                    for (String id : containerLoot.get(container.getId())) {
                        txt.append(def.findLoot(id).getName()).append(", ");
                    }
                    txt = new StringBuilder(txt.substring(0, txt.length() - 2)).append("\n");
                }
            }
        } else if (containerLoot.isEmpty()) {
            txt.append("LOOT: ");
            for (String str : roomLoot) {
                txt.append(def.findLoot(str).getName()).append(", ");
            }
            for (String str : roomKeycards) {
                txt.append(def.findKeycard(str).getName()).append(", ");
            }
            txt = new StringBuilder(txt.substring(0, txt.length() - 2));
        } else {
            txt.append("LOOT: ");

            for (String str : roomLoot) {
                txt.append(def.findLoot(str).getName()).append(", ");
            }
            for (String str : roomKeycards) {
                txt.append(def.findKeycard(str).getName()).append(", ");
            }

            txt = new StringBuilder(txt.substring(0, txt.length() - 2)).append("\n");

            for (LootContainer container : r.getRoomContainers()) {
                if (container instanceof SafeContainer safe) {
                    txt.append(safe.getId()).append(" [SAFE - Difficulty: ").append(safe.getCrackDifficulty()).append("]: ");
                }
                if (container instanceof DisplayCaseContainer display) {
                    txt.append(display.getId()).append(" [DISPLAY CASE - Difficulty: ").append(display.getPickDifficulty()).append("]: ");
                }
                if (container instanceof DisplayCaseContainer display) {
                    txt.append(display.getId()).append(" [DISPLAY CASE - Difficulty: ").append(display.getPickDifficulty()).append("]: ");
                }
                if (containerLoot.get(container.getId()).isEmpty()) {
                    txt.append("(EMPTY)");
                } else {
                    for (String id : containerLoot.get(container.getId())) {
                        txt.append(def.findLoot(id).getName()).append(", ");
                    }
                    txt = new StringBuilder(txt.substring(0, txt.length() - 2)).append("\n");
                }
            }
        }
        return txt;
    }

    public void addActor(String actorId, String roomId, Color color, Target target) {
        Circle c = new Circle();
        c.setStroke(Color.BLACK);

        double centerX = target.findRoom(roomId).getX() + ROOM_WIDTH / 2.0;
        double centerY = target.findRoom(roomId).getY() + ROOM_HEIGHT / 2.0;

        int slot = actorsInRoom(roomId).size() % SLOT_NUM;

        c.setCenterX(centerX + offsetX[slot]);
        c.setCenterY(centerY + offsetY[slot]);
        c.setRadius(5);

        c.setFill(color);

        if (target.findPatrol(actorId) != null) {
            Tooltip tip = new Tooltip();
            Patrol p = target.findPatrol(actorId);

            StringBuilder txt = new StringBuilder("GUARD\nPATROL: ");
            if (p.getPatrolRoute().size() == 1) {
                txt.append(p.getPatrolRoute().getFirst().getRoom()).append(" (STATIONARY)");
            } else {
                for (PatrolEntry entry : p.getPatrolRoute()) {
                    txt.append(entry.getRoom()).append(" (").append(entry.getDuration()).append(") -> ");
                }
                txt = new StringBuilder(txt.substring(0, txt.length() - 3));
            }

            tip.setText(String.valueOf(txt));
            tip.setShowDelay(Duration.millis(200));
            Tooltip.install(c, tip);
        }
        if (target.findStaff(actorId) != null) {
            Tooltip tip = new Tooltip();
            Staff s = target.findStaff(actorId);

            StringBuilder txt = new StringBuilder("STAFF\nPATROL: ");
            if (s.getPatrolRoute().size() == 1) {
                txt.append(s.getPatrolRoute().getFirst().getRoom()).append(" (STATIC)");
            } else {
                for (PatrolEntry entry : s.getPatrolRoute()) {
                    txt.append(entry.getRoom()).append(" (").append(entry.getDuration()).append(") -> ");
                }
                txt = new StringBuilder(txt.substring(0, txt.length() - 3));
            }
            txt.append("\nSUSPICION THRESHOLD: ").append(s.getSuspicionThreshold());

            tip.setText(String.valueOf(txt));
            tip.setShowDelay(Duration.millis(200));
            Tooltip.install(c, tip);
        }


        actors.put(actorId, c);
        List<String> list = actorsPerRoom.getOrDefault(roomId, new ArrayList<String>());
        list.add(actorId);
        actorsPerRoom.put(roomId, list);

        this.getChildren().add(c);
    }

    public void moveActor(String actorId, String newRoomId, Target target) {
        String oldRoomId = actorRoom(actorId);
        actorsInRoom(oldRoomId).remove(actorId);

        Circle c = actors.getOrDefault(actorId, null);
        if (c == null) throw new RuntimeException("Unable to find circle for " + actorId);

        actorsInRoom(newRoomId).add(actorId);

        if (!Objects.equals(actorsInRoom(oldRoomId), new ArrayList<String>())) relayoutRoom(oldRoomId, target);
        relayoutRoom(newRoomId, target);
    }

    private void relayoutRoom(String roomId, Target t) {
        double centerX = t.findRoom(roomId).getX() + ROOM_WIDTH / 2.0;
        double centerY = t.findRoom(roomId).getY() + ROOM_HEIGHT / 2.0;
        int slot = 0;
        for (String actorId : actorsInRoom(roomId)) {
            slot = slot % SLOT_NUM;
            actors.get(actorId).setCenterX(centerX + offsetX[slot]);
            actors.get(actorId).setCenterY(centerY + offsetY[slot]);
            slot++;
        }
    }

    public List<String> actorsInRoom(String id) {
        return actorsPerRoom.computeIfAbsent(id, k -> new ArrayList<>());
    }

    public String actorRoom(String id) {
        for (String roomId : actorsPerRoom.keySet()) {
            if (actorsPerRoom.get(roomId).contains(id)) {
                return roomId;

            }
        }
        return null;
    }

    public void reset(Target target) {
        for (Map.Entry<String, Circle> e : actors.entrySet()) {
            if (e.getKey().length() != 2) moveActor(e.getKey(), target.getEntryRoom(), target);
        }
        for (Patrol p : target.getPatrols()) {
            moveActor(p.getId(), p.getPatrolRoute().getFirst().getRoom(), target);
        }
        for (Staff s : target.getStaff()) {
            moveActor(s.getId(), s.getPatrolRoute().getFirst().getRoom(), target);
        }
        for (Room r : target.getRooms()) {
            relayoutRoom(r.getId(), target);
        }
    }

    public void updateRoomContents(String itemId, String roomId) {
        removedItemIds.add(itemId);
        Tooltip tip = roomTooltips.get(roomId);
        tip.setText(getRoomTooltipContent(roomId).toString());
    }

    public void updateDoor(String doorId) {
        Line line = connections.get(doorId);
        line.setStroke(Color.BLACK);
    }
    public void updateBlockage(String blockageId) {
        Line line = connections.get(blockageId);
        line.setStrokeDashOffset(0);
    }

    public void updateSensor(String sensorId) {
        Circle circle = sensors.get(sensorId);
        circle.setFill(Color.GREY);
    }

    public void setBribedGuard(String guardId) {
        actors.get(guardId).setFill(Color.GREY);
    }
}
