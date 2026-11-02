package ca.ACOII.lockstep.model;

public class ForceOpenContainer extends LootContainer {
    int forceDifficulty;
    public ForceOpenContainer() {
        forceDifficulty = 0;
    }

    public int getForceDifficulty() {
        return forceDifficulty;
    }

    public void setForceDifficulty(int forceDifficulty) {
        this.forceDifficulty = forceDifficulty;
    }

    @Override
    public void unlock() {
        //TODO: Add logic
    }
}
