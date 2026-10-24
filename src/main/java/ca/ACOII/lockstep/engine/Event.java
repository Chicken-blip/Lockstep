package ca.ACOII.lockstep.engine;

public record Event(int stepNum, String description, String actorId, EventType evtType) {
    @Override
    public String toString() {
        return switch (evtType) {
            case EventType.SPOTTED -> "[Step " + stepNum + "] " + actorId + " spotted in " + description;
            case EventType.ALARM_RAISED -> "[Step " + stepNum + "] ALARM RAISED";
            case EventType.CAUGHT -> "[Step " + stepNum + "] " + actorId + " caught!";
            case EventType.ESCAPED -> "[Step " + stepNum + "] " + actorId + " escaped!";
            default -> "[Step " + stepNum + "] " + actorId + ": " + description;
        };
    }
}