package ca.ACOII.lockstep.engine;

import ca.ACOII.lockstep.model.LootItem;

public class GrabAction implements Action {
    LootItem item;
    public GrabAction(LootItem item) {
        this.item = item;
    }
    @Override
    public int getDuration() {
        return item.getGrabCost();
    }

    @Override
    public String getDescription() {
        return "Grabbed " + item.getName();
    }
}
