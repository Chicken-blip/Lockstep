package ca.ACOII.lockstep.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    private String id;
    private Map<String, Double> roomLoot;
    private List<LootContainer> roomContainers;

    public Room() {
        id = null;
        roomLoot = new HashMap<>();
        roomContainers = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Double> getRoomLoot() {
        return roomLoot;
    }

    public void setRoomLoot(Map<String, Double> roomLoot) {
        this.roomLoot = roomLoot;
    }

    public List<LootContainer> getRoomContainers() {
        return roomContainers;
    }

    public void setRoomContainers(List<LootContainer> roomContainers) {
        this.roomContainers = roomContainers;
    }
}
