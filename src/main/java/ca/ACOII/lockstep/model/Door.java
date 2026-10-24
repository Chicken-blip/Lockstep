package ca.ACOII.lockstep.model;

public class Door {
    private String id, roomOrigin, roomDest;
    private int moveCost;
    private Lock lock;

    public Door() {
    }

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

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }
}
