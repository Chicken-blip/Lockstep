package ca.ACOII.lockstep.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SkillList {
    private List<Skill> skills;

    public SkillList() {
        this.skills = new ArrayList<>();
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public boolean hasSkill(String skill) {
        for (Skill n : skills) {
            if (Objects.equals(n.getId(), skill)) return true;
        }
        return false;
    }
}
