package ca.ACOII.lockstep.engine;

import ca.ACOII.lockstep.campaign.RosterEntry;
import ca.ACOII.lockstep.model.*;

import java.util.Objects;

public class ActDurationCalculator {
    public static int pickLockDuration(Door door, RosterEntry member) {
        int dur = door.getLock().getPickDifficulty();
        dur -= member.getData().getSkills().getOrDefault("pick", 0);
        for (Gear g : member.getGear()) {
            for (GearEffect eff : g.getEffects()) {
                if (Objects.equals(eff.getSkill(), "pick")) dur += eff.getStepDelta();
            }
        }
        return Math.max(dur, 1);

    }
    public static int crackDuration(SafeContainer container, RosterEntry member) {
        int dur = container.getCrackDifficulty();
        dur -= member.getData().getSkills().getOrDefault("crack", 0);
        for (Gear g : member.getGear()) {
            for (GearEffect eff : g.getEffects()) {
                if (Objects.equals(eff.getSkill(), "crack")) dur += eff.getStepDelta();
            }
        }
        return Math.max(dur, 1);
    }

    public static int pickCaseDuration(DisplayCaseContainer container, RosterEntry member) {
        int dur = container.getPickDifficulty();
        dur -= member.getData().getSkills().getOrDefault("pick", 0);
        for (Gear g : member.getGear()) {
            for (GearEffect eff : g.getEffects()) {
                if (Objects.equals(eff.getSkill(), "pick")) dur += eff.getStepDelta();
            }
        }
        return Math.max(dur, 1);
    }
}
