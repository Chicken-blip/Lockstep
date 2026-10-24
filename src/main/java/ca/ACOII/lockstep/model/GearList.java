package ca.ACOII.lockstep.model;

import java.util.ArrayList;
import java.util.List;

public class GearList {
    private List<Gear> gear;

    public GearList() {
        gear = new ArrayList<>();
    }

    public List<Gear> getGear() {
        return gear;
    }

    public void setGear(List<Gear> gear) {
        this.gear = gear;
    }
}
