package ca.ACOII.lockstep.campaign;

import ca.ACOII.lockstep.engine.CrewMemberState;
import ca.ACOII.lockstep.engine.HeistRunner;
import ca.ACOII.lockstep.model.CrewMember;

import java.util.ArrayList;
import java.util.List;

public class Campaign {
    Calendar calendar;
    Stash stash;
    List<RosterEntry> crew;

    public Campaign(Calendar calendar, Stash stash, List<CrewMember> crew) {
        this.calendar = calendar;
        this.stash = stash;
        this.crew = new ArrayList<>();
        for (CrewMember c : crew) {
            this.crew.add(new RosterEntry(c));
        }
    }

    public void settleHeist(HeistRunner runner, int arrestDuration) {
        for (CrewMemberState member : runner.crewQueues.keySet()) {
            if (member.arrested()) {
                getFromRoster(member.getData()).releaseDate = calendar.getCurrentDay() + arrestDuration + 1;
            }
        }
    }

    public RosterEntry getFromRoster(CrewMemberState c) {
        for (RosterEntry e : crew) {
            if (e.data == c.getData()) {
                return e;
            }
        }
        return null;
    }
    public RosterEntry getFromRoster(CrewMember c) {
        for (RosterEntry e : crew) {
            if (e.data == c) {
                return e;
            }
        }
        return null;
    }

    public Stash getStash() {
        return stash;
    }

    public void setCrew(List<CrewMember> crew) {
        for (CrewMember c : crew) {
            this.crew.add(new RosterEntry(c));
        }
    }

    public List<RosterEntry> getCrew() {
        return crew;
    }

    public boolean isCrewAvailable(CrewMember member) {
        RosterEntry entry = getFromRoster(member);
        return entry.releaseDate <= calendar.currentDay;
    }

    public void nextDay() {
        calendar.nextDay();
    }
    public int getDate() {
        return calendar.currentDay;
    }
}

