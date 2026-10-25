package ca.ACOII.lockstep;

import ca.ACOII.lockstep.campaign.*;
import ca.ACOII.lockstep.engine.ActionQueue;
import ca.ACOII.lockstep.engine.EventLog;
import ca.ACOII.lockstep.engine.HeistRunner;
import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.Gear;
import ca.ACOII.lockstep.model.Target;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {
    Definitions def;
    Campaign campaign;
    String saveFile;
    public Game() {
        def = new Definitions();
        saveFile = "save.json";

        try {
            SaveState save = new ObjectMapper().readValue(new File(saveFile), SaveState.class);
            campaign = new SaveManager().loadGame(saveFile, def);
        } catch (IOException e) {
            campaign = new Campaign(new Calendar(), new Stash(def), new ArrayList<>(), 0);
        }

    }

    public List<Target> getAvailableTargets() {
        return campaign.calendar.getAvailableTargets(def.targets);
    }

    public List<RosterEntry> getRoster() {
        return campaign.getRoster();
    }

    public void runHeist(Target t, Map<RosterEntry, ActionQueue> queues) {
        HeistRunner heist = new HeistRunner(queues, t, campaign);
        heist.run();
        //TODO: Change arrestDuration to target-dependent value
        campaign.settleHeist(heist, 3);
        campaign.nextDay();
        save();

        EventLog log = heist.getLog();
        //TODO: Change print() to return string collection instead of directly printing
        log.print();
    }

    public void train(RosterEntry e, String skillId, int cost) {
        campaign.trainSkill(e.getData(), skillId, cost);
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
}
