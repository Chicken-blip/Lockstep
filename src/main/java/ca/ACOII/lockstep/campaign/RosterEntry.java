package ca.ACOII.lockstep.campaign;

import ca.ACOII.lockstep.model.CrewMember;
import ca.ACOII.lockstep.model.Gear;

import java.util.ArrayList;
import java.util.List;

public class RosterEntry {
    CrewMember data;
    int releaseDate;
    List<Gear> equippedGear;
    public RosterEntry(CrewMember member) {
        data = member;
        releaseDate = 0;
        equippedGear = new ArrayList<>();
    }

    public CrewMember getData() {
        return data;
    }

    public void setData(CrewMember data) {
        this.data = data;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setGear(List<Gear> gear) {
        this.equippedGear = gear;
    }

    public void addGear(Gear gear) {
        equippedGear.add(gear);
    }

    public List<Gear> getGear() {
        return equippedGear;
    }
}
