package ca.ACOII.lockstep.model;

public class SafeContainer extends LootContainer {
    private int crackDifficulty;

    public SafeContainer() {
        crackDifficulty = 0;
    }

    public int getCrackDifficulty() {
        return crackDifficulty;
    }

    public void setCrackDifficulty(int crackDifficulty) {
        this.crackDifficulty = crackDifficulty;
    }

    @Override
    public void unlock() {
        //TODO: Add logic
    }
}
