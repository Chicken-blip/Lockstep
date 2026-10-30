package ca.ACOII.lockstep.engine;

public class PickCaseAction implements Action {

    String containerId;
    int duration;
    public PickCaseAction(String containerId, int duration) {
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
        return "Picked display case " + containerId;
    }
}
