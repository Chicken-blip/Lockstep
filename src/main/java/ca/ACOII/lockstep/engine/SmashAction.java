package ca.ACOII.lockstep.engine;

public class SmashAction implements Action {
    String blockageId;
    int duration;
    public SmashAction(String blockageId, int duration) {
        this.blockageId = blockageId;
        this.duration = duration;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    public String getBlockageId() {
        return blockageId;
    }
    @Override
    public String getDescription() {
        return "Smashed blockage " + blockageId;
    }
}
