package ca.ACOII.lockstep.model;

import java.util.Map;

public class CrewMember {
    private String id, name;
    private int recruitFee, dayAvailability;
    Map<String, Integer> skills;

    public CrewMember() {
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

    public int getRecruitFee() {
        return recruitFee;
    }

    public void setRecruitFee(int recruitFee) {
        this.recruitFee = recruitFee;
    }

    public int getDayAvailability() {
        return dayAvailability;
    }

    public void setDayAvailability(int dayAvailability) {
        this.dayAvailability = dayAvailability;
    }

    public Map<String, Integer> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, Integer> skills) {
        this.skills = skills;
    }
}
