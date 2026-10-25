package ca.ACOII.lockstep.model;

import java.util.Map;

public class CrewMember {
    private String id, name;
    private int recruitFee, dayAvailability, releaseDate;
    Map<String, Integer> skills;

    public CrewMember() {
        releaseDate = 0;
    }
    public CrewMember(CrewMember c) {
        this.id = c.id;
        this.name = c.name;
        this.recruitFee = c.recruitFee;
        this.dayAvailability = c.dayAvailability;
        this.skills = c.skills;
        releaseDate = 0;
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

    public int getReleaseDate() {
        return releaseDate;
    }
}
