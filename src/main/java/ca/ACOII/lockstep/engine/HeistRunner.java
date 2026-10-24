package ca.ACOII.lockstep.engine;

import ca.ACOII.lockstep.model.CrewMember;
import ca.ACOII.lockstep.model.Patrol;
import ca.ACOII.lockstep.model.Target;

import java.util.*;

public class HeistRunner {
    EventLog log;
    int currentStep, alarmTimer;
    Map<CrewMemberState, ActionQueue> crewQueues;
    public List<Guard> guards;
    Target t;

    public HeistRunner(List<CrewMemberState> crew, List<ActionQueue> queues, Target t) {
        currentStep = 1;
        crewQueues = new HashMap<>();
        for (int i = crew.size() - 1; i > -1; i--) {
            crew.get(i).setCurrentRoom(t.getEntryRoom());
            crewQueues.put(crew.get(i), queues.get(i));
        }
        log = new EventLog();
        guards = new ArrayList<>();

        for (Patrol p : t.getPatrols()) {
            guards.add(new Guard(p));
        }
        this.t = t;
        alarmTimer = -1;
    }

    public void run() {
        boolean running = true;
        while (running) {
            for (CrewMemberState c : crewQueues.keySet()) {
                if (c.status == CrewMemberState.Status.ACTIVE) {
                    Action a = crewQueues.get(c).tick();
                    EventType e;

                    if (a instanceof MoveAction move) {
                        c.setCurrentRoom(move.getDestinationRoom());
                        e = EventType.MOVE;
                    } else {
                        e = EventType.WAIT;
                    }

                    if (a != null) log.addEvent(new Event(currentStep, a.getDescription(), c.getId(), e));
                    if (a instanceof MoveAction move) {
                        if (t.getExitRooms().contains(move.getDestinationRoom())) {
                            c.status = CrewMemberState.Status.ESCAPED;
                            log.addEvent(new Event(currentStep, null, c.getId(), EventType.ESCAPED));
                        }
                    }
                }
            }
            for (Guard g : guards) {
                g.tick();
            }
            securityCheck();
            running = false;
            for (CrewMemberState c : crewQueues.keySet()) {
                //Check if any members are still active
                if (c.status == CrewMemberState.Status.ACTIVE) {
                    running = true;
                }
            }
            currentStep++;
        }
        log.print();
    }


    public void securityCheck() {
        if (alarmTimer > 0) alarmTimer--;
        if (alarmTimer == 0) {
            for (CrewMemberState c : crewQueues.keySet()) {
                if (c.status == CrewMemberState.Status.ACTIVE) {
                    c.status = CrewMemberState.Status.CAUGHT;
                    log.addEvent(new Event(currentStep, null, c.getId(), EventType.CAUGHT));
                }
            }
        } else {
            for (CrewMemberState c : crewQueues.keySet()) {
                if (c.status == CrewMemberState.Status.ACTIVE) {
                    for (Guard g : guards) {
                        if (Objects.equals(c.getCurrentRoom(), g.getCurrentRoom()) && !c.spotted) {
                            c.spotted = true;
                            log.addEvent(new Event(currentStep, "spotted in " + c.getCurrentRoom(), c.getId(), EventType.SPOTTED));
                            if (alarmTimer == -1) {
                                alarmTimer = t.getAlarmCountdown();
                                log.addEvent(new Event(currentStep, null, c.getId(), EventType.ALARM_RAISED));
                            }
                        }
                    }
                }
            }
        }
    }
}
