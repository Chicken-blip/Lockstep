package ca.ACOII.lockstep.engine;

public class ForceOpenAction implements Action {
    String containerId;
    int duration;

    public ForceOpenAction(String containerId, int duration) {
        this.containerId = containerId;
        this.duration = duration;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    public String getContainerId() {
        return containerId;
    }

    @Override
    public String getDescription() {
        return "Forced open container " + containerId;
    }
}
