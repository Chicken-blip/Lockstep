package ca.ACOII.lockstep.load;

import ca.ACOII.lockstep.model.Keycard;
import ca.ACOII.lockstep.model.LootItem;
import ca.ACOII.lockstep.model.Skill;
import ca.ACOII.lockstep.model.SkillList;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeycardLoader {
    private final ObjectMapper mapper = new ObjectMapper();
    public Keycard load(String filename) {
        try {
            InputStream inStrm = getClass().getResourceAsStream("/keycards/" + filename);
            if (inStrm == null) throw new RuntimeException("Keycard file not found: " + filename);
            return mapper.readValue(inStrm, Keycard.class);
        } catch (IOException e) {
            throw new RuntimeException("Keycard file not found: " + filename + " | ", e);
        }
    }

    public List<Keycard> loadAll() {
        try (InputStream inStrm = getClass().getResourceAsStream("/keycards/index.json");) {
            List<String> filenames = mapper.readValue(inStrm, new TypeReference<>() {});
            List<Keycard> cards = new ArrayList<>();
            for (String name : filenames) {
                cards.add(load(name));
            }
            validate(cards);
            return cards;
        } catch (IOException e) {
            throw new RuntimeException("No index.json file found", e);
        }
    }

    private void validate(List<Keycard> list) {
        if (list == null) throw new RuntimeException("keycards/index.json did not load properly");
        Set<String> seenIds = new HashSet<>();
        for (Keycard x : list) {
            if (seenIds.contains(x.getId())) {
                throw new RuntimeException("Duplicate Keycard loaded: " + x.getId());
            } else {
                seenIds.add(x.getId());
            }
        }
    }
}
