package ca.ACOII.lockstep.engine;

public record Event(int stepNum, String description, String actorId) {
    @Override
    public String toString() {
        return "[Step " + stepNum + "] " + actorId + ": " + description;
    }
}
