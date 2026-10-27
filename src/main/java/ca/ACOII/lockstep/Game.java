package ca.ACOII.lockstep;

import ca.ACOII.lockstep.campaign.*;
import ca.ACOII.lockstep.engine.*;
import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.Gear;
import ca.ACOII.lockstep.model.Target;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

public class Game {
    Definitions def;
    Campaign campaign;
    String saveFile;
    EventLog log;
    HeistInstance instance;
    public Game(boolean toLoad) {
        def = new Definitions();
        saveFile = "save.json";

        if (toLoad) {

            try {
                SaveState save = new ObjectMapper().readValue(new File(saveFile), SaveState.class);
                campaign = new SaveManager().loadGame(saveFile, def);
            } catch (IOException e) {
                campaign = new Campaign(def, new Calendar(), new Stash(def), new ArrayList<>(), 0);
            }
        } else {
            campaign = new Campaign(def, new Calendar(), new Stash(def), new ArrayList<>(), 0);
        }

        log = null;
        if (campaign.currentTargetId != null) {
            instance = new HeistInstance(def.findTarget(campaign.currentTargetId), def);
        }

    }

    public List<Target> getAvailableTargets() {
        List<Target> list = campaign.calendar.getAvailableTargets(def.targets);
        return list.stream().filter(new Predicate<>() {
            @Override
            public boolean test(Target target) {
                return !campaign.completedTargetIds.contains(target.getId());
            }
        }).toList();
    }

    public List<RosterEntry> getRoster() {
        return campaign.getRoster();
    }

    public void prepareHeist(Target target) {
        campaign.seed = new Random().nextLong();
        campaign.currentTargetId = target.getId();
        instance = HeistGenerator.generate(target, def, campaign.seed);
    }
    public void runHeist(Map<RosterEntry, ActionQueue> queues) {
        HeistRunner heist = new HeistRunner(queues, instance, campaign);
        heist.run();
        //TODO: Change arrestDuration to target-dependent value
        campaign.settleHeist(heist, 3);
        campaign.nextDay();
        save();

        log = heist.getLog();
    }

    public void train(RosterEntry e, String skillId) {
        campaign.trainSkill(e.getData(), skillId);
        campaign.nextDay();
        save();
    }

    public void buyGear(RosterEntry e, Gear gear) {
        campaign.buyGear(e.getData(), gear);
        save();
    }

    public void recruit(String id, String name) {
        campaign.recruitMember(id, def, name);
        save();
    }

    public void save() {
        new SaveManager().saveGame(campaign, saveFile);
    }
    public void load() {
        campaign = new SaveManager().loadGame(saveFile, def);
    }

    //TODO: Admin method - to remove/comment out later
    public void setMoney(int i) {
        campaign.getStash().setMoney(i);
    }

    public EventLog getLog() {
        return log;
    }

    private void printLog() {
        log.print();
    }

    public Stash getStash() {return campaign.getStash();}
    public Calendar getCalendar() {return campaign.calendar;}

    public HeistInstance getInstance() {
        return this.instance;
    }
    public Campaign getCampaign() {
        return this.campaign;
    }
}
