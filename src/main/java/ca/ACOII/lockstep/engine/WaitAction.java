package ca.ACOII.lockstep.engine;

public class WaitAction implements Action {
    private final int duration;
    public WaitAction(int duration) {
        this.duration = duration;
    }
    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public String getDescription() {
        return "WAIT";
    }
}
