package ca.ACOII.lockstep.model;

public class KeycardLock extends Lock {
    private String keycardId;
    public KeycardLock() {
        keycardId = null;
    }
    public KeycardLock(String keycardId) {
        this.keycardId = keycardId;
    }

    public String getKeycardId() {
        return keycardId;
    }

    public void setKeycardId(String keycardId) {
        this.keycardId = keycardId;
    }
}
