package ca.ACOII.lockstep.load;

import ca.ACOII.lockstep.model.SetList;
import ca.ACOII.lockstep.model.LootSet;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class SetLoader {
    private final ObjectMapper mapper = new ObjectMapper();
    public SetList load() {
        try (InputStream inStrm = getClass().getResourceAsStream("/sets.json")) {
            if (inStrm == null) throw new RuntimeException("sets.json not found");
            SetList list = mapper.readValue(inStrm, SetList.class);
            validate(list);
            return list;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read sets.json | ", e);
        }
    }
    private void validate(SetList list) {
        if (list == null || list.getSets() == null) throw new RuntimeException("sets.json did not load properly");
        Set<String> seenIds = new HashSet<>();
        for (LootSet x : list.getSets()) {
            if (seenIds.contains(x.getId())) {
                throw new RuntimeException("Duplicate LootSet loaded: " + x.getId());
            } else {
                seenIds.add(x.getId());
            }
        }
    }
}
