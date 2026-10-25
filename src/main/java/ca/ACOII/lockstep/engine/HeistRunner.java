package ca.ACOII.lockstep.engine;

import ca.ACOII.lockstep.campaign.*;
import ca.ACOII.lockstep.campaign.Calendar;
import ca.ACOII.lockstep.model.*;

import java.util.*;

public class HeistRunner {
    EventLog log;
    int currentStep, alarmTimer;
    public Map<CrewMemberState, ActionQueue> crewQueues;
    public List<Guard> guards;
    Set<String> unlockedDoors, openContainers;
    Target t;
    boolean isCompleted = false;
    Campaign campaign;

    public HeistRunner(List<CrewMemberState> crew, List<ActionQueue> queues, Target t, Campaign campaign) {
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
        unlockedDoors = new HashSet<>();
        openContainers = new HashSet<>();

        this.campaign = campaign;
    }
    public void run() {
        boolean running = true;
        while (running) {
            for (CrewMemberState c : crewQueues.keySet()) {
                if (c.status == CrewMemberState.Status.ACTIVE) {
                    Action a = crewQueues.get(c).tick();
                    validateAction(c, a);
                    EventType e = null;

                    if (a instanceof MoveAction move) {
                        c.setCurrentRoom(move.getDestinationRoom());
                        e = EventType.MOVE;
                    } else if (a instanceof PickLockAction pick) {
                        if (crewQueues.get(c).remainingSteps == 0) {
                            unlockedDoors.add(pick.doorId);
                            e = EventType.PICK_LOCK;
                        } else {
                            a = null;
                        }
                    } else if (a instanceof GrabAction grab) {
                        if (crewQueues.get(c).remainingSteps == 0) {
                            c.addToInventory(grab.item);
                            e = EventType.GRAB;
                        } else {
                            a = null;
                        }
                    } else if (a instanceof CrackAction crack) {
                        if (crewQueues.get(c).remainingSteps == 0) {
                            openContainers.add(crack.containerId);
                            e = EventType.CRACK;
                        } else {
                            a = null;
                        }
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

                //Print guard location each step
                //System.out.println("[Step " + currentStep + "] Guard " + g.getId() + " in room " + g.getCurrentRoom());
            }
            securityCheck();

            for (CrewMemberState c : crewQueues.keySet()) {
                if (c.status == CrewMemberState.Status.ESCAPED) {
                    for (LootItem i : c.inventory) {
                        if (i.isMainScore()) {
                            isCompleted = true;
                            break;
                        }
                    }
                }
            }

            running = false;
            if (isCompleted) {
                log.print();
                getStash();
                return;
            }
            for (CrewMemberState c : crewQueues.keySet()) {
                //Check if any members are still active
                if (c.status == CrewMemberState.Status.ACTIVE) {
                    running = true;
                    break;
                }
            }
            currentStep++;
        }
        log.print();
        getStash();
    }

    private void getStash() {
        for (CrewMemberState c : crewQueues.keySet()) {
            if (c.status == CrewMemberState.Status.ESCAPED) {
                campaign.getStash().addItems(c.inventory);
            }
        }
    }

    private void validateAction (CrewMemberState crew, Action action) {
        if (action instanceof GrabAction grab) {
            Room curr = t.findRoom(crew.currentRoom);
            //Check if the current room has the directed item
            if (curr.getRoomLoot().containsKey(grab.item.getId())) {
                return;
            } else {
                //Check each container within the room
                for (LootContainer container : curr.getRoomContainers()) {
                    //Check if the specified container is unlocked and has the item
                    if (container.getContents().contains(grab.item.getId()) && openContainers.contains(container.getId())) {
                        return;
                    }
                }
            }
            throw new RuntimeException("Attempt at grabbing unavailable item occurred");
        } else if (action instanceof CrackAction crack) {
            Room curr = t.findRoom(crew.currentRoom);
            LootContainer container = t.findContainer(crack.containerId);
            if (container == null) {
                throw new RuntimeException("Attempt at cracking nonexistent container occurred");
            } if (!curr.getRoomContainers().contains(container)) {
                throw new RuntimeException("Attempt at cracking unavailable container occurred");
            }
            if (openContainers.contains(container.getId())) {
                throw new RuntimeException("Attempt at cracking unlocked container occurred");
            }
        } else if (action instanceof PickLockAction pick) {
            Room curr = t.findRoom(crew.currentRoom);
            Door d = t.findDoor(pick.doorId);
            if (d == null) {
                throw new RuntimeException("Attempt at picking nonexistent door occurred");
            } if (!(Objects.equals(curr.getId(), d.getRoomDest()) || Objects.equals(curr.getId(), d.getRoomOrigin()))) {
                throw new RuntimeException("Attempt at unlocking door in different room occurred");
            } if (unlockedDoors.contains(d.getId())) {
                throw new RuntimeException("Attempt at unlocking unlocked door occurred");
            }
        }
    }

    public void securityCheck() {
        if (alarmTimer > 0) alarmTimer--;
        if (alarmTimer == 0) {
            for (CrewMemberState c : crewQueues.keySet()) {
                if (c.status == CrewMemberState.Status.ACTIVE) {
                    c.status = CrewMemberState.Status.CAUGHT;
                    c.wasArrested = true;

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

    public Campaign getCampaign() {
        return campaign;
    }
}
