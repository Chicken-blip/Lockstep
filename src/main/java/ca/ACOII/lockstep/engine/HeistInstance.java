package ca.ACOII.lockstep.engine;

import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.LootContainer;
import ca.ACOII.lockstep.model.Room;
import ca.ACOII.lockstep.model.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeistInstance {
    Target target;
    Map<String, List<String>> roomLootIds, containerLootIds;
    String mainScoreId;
    Definitions definitions;
    public HeistInstance(Target target, Definitions definitions) {
        this.target = target;
        this.definitions = definitions;
        roomLootIds = new HashMap<>();
        containerLootIds = new HashMap<>();
        for (Room r : target.getRooms()) {
            roomLootIds.put(r.getId(), new ArrayList<>());
            for (LootContainer container : r.getRoomContainers()) {
                containerLootIds.put(container.getId(), new ArrayList<>(container.getContents()));
            }
        }
        mainScoreId = target.findMainScore(definitions);
    }

    public Target getTarget() {
        return target;
    }

    public List<String> getRoomLoot(String roomId) {
        return roomLootIds.get(roomId);
    }
    public List<String> getContainerLoot(String containerId) {
        return containerLootIds.get(containerId);
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Map<String, List<String>> getRoomLootIds() {
        return roomLootIds;
    }

    public Map<String, List<String>> getContainerLootIds() {
        return containerLootIds;
    }

    public String getMainScoreId() {
        return mainScoreId;
    }

    public void setRoomLootIds(Map<String, List<String>> roomLootIds) {
        this.roomLootIds = roomLootIds;
    }

    public void setContainerLootIds(Map<String, List<String>> containerLootIds) {
        this.containerLootIds = containerLootIds;
    }

    public void setMainScoreId(String mainScoreId) {
        this.mainScoreId = mainScoreId;
    }
}
