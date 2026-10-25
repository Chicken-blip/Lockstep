package ca.ACOII.lockstep.engine;

import ca.ACOII.lockstep.model.CrewMember;
import ca.ACOII.lockstep.model.Gear;
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
    List<Gear> equippedGear;

    public CrewMemberState(CrewMember data, List<Gear> gear) {
        this.data = data;
        this.id = data.getId();
        inventory = new ArrayList<>();
        wasArrested = false;
        equippedGear = new ArrayList<>();
        equippedGear.addAll(gear);
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
