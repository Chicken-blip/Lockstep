package ca.ACOII.lockstep.model;

public class DisplayCaseContainer extends LootContainer {
    int pickDifficulty;
    public DisplayCaseContainer() {
        pickDifficulty = 0;
    }

    public int getPickDifficulty() {
        return pickDifficulty;
    }

    public void setPickDifficulty(int pickDifficulty) {
        this.pickDifficulty = pickDifficulty;
    }

    @Override
    public void unlock() {
        //TODO: Add logic
    }
}
