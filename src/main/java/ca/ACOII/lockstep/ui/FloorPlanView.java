package ca.ACOII.lockstep.ui;

import ca.ACOII.lockstep.model.Door;
import ca.ACOII.lockstep.model.Room;
import ca.ACOII.lockstep.model.Target;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class FloorPlanView extends Pane {
    private static final double ROOM_WIDTH = 150, ROOM_HEIGHT = 100;
    public FloorPlanView(Target t) {
        super();
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
            this.getChildren().add(room);
            Text label = new Text(room.getX() + 10, room.getY() + 20, room.getId());
            this.getChildren().add(label);
        }
    }
}
