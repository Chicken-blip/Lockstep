package ca.ACOII.lockstep.model;

public class LootItem {
    private String id, name, set;
    private int grabCost, pieceValue;
    private boolean isMainScore;

    public LootItem() {
        id = null;
        name = null;
        set = null;
        grabCost = 0;
        pieceValue = 0;
        isMainScore = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public int getGrabCost() {
        return grabCost;
    }

    public void setGrabCost(int grabCost) {
        this.grabCost = grabCost;
    }

    public int getPieceValue() {
        return pieceValue;
    }

    public void setPieceValue(int pieceValue) {
        this.pieceValue = pieceValue;
    }

    public boolean isMainScore() {
        return isMainScore;
    }

    public void setIsMainScore(boolean isMainScore) {
        this.isMainScore = isMainScore;
    }
}
