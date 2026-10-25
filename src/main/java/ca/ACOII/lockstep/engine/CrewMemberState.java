package ca.ACOII.lockstep.engine;

import ca.ACOII.lockstep.model.CrewMember;
import ca.ACOII.lockstep.model.LootItem;

import java.util.ArrayList;
import java.util.List;

public class CrewMemberState {
    CrewMember data;
    String id, currentRoom;

    enum Status { ACTIVE, ESCAPED, CAUGHT }
    Status status = Status.ACTIVE;
    boolean spotted = false, wasArrested;
    List<LootItem> inventory;

    public CrewMemberState(CrewMember data) {
        this.data = data;
        this.id = data.getId();
        inventory = new ArrayList<>();
        wasArrested = false;
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

    public List<LootItem> getInventory() {
        return inventory;
    }

    public void addToInventory(LootItem item) {
        inventory.add(item);
    }

    public boolean arrested() {
        return wasArrested;
    }

    public CrewMember getData() {
        return data;
    }
}
