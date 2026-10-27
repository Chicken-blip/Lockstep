package ca.ACOII.lockstep.campaign;

import java.util.ArrayList;
import java.util.List;

public class SaveState {
    public int currentDay, money;
    public List<String> stashIds;
    public List<CrewMemberData> crew;
    public long seed;
    public String currentTargetId;
    public List<String> completedTargetIds;

    public SaveState() {
    }
}
