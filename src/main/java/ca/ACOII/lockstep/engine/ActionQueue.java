package ca.ACOII.lockstep.engine;

import java.util.List;

public class ActionQueue {
    private final List<Action> plan;
    public int remainingSteps;
    private Action currentAction;
    public ActionQueue(List<Action> list) {
        plan = list;
        remainingSteps = 0;
        currentAction = null;
    }
    public Action tick() {
        if (currentAction == null) {
            if (plan.isEmpty()) return null;
            currentAction = plan.getFirst();
            plan.removeFirst();
            remainingSteps = currentAction.getDuration();
        }
        remainingSteps--;
        if (remainingSteps == 0) {
            Action a = currentAction;
            currentAction = null;
            return a;
        }
        return currentAction;
    }

    public boolean isDone() {
        return (plan.isEmpty() && currentAction == null);
    }
}
