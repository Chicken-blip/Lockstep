package ca.ACOII.lockstep.engine;

public class HideAction implements Action {
    private final int duration;
    public HideAction(int duration) {
        this.duration = duration;
    }
    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public String getDescription() {
        return "Hiding...";
    }
}
