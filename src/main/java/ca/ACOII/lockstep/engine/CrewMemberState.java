package ca.ACOII.lockstep.engine;

import ca.ACOII.lockstep.model.CrewMember;

public class CrewMemberState {
    CrewMember data;
    String id, currentRoom;
    enum Status { ACTIVE, ESCAPED, CAUGHT };
    Status status = Status.ACTIVE;
    boolean spotted = false;

    public CrewMemberState(CrewMember data) {
        this.data = data;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
