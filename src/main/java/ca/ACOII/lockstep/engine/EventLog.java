package ca.ACOII.lockstep.engine;

import java.util.ArrayList;
import java.util.List;

public class EventLog {
    List<Event> events;
    public EventLog() {
        events = new ArrayList<>();
    }

    public void addEvent(Event evt) {
        events.add(evt);
    }
    public void print() {
        for (Event e : events) {
            System.out.println(e.toString());
        }
    }

    public List<Event> getEvents() {
        return events;
    }
}
