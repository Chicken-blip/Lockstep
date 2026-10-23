package ca.ACOII.lockstep.model;

import java.util.List;

public class Target {
    private String id, name, entryRoom;
    private List<String> exitRooms;
    private int alarmCountdown, availableFrom, availabilityDuration, maxCrew;
    private List<Room> rooms;
    private List<Door> doors;
    //TODO: Figure out how to select the main goal for the target
    private List<Patrol> patrols;
}
