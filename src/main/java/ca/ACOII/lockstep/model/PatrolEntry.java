package ca.ACOII.lockstep.model;

public class PatrolEntry {
    private String room;
    private int duration;

    public PatrolEntry() {
        room = null;
        duration = 0;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
