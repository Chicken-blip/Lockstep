package ca.ACOII.lockstep.engine;

public class PickLockAction implements Action {

    String doorId;
    int duration;
    public PickLockAction(String doorId, int duration) {
        this.doorId = doorId;
        this.duration = duration;
    }
    @Override
    public int getDuration() {
        return duration;
    }

    public String getDoorId() {
        return doorId;
    }

    @Override
    public String getDescription() {
        return "Picked lock on door " + doorId;
    }
}
