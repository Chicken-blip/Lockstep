package ca.ACOII.lockstep.model;

public class LootSet {
    private String id, name;
    private int setValue;

    public LootSet() {
        id = null;
        name = null;
        setValue = 0;
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

    public int getSetValue() {
        return setValue;
    }

    public void setSetValue(int setValue) {
        this.setValue = setValue;
    }
}
