package ca.ACOII.lockstep.model;

import java.util.ArrayList;
import java.util.List;

public class Target {
    private String id, name, entryRoom;
    private List<String> exitRooms;
    private int alarmCountdown, availableFrom, availabilityDuration, maxCrew;
    private List<Room> rooms;
    private List<Door> doors;
    //TODO: Figure out how to select the main goal for the target
    private List<Patrol> patrols;

    public Target() {
        id = null;
        name = null;
        entryRoom = null;
        exitRooms = new ArrayList<>();
        alarmCountdown = 0;
        availableFrom = 0;
        availabilityDuration = 0;
        maxCrew = 0;
        rooms = new ArrayList<>();
        doors = new ArrayList<>();
        patrols = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntryRoom() {
        return entryRoom;
    }

    public void setEntryRoom(String entryRoom) {
        this.entryRoom = entryRoom;
    }

    public List<String> getExitRooms() {
        return exitRooms;
    }

    public void setExitRooms(List<String> exitRooms) {
        this.exitRooms = exitRooms;
    }

    public int getAlarmCountdown() {
        return alarmCountdown;
    }

    public void setAlarmCountdown(int alarmCountdown) {
        this.alarmCountdown = alarmCountdown;
    }

    public int getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(int availableFrom) {
        this.availableFrom = availableFrom;
    }

    public int getAvailabilityDuration() {
        return availabilityDuration;
    }

    public void setAvailabilityDuration(int availabilityDuration) {
        this.availabilityDuration = availabilityDuration;
    }

    public int getMaxCrew() {
        return maxCrew;
    }

    public void setMaxCrew(int maxCrew) {
        this.maxCrew = maxCrew;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Door> getDoors() {
        return doors;
    }

    public void setDoors(List<Door> doors) {
        this.doors = doors;
    }

    public List<Patrol> getPatrols() {
        return patrols;
    }

    public void setPatrols(List<Patrol> patrols) {
        this.patrols = patrols;
    }
}
