package ca.ACOII.lockstep.load;

import ca.ACOII.lockstep.model.LootSet;
import ca.ACOII.lockstep.model.SetList;
import ca.ACOII.lockstep.model.Skill;
import ca.ACOII.lockstep.model.SkillList;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class SkillLoader {
    private final ObjectMapper mapper = new ObjectMapper();
    public SkillList load() {
        try (InputStream inStrm = getClass().getResourceAsStream("/skills.json")) {
            if (inStrm == null) throw new RuntimeException("skills.json not found");
            SkillList list = mapper.readValue(inStrm, SkillList.class);
            validate(list);
            return list;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read skills.json | ", e);
        }
    }
    private void validate(SkillList list) {
        if (list == null || list.getSkills() == null) throw new RuntimeException("skills.json did not load properly");
        Set<String> seenIds = new HashSet<>();
        for (Skill x : list.getSkills()) {
            if (seenIds.contains(x.getId())) {
                throw new RuntimeException("Duplicate Skill loaded: " + x.getId());
            } else {
                seenIds.add(x.getId());
            }
        }
    }
}
