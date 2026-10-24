package ca.ACOII.lockstep.engine;

import ca.ACOII.lockstep.model.Patrol;

public class Guard {
    Patrol patrolData;
    int currIndex, remainingSteps;
    String currentRoom;
    public Guard(Patrol patrol) {
        patrolData = patrol;
        currIndex = -1;
        currentRoom = null;
        remainingSteps = 1;
    }
    public void tick() {
        if (remainingSteps == 1) {
            currIndex = (currIndex + 1) % patrolData.getPatrolRoute().size();
            currentRoom = patrolData.getPatrolRoute().get(currIndex).getRoom();
            remainingSteps = patrolData.getPatrolRoute().get(currIndex).getDuration();
        } else {
            remainingSteps--;
        }
    }

    public String getId() {
        return patrolData.getId();
    }

    public String getCurrentRoom() {
        return currentRoom;
    }
}
