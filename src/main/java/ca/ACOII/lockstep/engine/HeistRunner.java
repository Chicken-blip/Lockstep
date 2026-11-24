package ca.ACOII.lockstep.engine;

import ca.ACOII.lockstep.campaign.*;
import ca.ACOII.lockstep.model.*;

import java.util.*;

public class HeistRunner {
    EventLog log;
    int currentStep, alarmTimer;
    public Map<CrewMemberState, ActionQueue> crewQueues;
    public List<Guard> guards;
    public List<StaffState> staff;
    Set<String> unlockedDoors, clearedBlockages, openContainers, disabledSensors;
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

        staff = new ArrayList<>();
        for (Staff s : t.getStaff()) {
            staff.add(new StaffState(s));
        }

        alarmTimer = -1;
        unlockedDoors = new HashSet<>();
        clearedBlockages = new HashSet<>();
        openContainers = new HashSet<>();
        disabledSensors = new HashSet<>();

        this.campaign = campaign;
    }
    public void run() {
        running = true;
        while (running) {
            for (CrewMemberState c : crewQueues.keySet()) {
                if (c.status == CrewMemberState.Status.ACTIVE) {
                    ActionQueue queue = crewQueues.get(c);
                    if (queue.isDone()) {
                        continue;
                    }
                    Action a = queue.tick();
                    String validationError = validateAction(c, a);
                    if (validationError != null) {
                        queue.cancelCurrentAction();
                        log.addEvent(new Event(currentStep, validationError, c.getData().getName(), EventType.ACTION_FAILED));
                        continue;
                    }
                    EventType e = null;

                    //NOTE: Actual action execution
                    if (a instanceof HideAction) {
                        if (!c.isHiding()) c.toggleHiding();
                        e = EventType.HIDE;
                    } else {
                        if (c.isHiding()) c.toggleHiding();
                        if (a instanceof MoveAction move) {
                            String currRoom = c.getCurrentRoom();
                            String destRoom = move.getDestinationRoom();

                            Door d = instance.getTarget().findDoor(currRoom, destRoom);
                            if (d != null) {
                                if (d.getSensor() != null && !disabledSensors.contains(d.getSensor().id)) {
                                    if (!c.spotted) {
                                        c.spotted = true;
                                        log.addEvent(new Event(currentStep, "detected passing through " + d.getId() + " to " + destRoom, c.getData().getName(), EventType.GUARD_SPOTTED));
                                        if (alarmTimer == -1) {
                                            alarmTimer = t.getAlarmCountdown();
                                            log.addEvent(new Event(currentStep, null, c.getData().getName(), EventType.ALARM_RAISED));
                                        }
                                    }
                                }
                            }
                            c.setCurrentRoom(destRoom);
                            e = EventType.MOVE;
                        } else if (a instanceof PickLockAction pick) {
                            if (crewQueues.get(c).remainingSteps == 0) {
                                unlockedDoors.add(pick.doorId);
                                e = EventType.PICK_LOCK;
                            } else {
                                e = EventType.PICK_LOCK_PROGRESS;
                            }
                        } else if (a instanceof ForceDoorAction forceDoor) {
                            if (crewQueues.get(c).remainingSteps == 0) {
                                unlockedDoors.add(forceDoor.doorId);
                                e = EventType.FORCE_DOOR;
                            } else {
                                e = EventType.FORCE_DOOR_PROGRESS;
                            }
                        } else if (a instanceof UseKeycardAction useKeycard) {
                            unlockedDoors.add(useKeycard.doorId);
                            e = EventType.USE_KEYCARD;
                        } else if (a instanceof SmashAction smash) {
                            if (crewQueues.get(c).remainingSteps == 0) {
                                clearedBlockages.add(smash.getBlockageId());
                                e = EventType.SMASH_BLOCKAGE;
                            } else {
                                e = EventType.SMASH_BLOCKAGE_PROGRESS;
                            }
                        } else if (a instanceof GrabAction grab) {
                            if (crewQueues.get(c).remainingSteps <= 0) {
                                if (campaign.definitions.findLoot(grab.itemId) == null) {
                                    c.keycards.add(grab.itemId);
                                } else {
                                    c.addToInventory(campaign.definitions.findLoot(grab.itemId));
                                }
                                removeFromTarget(grab.itemId);
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
                        } else if (a instanceof ForceOpenAction forceOpen) {
                            if (crewQueues.get(c).remainingSteps == 0) {
                                openContainers.add(forceOpen.containerId);
                                e = EventType.FORCE_OPEN;
                            } else {
                                e = EventType.FORCE_OPEN_PROGRESS;
                            }
                        } else if (a instanceof DisableSensorAction disable) {
                            if (crewQueues.get(c).remainingSteps == 0) {
                                disabledSensors.add(disable.getSensorId());
                                e = EventType.DISABLE_SENSOR;
                            } else {
                                e = EventType.DISABLE_SENSOR_PROGRESS;
                            }
                        } else if (a instanceof WaitAction) {
                            e = EventType.WAIT;
                        }
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
            }
            for (StaffState s : staff) {
                s.tick();
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

    private String validateAction (CrewMemberState crew, Action action) {
        if (action == null) {
            return "No action was available";
        }
        if (action instanceof HideAction hide) {
            Room curr = t.findRoom(crew.currentRoom);
            //Check if the current room allows hiding
            if (curr == null || !curr.canHide()) {
                return "Cannot hide in this room";
            }
            //Check if the set duration is outside the max allowed for the crew member
            if (hide.getDuration() > crew.getData().getHideDuration()) {
                return "Hide duration exceeds crew member's limit";
            }

        } else if (action instanceof GrabAction grab) {
            Room curr = t.findRoom(crew.currentRoom);
            if (curr == null) {
                return "Cannot grab an item outside a valid room";
            }
            //Check if the current room has the directed item
            if (instance.getRoomLoot(curr.getId()).contains(grab.itemId)) {
                return null;
            }
            //Check if the current room has the corresponding keycard, in case the id is of a keycard's
            if (instance.getRoomKeycards(curr.getId()).contains(grab.itemId)) {
                return null;
            }
            //Check each container within the room
            for (LootContainer container : curr.getRoomContainers()) {
                //Check if the specified container is unlocked and has the item
                List<String> containerLoot = instance.getContainerLoot(container.getId());
                if (containerLoot.contains(grab.itemId) && openContainers.contains(container.getId())) {
                    return null;
                }
            }
            return "Item is not available in this room";
        } else if (action instanceof UseKeycardAction useKeycard) {
            if (crew.keycards.isEmpty()) {
                return "Crew member has no keycard";
            }
            Room curr = t.findRoom(crew.currentRoom);
            Door d = t.findDoor(useKeycard.doorId);
            if (d == null) {
                return "Door does not exist";
            }
            if (!(d.getLock() instanceof KeycardLock keycardLock) || keycardLock.getKeycardId() == null) {
                return "Door does not use a valid keycard lock";
            }
            if (!crew.hasKeycard(keycardLock.getKeycardId())) {
                return "Crew member does not have the required keycard";
            }
            if (curr == null || !(Objects.equals(curr.getId(), d.getRoomDest()) || Objects.equals(curr.getId(), d.getRoomOrigin()))) {
                return "Crew member is not next to that door";
            }
            if (unlockedDoors.contains(d.getId())) {
                return "Door is already unlocked";
            }
        } else if (action instanceof CrackAction crack) {
            Room curr = t.findRoom(crew.currentRoom);
            LootContainer container = t.findContainer(crack.containerId);
            if (container == null) {
                return "Container does not exist";
            }
            if (curr == null || !curr.getRoomContainers().contains(container)) {
                return "Container is not in this room";
            }
            if (openContainers.contains(container.getId())) {
                return "Container is already open";
            }
        } else if (action instanceof PickCaseAction pick) {
            Room curr = t.findRoom(crew.currentRoom);
            LootContainer container = t.findContainer(pick.containerId);
            if (container == null) {
                return "Container does not exist";
            }
            if (!(container instanceof DisplayCaseContainer)) {
                return "Container is not a display case";
            }
            if (curr == null || !curr.getRoomContainers().contains(container)) {
                return "Container is not in this room";
            }
            if (openContainers.contains(container.getId())) {
                return "Container is already open";
            }
        } else if (action instanceof PickLockAction pick) {
            Room curr = t.findRoom(crew.currentRoom);
            Door d = t.findDoor(pick.doorId);
            if (d == null) {
                return "Door does not exist";
            }
            if (!(d.getLock() instanceof PickLock pickLock) || pickLock.getPickDifficulty() == 0) {
                return "Door cannot be picked";
            }
            if (curr == null || !(Objects.equals(curr.getId(), d.getRoomDest()) || Objects.equals(curr.getId(), d.getRoomOrigin()))) {
                return "Crew member is not next to that door";
            }
            if (unlockedDoors.contains(d.getId())) {
                return "Door is already unlocked";
            }
        } else if (action instanceof DisableSensorAction disable) {
            Door found = null;
            for (Door d : instance.getTarget().getDoors()) {
                if (d.getSensor() != null && Objects.equals(d.getSensor().id, disable.getSensorId())) found = d;
            }
            if (found == null) {
                return "Sensor does not exist";
            }
            if (!Objects.equals(crew.getCurrentRoom(), found.getRoomDest()) && !Objects.equals(crew.getCurrentRoom(), found.getRoomOrigin())) {
                return "Crew member is not next to that sensor";
            }
            if (disabledSensors.contains(disable.getSensorId())) {
                return "Sensor is already disabled";
            }

        } else if (action instanceof ForceOpenAction forceOpen) {
            Room curr = t.findRoom(crew.currentRoom);
            LootContainer container = t.findContainer(forceOpen.containerId);
            if (container == null) {
                return "Container does not exist";
            }
            if (!(container instanceof ForceOpenContainer)) {
                return "Container cannot be forced open";
            }
            if (curr == null || !curr.getRoomContainers().contains(container)) {
                return "Container is not in this room";
            }
            if (openContainers.contains(container.getId())) {
                return "Container is already open";
            }
        } else if (action instanceof ForceDoorAction forceDoor) {
            Room curr = t.findRoom(crew.currentRoom);
            Door d = t.findDoor(forceDoor.doorId);
            if (d == null) {
                return "Door does not exist";
            }
            if (!(d.getLock() instanceof PickLock pickLock) || pickLock.getForceDifficulty() == 0) {
                return "Door cannot be forced open";
            }
            if (curr == null || !(Objects.equals(curr.getId(), d.getRoomDest()) || Objects.equals(curr.getId(), d.getRoomOrigin()))) {
                return "Crew member is not next to that door";
            }
            if (unlockedDoors.contains(d.getId())) {
                return "Door is already unlocked";
            }
        } else if (action instanceof SmashAction smash) {
            Room curr = t.findRoom(crew.currentRoom);
            Blockage b = t.findBlockage(smash.blockageId);
            if (b == null) {
                return "Blockage does not exist";
            }
            if (curr == null || !(Objects.equals(curr.getId(), b.getRoomDest()) || Objects.equals(curr.getId(), b.getRoomOrigin()))) {
                return "Crew member is not next to that blockage";
            }
            if (b.getBreakDifficulty() == 0) {
                return "Blockage cannot be smashed";
            }
            if (clearedBlockages.contains(b.getId())) {
                return "Blockage is already cleared";
            }
        } else if (action instanceof MoveAction move) {
            Room originRoom = instance.getTarget().findRoom(crew.getCurrentRoom());
            Room destRoom = instance.getTarget().findRoom(move.getDestinationRoom());
            if (destRoom == null) {
                return "Destination room does not exist";
            }
            if (originRoom == null) {
                return "Current room does not exist";
            }
            boolean foundDoor = false, validOrigin, validDestination;
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
                for (Blockage b : instance.getTarget().getBlockages()) {
                    validOrigin = Objects.equals(b.getRoomOrigin(), originRoom.getId()) || Objects.equals(b.getRoomDest(), originRoom.getId());
                    validDestination = Objects.equals(b.getRoomOrigin(), destRoom.getId()) || Objects.equals(b.getRoomDest(), destRoom.getId());
                    if (validOrigin && validDestination) {
                        if (clearedBlockages.contains(b.getId())) {
                            foundDoor = true;
                            break;
                        }
                    }
                }
            }
            if (!foundDoor) {
                return "No open path to destination room";
            }
        }
        return null;
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
                        if (Objects.equals(c.getCurrentRoom(), g.getCurrentRoom()) && !c.spotted && !c.isHiding()) {
                            c.spotted = true;
                            log.addEvent(new Event(currentStep, "spotted in " + c.getCurrentRoom(), c.getData().getName(), EventType.GUARD_SPOTTED));
                            if (alarmTimer == -1) {
                                alarmTimer = t.getAlarmCountdown();
                                log.addEvent(new Event(currentStep, null, null, EventType.ALARM_RAISED));
                            }
                        }
                    }
                }
            }
            for (StaffState s : staff) {
                List<CrewMemberState> spottedPerson = new ArrayList<>();
                for (CrewMemberState c : crewQueues.keySet()) {
                    if (Objects.equals(s.getCurrentRoom(), c.getCurrentRoom()) && alarmTimer == -1 && !c.isHiding()) {
                        spottedPerson.add(c);
                        break;
                    }
                }
                if (!spottedPerson.isEmpty()) {
                    s.incrementSuspicion();
                    for (CrewMemberState x : spottedPerson) {
                        x.spotted = true;
                    }
                    if (s.reachedThreshold()) {
                        for (CrewMemberState c : spottedPerson) {
                            log.addEvent(new Event(currentStep, "spotted in " + c.getCurrentRoom(), c.getData().getName(), EventType.STAFF_SPOTTED));
                        }
                        if (alarmTimer == -1) {
                            alarmTimer = t.getAlarmCountdown();
                            log.addEvent(new Event(currentStep, null, null, EventType.ALARM_RAISED));
                        }
                    }
                } else {
                    s.decrementSuspicion();
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

    private void removeFromTarget(String id) {
        for (String room : instance.roomLootIds.keySet()) {
            instance.roomLootIds.get(room).remove(id);
            instance.roomKeycardIds.get(room).remove(id);
        }
        for (String container : instance.containerLootIds.keySet()) {
            instance.containerLootIds.get(container).remove(id);
        }
    }
}
