package ca.ACOII.lockstep.campaign;

import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.LootItem;
import ca.ACOII.lockstep.model.LootSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Stash {
    List<LootItem> items;
    int money;
    List<LootItem> allItems;

    public Stash(Definitions x) {
        items = new ArrayList<>();
        money = 0;
        allItems = x.lootItems;
    }

    public void addItems(List<LootItem> items) {
        this.items.addAll(items);
    }
    public void addItem(LootItem item) {
        this.items.add(item);
    }
    public int getMoney() {
        return money;
    }

    public List<LootItem> setItems(String id) {
        List<LootItem> list = new ArrayList<>();
        for (LootItem i : allItems) {
            if (Objects.equals(i.getSet(), id)) {
                list.add(i);
            }
        }
        return list;
    }

    public LootItem sellItem(String id) {
        for (LootItem i : items) {
            if (Objects.equals(i.getId(), id)) {
                money += i.getPieceValue();
                items.remove(i);
                return i;
            }
        }
        throw new RuntimeException("Item " + id + " not found in stash");
    }

    public void sellSet(LootSet setData) {
        for (LootItem i : setItems(setData.getId())) {
            if (!items.contains(i)) {
                throw new RuntimeException("Cannot sell incomplete set " + setData.getName());
            }
        }
        for (LootItem i : setItems(setData.getId())) {
            items.remove(i);
        }
        money += setData.getSetValue();
    }

    public void printStash() {
        if (items.isEmpty()) System.out.println("N/A");
        for (LootItem i : items) {
            System.out.println(i.getName());
        }
    }

    public void deductMoney(int deduction) {
        money -= deduction;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
