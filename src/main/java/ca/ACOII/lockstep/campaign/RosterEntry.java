package ca.ACOII.lockstep.campaign;

import ca.ACOII.lockstep.model.CrewMember;

public class RosterEntry {
    CrewMember data;
    int releaseDate;
    public RosterEntry(CrewMember member) {
        data = member;
        releaseDate = 0;
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
}
