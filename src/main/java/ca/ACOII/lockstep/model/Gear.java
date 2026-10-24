package ca.ACOII.lockstep.model;

import java.util.ArrayList;
import java.util.List;

public class Gear {
    private String id, name;
    private int price;
    private List<GearEffect> effects;

    public Gear() {
        id = null;
        name = null;
        price = 0;
        effects = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<GearEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<GearEffect> effects) {
        this.effects = effects;
    }
}
