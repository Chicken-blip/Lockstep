package ca.ACOII.lockstep.model;

public class Keycard {
    private String id, name;
    public Keycard() {
        id = null;
        name = null;
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
}
