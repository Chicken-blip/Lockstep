package ca.ACOII.lockstep.engine;

public record Event(int stepNum, String description) {
    @Override
    public String toString() {
        return "[Step " + stepNum + "] " + description;
    }
}
