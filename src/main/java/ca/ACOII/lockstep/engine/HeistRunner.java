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
    HeistInstance instance;
    boolean isCompleted = false, running = true;
    Campaign campaign;

    public HeistRunner(Map<RosterEntry, ActionQueue> queues, HeistInstance instance, Campaign campaign) {
        currentStep = 1;
        this.t = instance.getTarget();
        this.instance = instance;
        crewQueues = new HashMap<>();
        for (RosterEntry e : queues.keySet()) {
            CrewMember member = e.getData();
            CrewMemberState state = new CrewMemberState(member, e.getGear());
            state.setCurrentRoom(t.getEntryRoom());
            crewQueues.put(state, queues.get(e));
        }
        log = new EventLog();
        guards = new ArrayList<>();

        for (Patrol p : t.getPatrols()) {
            guards.add(new Guard(p));
        }
        alarmTimer = -1;
        unlockedDoors = new HashSet<>();
        openContainers = new HashSet<>();

        this.campaign = campaign;
    }
    public void run() {
        running = true;
        while (running) {
            for (CrewMemberState c : crewQueues.keySet()) {
                if (c.status == CrewMemberState.Status.ACTIVE) {
                    Action a = crewQueues.get(c).tick();
                    validateAction(c, a);
                    if (!running) {
                        arrestRemainingActors();
                        campaign.maxStep = currentStep;
                        return;
                    }
                    EventType e;

                    if (a instanceof MoveAction move) {
                        c.setCurrentRoom(move.getDestinationRoom());
                        e = EventType.MOVE;
                    } else if (a instanceof PickLockAction pick) {
                        if (crewQueues.get(c).remainingSteps == 0) {
                            unlockedDoors.add(pick.doorId);
                            e = EventType.PICK_LOCK;
                        } else {
                            e = EventType.PICK_LOCK_PROGRESS;
                        }
                    } else if (a instanceof GrabAction grab) {
                        if (crewQueues.get(c).remainingSteps == 0) {
                            c.addToInventory(grab.item);
                            e = EventType.GRAB;
                        } else {
                            e = EventType.GRAB_PROGRESS;
                        }
                    } else if (a instanceof CrackAction crack) {
                        if (crewQueues.get(c).remainingSteps == 0) {
                            openContainers.add(crack.containerId);
                            e = EventType.CRACK;
                        } else {
                            e = EventType.CRACK_PROGRESS;
                        }
                    } else if (a instanceof PickCaseAction pick) {
                        if (crewQueues.get(c).remainingSteps == 0) {
                            openContainers.add(pick.containerId);
                            e = EventType.PICK_CASE;
                        } else {
                            e = EventType.PICK_CASE_PROGRESS;
                        }
                    } else {
                        e = EventType.WAIT;
                    }

                    if (a != null) log.addEvent(new Event(currentStep, a.getDescription(), c.getData().getName(), e));
                    if (a instanceof MoveAction move) {
                        if (t.getExitRooms().contains(move.getDestinationRoom())) {
                            c.status = CrewMemberState.Status.ESCAPED;
                            log.addEvent(new Event(currentStep, null, c.getData().getName(), EventType.ESCAPED));
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
                        if (Objects.equals(instance.getMainScoreId(), i.getId())) {
                            isCompleted = true;
                            break;
                        }
                    }
                }
            }

            running = false;
            if (isCompleted) {
                getStash();
                campaign.completedTargetIds.add(instance.getTarget().getId());
                campaign.maxStep = currentStep;
                return;
            }
            for (CrewMemberState c : crewQueues.keySet()) {
                //Check if any members are still active
                if (c.status == CrewMemberState.Status.ACTIVE && !crewQueues.get(c).isDone()) {
                    running = true;
                    break;
                }
            }
            currentStep++;
        }
        getStash();
        arrestRemainingActors();
        campaign.maxStep = currentStep;
    }

    private void arrestRemainingActors() {
        for (CrewMemberState crew : crewQueues.keySet()) {
            if (crew.status != CrewMemberState.Status.ESCAPED && !crew.wasArrested) {
                crew.status = CrewMemberState.Status.CAUGHT;
                crew.wasArrested = true;

                log.addEvent(new Event(currentStep, null, crew.getData().getName(), EventType.CAUGHT));
            }
        }
    }

    private void getStash() {
        for (CrewMemberState c : crewQueues.keySet()) {
            if (c.status == CrewMemberState.Status.ESCAPED) {
                campaign.getStash().addItems(c.inventory);
            }
        }
    }

    private void validateAction (CrewMemberState crew, Action action) {
        if (action == null) {
            System.out.println("Empty action - something's wrong");
            running = false;
        }
        if (action instanceof GrabAction grab) {
            Room curr = t.findRoom(crew.currentRoom);
            //Check if the current room has the directed item
            if (instance.getRoomLoot(curr.getId()).contains(grab.item.getId())) {
                return;
            } else {
                //Check each container within the room
                for (LootContainer container : curr.getRoomContainers()) {
                    //Check if the specified container is unlocked and has the item
                    List<String> containerLoot = instance.getContainerLoot(container.getId());
                    if (containerLoot.contains(grab.item.getId()) && openContainers.contains(container.getId())) {
                        return;
                    }
                }
            }
            System.out.println("Attempt at grabbing unavailable item occurred");
            running = false;
        } else if (action instanceof CrackAction crack) {
            Room curr = t.findRoom(crew.currentRoom);
            LootContainer container = t.findContainer(crack.containerId);
            if (container == null) {
                System.out.println("Attempt at cracking nonexistent container occurred");
                running = false;
                return;
            }
            if (!curr.getRoomContainers().contains(container)) {
                System.out.println("Attempt at cracking unavailable container occurred");
                running = false;
                return;
            }
            if (openContainers.contains(container.getId())) {
                System.out.println("Attempt at cracking unlocked container occurred");
                running = false;
            }
        } else if (action instanceof PickCaseAction pick) {
            Room curr = t.findRoom(crew.currentRoom);
            LootContainer container = t.findContainer(pick.containerId);
            if (container == null) {
                System.out.println("Attempt at picking nonexistent container occurred");
                running = false;
                return;
            } if (!curr.getRoomContainers().contains(container)) {
                System.out.println("Attempt at picking unavailable container occurred");
                running = false;
                return;
            }
            if (openContainers.contains(container.getId())) {
                System.out.println("Attempt at picking unlocked container occurred");
                running = false;
            }
        } else if (action instanceof PickLockAction pick) {
            Room curr = t.findRoom(crew.currentRoom);
            Door d = t.findDoor(pick.doorId);
            if (d == null) {
                System.out.println("Attempt at picking nonexistent door occurred");
                running = false;
                return;
            } if (!(Objects.equals(curr.getId(), d.getRoomDest()) || Objects.equals(curr.getId(), d.getRoomOrigin()))) {
                System.out.println("Attempt at unlocking door in different room occurred");
                running = false;
                return;
            } if (unlockedDoors.contains(d.getId())) {
                System.out.println("Attempt at unlocking unlocked door occurred");
                running = false;
            }
        } else if (action instanceof MoveAction move) {
            if (instance.getTarget().findRoom(move.getDestinationRoom()) == null) {
                System.out.println("Invalid move destination");
                running = false;
                return;
            }
            Room destRoom = instance.getTarget().findRoom(move.getDestinationRoom());
            Room originRoom = instance.getTarget().findRoom(crew.getCurrentRoom());
            boolean foundDoor = false, validOrigin = false, validDestination = false;
            for (Door d : instance.getTarget().getDoors()) {
                validOrigin = Objects.equals(d.getRoomOrigin(), originRoom.getId()) || Objects.equals(d.getRoomDest(), originRoom.getId());
                validDestination = Objects.equals(d.getRoomOrigin(), destRoom.getId()) || Objects.equals(d.getRoomDest(), destRoom.getId());
                if (validOrigin && validDestination) {
                    if (d.getLock() == null || unlockedDoors.contains(d.getId())) {
                        foundDoor = true;
                        break;
                    }
                }
            }
            if (!foundDoor) {
                System.out.println("No valid path from origin to move destination");
                running = false;
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

                    log.addEvent(new Event(currentStep, null, c.getData().getName(), EventType.CAUGHT));
                }
            }
        } else {
            for (CrewMemberState c : crewQueues.keySet()) {
                if (c.status == CrewMemberState.Status.ACTIVE) {
                    for (Guard g : guards) {
                        if (Objects.equals(c.getCurrentRoom(), g.getCurrentRoom()) && !c.spotted) {
                            c.spotted = true;
                            log.addEvent(new Event(currentStep, "spotted in " + c.getCurrentRoom(), c.getData().getName(), EventType.SPOTTED));
                            if (alarmTimer == -1) {
                                alarmTimer = t.getAlarmCountdown();
                                log.addEvent(new Event(currentStep, null, c.getData().getName(), EventType.ALARM_RAISED));
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

    public EventLog getLog() {
        return log;
    }
}
