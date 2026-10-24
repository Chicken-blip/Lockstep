package ca.ACOII.lockstep.engine;

import ca.ACOII.lockstep.model.CrewMember;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class HeistRunner {
    ActionQueue queue;
    EventLog log;
    int currentStep;
    Map<CrewMember, String> crewLocations;

    public HeistRunner(List<CrewMember> crew, ActionQueue queue, String roomOrigin) {
        currentStep = 1;
        crewLocations = new HashMap<>();
        for (CrewMember c : crew) {
            crewLocations.put(c, roomOrigin);
        }
        this.queue = queue;
        log = new EventLog();
    }

    public void run() {
        while (!queue.isDone()) {
            Action a = queue.tick();
            if (a != null) log.addEvent(new Event(currentStep, a.getDescription()));
            if (a instanceof MoveAction move) {
                //TODO: Refactor to individual location change
                crewLocations.replaceAll((c, v) -> move.getDestinationRoom());
            }
            currentStep++;
        }
        log.print();
    }

}
