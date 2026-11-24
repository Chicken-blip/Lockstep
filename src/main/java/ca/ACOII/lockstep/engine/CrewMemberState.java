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
    boolean spotted = false, wasArrested, hiding = false;
    List<LootItem> inventory;
    List<String> keycards;
    List<Gear> equippedGear;

    public CrewMemberState(CrewMember data, List<Gear> gear) {
        this.data = data;
        this.id = data.getId();
        inventory = new ArrayList<>();
        keycards = new ArrayList<>();
        wasArrested = false;
        hiding = false;
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
    public List<String> getKeycards() {return keycards;}

    public void addToInventory(LootItem item) {
        inventory.add(item);
    }
    public void addKeycard(String id) {keycards.add(id);}
    public boolean hasKeycard(String id) {return keycards.contains(id);}
    public boolean arrested() {
        return wasArrested;
    }
    public void toggleHiding() {hiding = !hiding;}
    public boolean isHiding() {return hiding;}

    public CrewMember getData() {
        return data;
    }
}
