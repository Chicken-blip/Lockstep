package ca.ACOII.lockstep.engine;

import ca.ACOII.lockstep.model.Keycard;
import ca.ACOII.lockstep.model.LootItem;

public class GrabAction implements Action {
    String itemId;
    String itemName;
    int grabCost;
    public GrabAction(LootItem item) {
        this.itemId = item.getId();
        this.itemName = item.getName();
        grabCost = 1;
    }
    public GrabAction(Keycard card) {
        this.itemId = card.getId();
        this.itemName = card.getName();
        grabCost = 1;
    }
    @Override
    public int getDuration() {
        return grabCost;
    }
    public String getItemId() {
        return itemId;
    }
    public String getItemName() {return itemName;}
    @Override
    public String getDescription() {
        return "Grabbed " + itemName;
    }
}
