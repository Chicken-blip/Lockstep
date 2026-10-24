package ca.ACOII.lockstep.engine;

import ca.ACOII.lockstep.model.CrewMember;
import ca.ACOII.lockstep.model.Patrol;
import ca.ACOII.lockstep.model.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class HeistRunner {
    EventLog log;
    int currentStep;
    Map<CrewMemberState, ActionQueue> crewQueues;
    public List<Guard> guards;

    public HeistRunner(List<CrewMemberState> crew, List<ActionQueue> queues, Target t) {
        currentStep = 1;
        crewQueues = new HashMap<>();
        for (int i = crew.size() - 1; i > -1; i--) {
            crewQueues.put(crew.get(i), queues.get(i));
        }
        log = new EventLog();
        guards = new ArrayList<>();

        for (Patrol p : t.getPatrols()) {
            guards.add(new Guard(p));
        }
    }

    public void run() {
        //TODO: Refactor to iterate through each crew queue per turn
        boolean running = true;
        while (running) {
            for (CrewMemberState c : crewQueues.keySet()) {
                Action a = crewQueues.get(c).tick();
                if (a == null) running = false;
                else log.addEvent(new Event(currentStep, a.getDescription(), c.getId()));
            }
            for (Guard g : guards) {
                g.tick();
            }
            currentStep++;
        }
        log.print();
    }

}
