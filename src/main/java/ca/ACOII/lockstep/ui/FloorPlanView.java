package ca.ACOII.lockstep.ui;

import ca.ACOII.lockstep.Game;
import ca.ACOII.lockstep.campaign.RosterEntry;
import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.Door;
import ca.ACOII.lockstep.model.Patrol;
import ca.ACOII.lockstep.model.Room;
import ca.ACOII.lockstep.model.Target;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.*;

public class FloorPlanView extends Pane {
    private static final double ROOM_WIDTH = 150, ROOM_HEIGHT = 100;
    Map<String, Circle> actors;
    Map<String, List<String>> actorsPerRoom;
    Double[] offsetX = new Double[]{-15d, 15d, -15d, 15d};
    Double[] offsetY = new Double[]{-15d, -15d, 15d, 15d};
    public FloorPlanView(Target t, Game g) {
        super();
        actors = new HashMap<>();
        actorsPerRoom = new HashMap<>();
        for (Door d : t.getDoors()) {
            Line connection = new Line(t.findRoom(d.getRoomOrigin()).getX() + ROOM_WIDTH / 2,
                    t.findRoom(d.getRoomOrigin()).getY() + ROOM_HEIGHT / 2,
                    t.findRoom(d.getRoomDest()).getX() + ROOM_WIDTH / 2,
                    t.findRoom(d.getRoomDest()).getY() + ROOM_HEIGHT / 2);
            connection.setStroke(d.getLock() != null ? Color.RED : Color.BLACK);
            this.getChildren().add(connection);
        }
        for (Room r : t.getRooms()) {
            Rectangle room = new Rectangle(r.getX(), r.getY(), ROOM_WIDTH, ROOM_HEIGHT);
            room.setStroke(Color.BLACK);
            room.setFill(Color.WHITE);
            Label label = new Label(r.getId());
            label.setLayoutX(r.getX() + 10);
            label.setLayoutY(r.getY() + 20);
            label.setTextFill(Color.BLACK);
            this.getChildren().add(room);
            this.getChildren().add(label);
        }

        for (RosterEntry e : g.getRoster()) {
            addActor(e.getData().getName(), t.getEntryRoom(), Color.BLACK, t);
        }
        for (Patrol p : t.getPatrols()) {
            addActor(p.getId(), p.getPatrolRoute().getFirst().getRoom(), Color.BLUE, t);
        }


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
}
