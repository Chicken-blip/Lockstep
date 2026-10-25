package ca.ACOII.lockstep.campaign;

import ca.ACOII.lockstep.engine.CrewMemberState;
import ca.ACOII.lockstep.engine.HeistRunner;
import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.CrewMember;
import ca.ACOII.lockstep.model.Gear;

import java.util.ArrayList;
import java.util.List;
import java.util.SequencedCollection;

public class Campaign {
    Calendar calendar;
    Stash stash;
    List<RosterEntry> crew;

    public Campaign(Calendar calendar, Stash stash, List<CrewMember> crew, int val) {
        this.calendar = calendar;
        this.stash = stash;
        this.crew = new ArrayList<>();
        for (CrewMember c : crew) {
            this.crew.add(new RosterEntry(c));
        }
    }

    public Campaign(Calendar calendar, Stash stash, List<RosterEntry> crew) {
        this.calendar = calendar;
        this.stash = stash;
        this.crew = crew;
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

    public void buyGear(CrewMember member, Gear gear) {
        RosterEntry e = getFromRoster(member);
        if (stash.getMoney() < gear.getPrice()) throw new RuntimeException("Not enough money for " + gear.getId());
        stash.deductMoney(gear.getPrice());
        e.addGear(gear);
    }

    public void trainSkill(CrewMember member, String skillId, int cost) {
        RosterEntry e  = getFromRoster(member);
        if (stash.getMoney() < cost) throw new RuntimeException("Not enough money for " + skillId + " training");
        stash.deductMoney(cost);
        e.getData().getSkills().put(skillId,e.getData().getSkills().get(skillId) + 1);
    }

    public void recruitMember(String id, Definitions x, String newName) {
        CrewMember member = x.findCrew(id);
        if (member == null) throw new RuntimeException("Unknown CrewMember id: " + id);
        if (calendar.currentDay < member.getDayAvailability()) throw new RuntimeException("CrewMember " + id + " not available yet");
        if (stash.getMoney() < member.getRecruitFee()) throw new RuntimeException("Not enough money for " + id + " recruitment");
        stash.deductMoney(member.getRecruitFee());
        CrewMember actual = new CrewMember(member);
        actual.setName(newName);
        getRoster().add(new RosterEntry(actual));
    }

    public List<RosterEntry> getRoster() {
        return crew;
    }
}

