package ca.ACOII.lockstep.engine;

import ca.ACOII.lockstep.model.Patrol;
import ca.ACOII.lockstep.model.Staff;

public class StaffState {
    Staff staffData;
    int currIndex, remainingSteps, suspicion;
    String currentRoom;

    public StaffState(Staff reference) {
        staffData = reference;
        currIndex = -1;
        currentRoom = null;
        remainingSteps = 1;
        suspicion = 0;
    }

    public void tick() {
        if (remainingSteps == 1) {
            currIndex = (currIndex + 1) % staffData.getPatrolRoute().size();
            currentRoom = staffData.getPatrolRoute().get(currIndex).getRoom();
            remainingSteps = staffData.getPatrolRoute().get(currIndex).getDuration();
        } else {
            remainingSteps--;
        }
    }

    public String getId() {
        return staffData.getId();
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public int getSuspicion() {
        return suspicion;
    }

    public void incrementSuspicion() {
        this.suspicion++;
    }
    public void decrementSuspicion() {
        this.suspicion = Math.max(this.suspicion - 1, 0);
    }
    public boolean reachedThreshold() {
        return this.suspicion >= this.staffData.getSuspicionThreshold();
    }
}
