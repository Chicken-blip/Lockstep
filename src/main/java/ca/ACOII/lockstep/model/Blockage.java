package ca.ACOII.lockstep.model;

public class Blockage {
    private String id, roomOrigin, roomDest;
    private int moveCost, breakDifficulty;
    public Blockage() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomOrigin() {
        return roomOrigin;
    }

    public void setRoomOrigin(String roomOrigin) {
        this.roomOrigin = roomOrigin;
    }

    public String getRoomDest() {
        return roomDest;
    }

    public void setRoomDest(String roomDest) {
        this.roomDest = roomDest;
    }

    public int getMoveCost() {
        return moveCost;
    }

    public void setMoveCost(int moveCost) {
        this.moveCost = moveCost;
    }

    public int getBreakDifficulty() {
        return breakDifficulty;
    }

    public void setBreakDifficulty(int breakDifficulty) {
        this.breakDifficulty = breakDifficulty;
    }
}
