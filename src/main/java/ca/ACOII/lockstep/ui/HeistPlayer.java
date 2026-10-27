package ca.ACOII.lockstep.ui;

import ca.ACOII.lockstep.engine.Event;
import ca.ACOII.lockstep.engine.EventLog;
import ca.ACOII.lockstep.engine.EventType;
import ca.ACOII.lockstep.model.Patrol;
import ca.ACOII.lockstep.model.Target;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Objects;

public class HeistPlayer {
    FloorPlanView plan;
    EventLog log;
    Timeline timeline;
    int timelineIndex;
    Target t;
    Runnable onFinished;

    public HeistPlayer(FloorPlanView plan, EventLog log, Target t, Runnable onComplete)  {
        this.plan = plan;
        this.log = log;
        this.onFinished = onComplete;
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), evt -> {
            tick();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        this.t = t;
    }

    public void play() {
        timeline.stop();
        timelineIndex = 0;
        timeline.play();
    }

    private String getRoomDest(Event e) {
        return e.evtType() == EventType.MOVE ? (e.description().substring(e.description().lastIndexOf("to ") + 2)).trim() : null;
    }

    private void tick() {
        //TODO: Edit to handle steps with no actions
        if (timelineIndex >= log.getEvents().size()) {
            timeline.stop();
            onFinished.run();
            return;
        }
        Event e = log.getEvents().get(timelineIndex);
        if (e != null) {
            System.out.println(e.toString());
            if (getRoomDest(e) != null) {
                plan.moveActor(e.actorId(), getRoomDest(e), t);
            }
        }
        for (Patrol p : t.getPatrols()) {
            assert e != null;
            String roomId = p.roomAt(e.stepNum() - 1);
            if (!Objects.equals(plan.actorRoom(p.getId()), roomId)) {
                plan.moveActor(p.getId(), roomId, t);
            }
        }

        timelineIndex++;

    }
}
