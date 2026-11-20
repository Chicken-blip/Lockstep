package ca.ACOII.lockstep.model;

public class Lock {
    private int pickDifficulty, forceDifficulty;

    public Lock() {
        pickDifficulty = 0; forceDifficulty = 0;
    }

    public int getPickDifficulty() {
        return pickDifficulty;
    }

    public int getForceDifficulty() {
        return forceDifficulty;
    }

    public void setForceDifficulty(int forceDifficulty) {
        this.forceDifficulty = forceDifficulty;
    }

    public void setPickDifficulty(int pickDifficulty) {
        this.pickDifficulty = pickDifficulty;
    }

}
