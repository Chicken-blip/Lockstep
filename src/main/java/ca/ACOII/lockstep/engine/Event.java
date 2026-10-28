package ca.ACOII.lockstep.engine;

public record Event(int stepNum, String description, String actorId, EventType evtType) {
    @Override
    public String toString() {
        return switch (evtType) {
            case EventType.CRACK_PROGRESS -> "[Step " + stepNum + "] " + actorId + ": Cracking safe...";
            case EventType.PICK_LOCK_PROGRESS -> "[Step " + stepNum + "] " + actorId + ": Picking door lock...";
            case EventType.GRAB_PROGRESS -> "[Step " + stepNum + "] " + actorId + ": Grabbing item...";
            case EventType.SPOTTED -> "[Step " + stepNum + "] " + actorId + " spotted in " + description;
            case EventType.ALARM_RAISED -> "[Step " + stepNum + "] ALARM RAISED";
            case EventType.CAUGHT -> "[Step " + stepNum + "] " + actorId + " caught!";
            case EventType.ESCAPED -> "[Step " + stepNum + "] " + actorId + " escaped!";
            default -> "[Step " + stepNum + "] " + actorId + ": " + description;
        };
    }
}