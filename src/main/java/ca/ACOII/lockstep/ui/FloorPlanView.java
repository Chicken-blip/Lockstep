package ca.ACOII.lockstep.ui;

import ca.ACOII.lockstep.Game;
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
    Map<String, Circle> actors;
    Map<String, List<String>> actorsPerRoom;
    Map<String, Rectangle> rooms;
    Map<String, Tooltip> roomTooltips;
    List<String> removedItemIds;
    Double[] offsetX = new Double[]{-15d, 15d, -15d, 15d};
    Double[] offsetY = new Double[]{-15d, -15d, 15d, 15d};
    Definitions def;
    HeistInstance instance;
    public FloorPlanView(HeistInstance instance, Game g, List<RosterEntry> entries, Definitions def) {
        super();
        this.def = def;
        this.instance = instance;
        actors = new HashMap<>();
        actorsPerRoom = new HashMap<>();
        rooms = new HashMap<>();
        roomTooltips = new HashMap<>();
        removedItemIds = new ArrayList<>();
        for (Door d : instance.getTarget().getDoors()) {
            Line connection = new Line(instance.getTarget().findRoom(d.getRoomOrigin()).getX() + ROOM_WIDTH / 2,
                    instance.getTarget().findRoom(d.getRoomOrigin()).getY() + ROOM_HEIGHT / 2,
                    instance.getTarget().findRoom(d.getRoomDest()).getX() + ROOM_WIDTH / 2,
                    instance.getTarget().findRoom(d.getRoomDest()).getY() + ROOM_HEIGHT / 2);
            connection.setStroke(d.getLock() != null ? Color.RED : Color.BLACK);
            connection.setStrokeWidth(5);
            connection.setPickOnBounds(true);
            Tooltip tip = new Tooltip();
            if (d.getLock() != null) {
                tip.setText(d.getId() + " (LOCKED - Difficulty: " + d.getLock().getPickDifficulty() + ")");
            } else {
                tip.setText(d.getId() + " (OPEN)");
            }
            tip.setShowDelay(Duration.millis(50));
            Tooltip.install(connection, tip);
            this.getChildren().add(connection);
        }
        for (Room r : instance.getTarget().getRooms()) {
            Rectangle room = new Rectangle(r.getX(), r.getY(), ROOM_WIDTH, ROOM_HEIGHT);
            room.setStroke(Color.BLACK);
            room.setFill(Color.WHITE);
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


    }

    private StringBuilder getRoomTooltipContent(String roomId) {
        Room r = instance.getTarget().findRoom(roomId);
        List<String> roomLoot;
        Map<String, List<String>> containerLoot;
        roomLoot = new ArrayList<>();
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

        StringBuilder txt = new StringBuilder(r.getId() + "\n");
        if (roomLoot.isEmpty() && containerLoot.isEmpty()) {
            txt.append("(EMPTY)");
        } else if (roomLoot.isEmpty()) {
            for (LootContainer container : r.getRoomContainers()) {
                if (container instanceof SafeContainer safe) {
                    txt.append(safe.getId()).append(" [SAFE - Difficulty: ").append(safe.getCrackDifficulty()).append("]: ");
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
        } else if (containerLoot.isEmpty()) {
            txt.append("LOOT: ");
            for (String str : roomLoot) {
                txt.append(def.findLoot(str).getName()).append(", ");
            }
            txt = new StringBuilder(txt.substring(0, txt.length() - 2));
        } else {
            txt.append("LOOT: ");

            for (String str : roomLoot) {
                txt.append(def.findLoot(str).getName()).append(", ");
            }

            txt = new StringBuilder(txt.substring(0, txt.length() - 2)).append("\n");

            for (LootContainer container : r.getRoomContainers()) {
                if (container instanceof SafeContainer safe) {
                    txt.append(safe.getId()).append(" [SAFE - Difficulty: ").append(safe.getCrackDifficulty()).append("]: ");
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

        double centerX = target.findRoom(roomId).getX() + ROOM_WIDTH / 2.0;
        double centerY = target.findRoom(roomId).getY() + ROOM_HEIGHT / 2.0;

        int slot = actorsInRoom(roomId).size() % 4;

        c.setCenterX(centerX + offsetX[slot]);
        c.setCenterY(centerY + offsetY[slot]);
        c.setRadius(5);

        c.setFill(color);

        if (target.findPatrol(actorId) != null) {
            Tooltip tip = new Tooltip();
            Patrol p = target.findPatrol(actorId);

            StringBuilder txt = new StringBuilder("GUARD\nPATROL: ");
            for (PatrolEntry entry : p.getPatrolRoute()) {
                txt.append(entry.getRoom() + " (" + entry.getDuration() + ") -> ");
            }
            txt = new StringBuilder(txt.substring(0, txt.length() - 3));

            tip.setText(String.valueOf(txt));
            tip.setShowDelay(Duration.millis(200));
            Tooltip.install(c, tip);
        }

        actors.put(actorId, c);
        List<String> list = actorsPerRoom.getOrDefault(roomId, new ArrayList<String>());
        list.add(actorId);
        actorsPerRoom.put(roomId, list);

        this.getChildren().add(c);
        System.out.println("room " + roomId + " at (" + centerX + ", " + centerY + ")");
        System.out.println("slot " + slot + " → (" + (centerX + offsetX[slot]) + ", " + (centerY + offsetY[slot]) + ")");
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
            slot = slot % 4;
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
        for (Room r : target.getRooms()) {
            relayoutRoom(r.getId(), target);
        }
    }

    public void updateRoomContents(String itemId, String roomId) {
        removedItemIds.add(itemId);
        Tooltip tip = roomTooltips.get(roomId);
        tip.setText(getRoomTooltipContent(roomId).toString());
    }
}
