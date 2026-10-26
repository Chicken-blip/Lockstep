package ca.ACOII.lockstep.model;

import java.util.ArrayList;
import java.util.List;

public class Patrol {
    private String id;
    private List<PatrolEntry> patrolRoute;

    public Patrol() {
        id = null;
        patrolRoute = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PatrolEntry> getPatrolRoute() {
        return patrolRoute;
    }

    public void setPatrolRoute(List<PatrolEntry> patrolRoute) {
        this.patrolRoute = patrolRoute;
    }

    public String roomAt(int index) {
        if (index < 0) return null;
        int loopLen = 0;
        for (PatrolEntry e : patrolRoute) {
            loopLen += e.getDuration();
        }
        index = index % loopLen;
        int sum = 0;
        for (PatrolEntry e : patrolRoute) {
            if (index < sum + e.getDuration()) return e.getRoom();
            sum += e.getDuration();
        }
        return null;
    }
}
