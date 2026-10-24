package ca.ACOII.lockstep.load;

import ca.ACOII.lockstep.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrewLoader {
    private final ObjectMapper mapper = new ObjectMapper();
    private SkillList skills;
    public CrewLoader(SkillList skills) {
        this.skills = skills;
    }
    public CrewMember load(String filename) {
        try {
            InputStream inStrm = getClass().getResourceAsStream("/crew/" + filename);
            if (inStrm == null) throw new RuntimeException("CrewMember file not found: " + filename);
            CrewMember member = mapper.readValue(inStrm, CrewMember.class);
            return member;
        } catch (IOException e) {
            throw new RuntimeException("CrewMember file not found: " + filename + " | ", e);
        }
    }
    public List<CrewMember> loadAll() {
        try (InputStream inStrm = getClass().getResourceAsStream("/crew/index.json");) {
            List<String> filenames = mapper.readValue(inStrm, new TypeReference<>() {});
            List<CrewMember> crew = new ArrayList<>();
            for (String name : filenames) {
                crew.add(load(name));
            }
            validate(crew);
            return crew;
        } catch (IOException e) {
            throw new RuntimeException("No index.json file found", e);
        }
    }

    private void validate(List<CrewMember> list) {
        if (list == null) throw new RuntimeException("crew/index.json did not load properly");
        Set<String> seenIds = new HashSet<>();
        for (CrewMember x : list) {
            if (seenIds.contains(x.getId())) {
                throw new RuntimeException("Duplicate CrewMember loaded: " + x.getId());
            } else {
                seenIds.add(x.getId());
            }
        }
        for (CrewMember x : list) {
            for (String skill : x.getSkills().keySet()) {
                if (!skills.hasSkill(skill)) {
                    throw new RuntimeException("CrewMember " + x.getName() + " references undefined skill " + skill);
                }
            }
        }
    }
}