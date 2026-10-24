package ca.ACOII.lockstep.model;

public class GearEffect {
    private String skill;
    private int stepDelta; //TODO: Purpose unknown - might be changed

    public GearEffect() {
        skill = null;
        stepDelta = 0;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getStepDelta() {
        return stepDelta;
    }

    public void setStepDelta(int stepDelta) {
        this.stepDelta = stepDelta;
    }
}
