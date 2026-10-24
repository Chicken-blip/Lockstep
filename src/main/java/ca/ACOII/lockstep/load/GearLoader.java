package ca.ACOII.lockstep.load;

import ca.ACOII.lockstep.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class GearLoader {
    private final ObjectMapper mapper = new ObjectMapper();
    private SkillList skills;
    public GearLoader(SkillList skills) {
        this.skills = skills;
    }
    public GearList load() {
        try (InputStream inStrm = getClass().getResourceAsStream("/gear.json")) {
            if (inStrm == null) throw new RuntimeException("gear.json not found");
            GearList list = mapper.readValue(inStrm, GearList.class);
            validate(list);
            return list;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read gear.json | ", e);
        }
    }
    private void validate(GearList list) {
        if (list == null || list.getGear() == null) throw new RuntimeException("gear.json did not load properly");
        Set<String> seenIds = new HashSet<>();
        for (Gear x : list.getGear()) {
            if (seenIds.contains(x.getId())) {
                throw new RuntimeException("Duplicate Gear loaded: " + x.getId());
            } else {
                seenIds.add(x.getId());
            }
        }
        for (Gear x : list.getGear()) {
            for (GearEffect y : x.getEffects()) {
                if (!skills.hasSkill(y.getSkill())) {
                    throw new RuntimeException("Gear " + x.getName() + " references undefined skill " + y.getSkill());
                }
            }
        }
    }
}
