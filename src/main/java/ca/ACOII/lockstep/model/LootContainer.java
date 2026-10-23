package ca.ACOII.lockstep.model;

import java.util.List;

public abstract class LootContainer {
    private String room;
    private List<String> contents;
    public abstract void unlock(); //TODO: Add argument
}
