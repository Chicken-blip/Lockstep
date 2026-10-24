package ca.ACOII.lockstep.model;

public class Lock {
    private int pickDifficulty;

    public Lock() {
        pickDifficulty = 0;
    }

    public int getPickDifficulty() {
        return pickDifficulty;
    }

    public void setPickDifficulty(int pickDifficulty) {
        this.pickDifficulty = pickDifficulty;
    }
}
