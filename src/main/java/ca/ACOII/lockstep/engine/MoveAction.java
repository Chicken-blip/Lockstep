package ca.ACOII.lockstep.engine;

public class MoveAction implements Action {
    private final String destinationRoom;
    private final int duration;

    public MoveAction(String destinationRoom, int duration) {
        this.destinationRoom = destinationRoom;
        this.duration = duration;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    public String getDestinationRoom() {
        return destinationRoom;
    }
    @Override
    public String getDescription() {
        return "MOVE to " + destinationRoom;
    }
}
