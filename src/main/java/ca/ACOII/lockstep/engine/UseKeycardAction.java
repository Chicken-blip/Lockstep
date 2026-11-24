package ca.ACOII.lockstep.engine;

public class UseKeycardAction implements Action {
    int duration;
    String doorId;
    String keycardId;
    public UseKeycardAction(String doorId, String keycardId, int duration) {
        this.duration = duration;
        this.doorId = doorId;
        this.keycardId = keycardId;
    }
    @Override
    public int getDuration() {
        return duration;
    }
    public String getDoorId() {return doorId;}
    public String getKeycardId() {return keycardId;}
    @Override
    public String getDescription() {
        return "Used keycard " + keycardId + " on door " + doorId;
    }
}
