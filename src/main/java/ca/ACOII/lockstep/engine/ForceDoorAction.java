package ca.ACOII.lockstep.engine;

public class ForceDoorAction implements Action {
    String doorId;
    int duration;
    public ForceDoorAction(String doorId, int duration) {
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
        return "Forced open door " + doorId;
    }
}
