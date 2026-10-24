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
}
