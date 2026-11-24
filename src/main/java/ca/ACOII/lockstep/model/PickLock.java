package ca.ACOII.lockstep.model;

public class PickLock extends Lock {
    private int pickDifficulty, forceDifficulty;
    public PickLock() {
        pickDifficulty = 0;
        forceDifficulty = 0;
    }

    public int getPickDifficulty() {
        return pickDifficulty;
    }

    public void setPickDifficulty(int pickDifficulty) {
        this.pickDifficulty = pickDifficulty;
    }

    public int getForceDifficulty() {
        return forceDifficulty;
    }

    public void setForceDifficulty(int forceDifficulty) {
        this.forceDifficulty = forceDifficulty;
    }
}
