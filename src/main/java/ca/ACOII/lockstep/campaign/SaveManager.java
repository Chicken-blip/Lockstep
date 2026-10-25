package ca.ACOII.lockstep.campaign;

import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.CrewMember;
import ca.ACOII.lockstep.model.LootItem;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            save.crew.add(new CrewMemberStateData(e.getData().getId(), e.getData().getName(), e.releaseDate));
        }

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), save);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Campaign loadGame(String filename, Definitions x) {
        SaveState save = null;
        try {
            save = mapper.readValue(new File("save.json"), SaveState.class);
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
        for (CrewMemberStateData c : save.crew) {
            CrewMember member = new CrewMember(x.findCrew(c.id()));
            member.setName(c.name());
            RosterEntry entry = new RosterEntry(member);
            entry.releaseDate = c.releaseDate();
            list.add(entry);
        }

        return new Campaign(calendar, stash, list);

    }
}
