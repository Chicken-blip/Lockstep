package ca.ACOII.lockstep.campaign;

import ca.ACOII.lockstep.engine.CrewMemberState;
import ca.ACOII.lockstep.model.Target;

import java.util.ArrayList;
import java.util.List;

public class Calendar {
    int currentDay;

    public Calendar() {
        currentDay = 1;
    }

    public void nextDay() {
        currentDay++;
    }

    public int getCurrentDay() {
        return currentDay;
    }
    public List<Target> getAvailableTargets(List<Target> allTargets) {
        List<Target> available = new ArrayList<>();
        for (Target t : allTargets) {
            if (t.getAvailableFrom() == 0 || t.getAvailableFrom() == currentDay) {
                //Check duration if it's end date has already passed, or if it's infinite
                if (t.getAvailabilityDuration() == 0 || t.getAvailableFrom() + t.getAvailabilityDuration() >= currentDay) {
                    available.add(t);
                }
            }
        }
        return available;
    }

}
