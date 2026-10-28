package ca.ACOII.lockstep.ui;

import ca.ACOII.lockstep.engine.Event;
import ca.ACOII.lockstep.engine.EventLog;
import ca.ACOII.lockstep.engine.EventType;
import ca.ACOII.lockstep.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.util.Duration;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class HeistPlayer {
    FloorPlanView plan;
    EventLog log;
    Timeline timeline;
    int step;
    Target t;
    Runnable onFinished;
    int maxStep;
    ListView<String> logDest;

    public HeistPlayer(FloorPlanView plan, EventLog log, Target t, Runnable onComplete, int len, ListView<String> printDestination)  {
        this.plan = plan;
        this.log = log;
        this.onFinished = onComplete;
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), evt -> {
            tick();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        this.t = t;
        maxStep = len;
        logDest = printDestination;

    }

    public void play() {
        timeline.stop();
        step = 1;
        timeline.play();
    }

    private String getRoomDest(Event e) {
        return e.evtType() == EventType.MOVE ? (e.description().substring(e.description().lastIndexOf("to ") + 2)).trim() : null;
    }
    private LootItem getItem(Event e) {
        if (e.evtType() != EventType.GRAB) return null;
        String itemName = e.description().substring(e.description().lastIndexOf("Grabbed ") + 7).trim();
        return plan.def.findLoot(plan.def.lootNameToId(itemName));
    }
    private Room getRoomParent(LootItem i) {
        for (Room r : plan.instance.getTarget().getRooms()) {
            if (r.getRoomLoot().containsKey(i.getId())) return r;
            for (LootContainer container : r.getRoomContainers()) {
                if (container.getContents().contains(i.getId())) return r;
            }
        }
        return null;
    }

    private void tick() {
        if (step != maxStep + 1) {
            int finalStep = step;
            List<Event> stepEvents = log.getEvents().stream().filter(new Predicate<Event>() {
                @Override
                public boolean test(Event event) {
                    return event.stepNum() == finalStep;
                }
            }).toList();
            for (Event e : stepEvents) {
                //Check each event at this step
                if (e != null) {
                    logDest.getItems().add(String.valueOf(e));
                    if (getRoomDest(e) != null) {
                        plan.moveActor(e.actorId(), getRoomDest(e), t);
                    }
                    if (getItem(e) != null && getRoomParent(getItem(e)) != null) {
                        plan.updateRoomContents(getItem(e).getId(), getRoomParent(getItem(e)).getId());
                    }
                }
            }
            for (Patrol p : t.getPatrols()) {
                String roomId = p.roomAt(step - 1);
                if (!Objects.equals(plan.actorRoom(p.getId()), roomId)) {
                    plan.moveActor(p.getId(), roomId, t);
                }
            }
            step++;
            return;
        }

        timeline.stop();
        onFinished.run();


    }
}
