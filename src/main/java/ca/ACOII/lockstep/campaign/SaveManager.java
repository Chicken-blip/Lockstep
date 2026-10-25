package ca.ACOII.lockstep.campaign;

import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.CrewMember;
import ca.ACOII.lockstep.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveManager {
    private final ObjectMapper mapper = new ObjectMapper();

    public void saveGame(Campaign campaign, String filename) {
        SaveState save = new SaveState();
        save.money = campaign.stash.getMoney();
        save.currentDay = campaign.getDate();
        save.stashIds = new ArrayList<>();
        for (LootItem i : campaign.stash.items) {
            save.stashIds.add(i.getId());
        }
        save.crew = new ArrayList<>();
        for (RosterEntry e : campaign.crew) {
            List<String> gearIds = new ArrayList<>();
            for (Gear g : e.getGear()) {
                gearIds.add(g.getId());
            }
            Map<String, Integer> skills = new HashMap<>();
            for (String str : e.getData().getSkills().keySet()) {
                skills.put(str, e.getData().getSkills().get(str));
            }
            save.crew.add(new CrewMemberData(e.getData().getId(), e.getData().getName(), e.releaseDate, gearIds, skills));
        }

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), save);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Campaign loadGame(String filename, Definitions x) {
        SaveState save;
        try {
            save = mapper.readValue(new File(filename), SaveState.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stash stash = new Stash(x);
        for (String id : save.stashIds) {
            stash.addItem(x.findLoot(id));
        }
        stash.money = save.money;

        Calendar calendar = new Calendar();
        calendar.currentDay = save.currentDay;

        List<RosterEntry> list = new ArrayList<>();
        for (CrewMemberData c : save.crew) {
            CrewMember member = new CrewMember(x.findCrew(c.id()));
            member.setName(c.name());
            RosterEntry entry = new RosterEntry(member);
            entry.releaseDate = c.releaseDate();
            for (String id : c.gear()) {
                entry.addGear(x.findGear(id));
            }
            for (String id : c.skillMap().keySet()) {
                entry.getData().addSkill(x.findSkill(id), c.skillMap().get(id));
            }
            list.add(entry);
        }

        return new Campaign(calendar, stash, list);

    }
}
