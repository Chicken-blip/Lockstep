package ca.ACOII.lockstep.load;

import ca.ACOII.lockstep.model.*;

import java.util.List;
import java.util.Objects;

public class Definitions {
    public final SetList sets;
    public final SkillList skills;
    public final GearList gear;
    public final List<LootItem> lootItems;
    public final List<CrewMember> crew;
    public final List<Target> targets;

    public Definitions() {
        sets = new SetLoader().load();
        skills = new SkillLoader().load();
        gear = new GearLoader(skills).load();
        lootItems = new LootLoader(sets).loadAll();
        crew = new CrewLoader(skills).loadAll();
        targets = new TargetLoader().loadAll();
    }
    public LootItem findLoot(String key) {
        for (LootItem i : lootItems) {
            if (Objects.equals(i.getId(), key)) {
                return i;
            }
        }
        return null;
    }
    public String lootNameToId(String name) {
        for (LootItem i : lootItems) {
            if (Objects.equals(i.getName(), name)) {
                return i.getId();
            }
        }
        return null;
    }
    public CrewMember findCrew(String key) {
        for (CrewMember c : crew) {
            if (Objects.equals(c.getId(), key)) {
                return c;
            }
        }
        return null;
    }

    public Gear findGear(String key) {
        for (Gear g : gear.getGear()) {
            if (Objects.equals(g.getId(), key)) {
                return g;
            }
        }
        return null;
    }

    public Skill findSkill(String key) {
        for (Skill s : skills.getSkills()) {
            if (Objects.equals(s.getId(), key)) {
                return s;
            }
        }
        return null;
    }

    public Target findTarget(String key) {
        for (Target t : targets) {
            if (Objects.equals(t.getId(), key)) return t;
        }
        return null;
    }
}
