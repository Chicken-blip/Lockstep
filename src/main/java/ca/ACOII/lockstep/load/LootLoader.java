package ca.ACOII.lockstep.load;

import ca.ACOII.lockstep.model.LootItem;
import ca.ACOII.lockstep.model.LootSet;
import ca.ACOII.lockstep.model.SetList;
import ca.ACOII.lockstep.model.SkillList;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LootLoader {
    private final ObjectMapper mapper = new ObjectMapper();
    private SetList sets;
    public LootLoader(SetList sets) {
        this.sets = sets;
    }
    public LootItem load(String filename) {
        try {
            InputStream inStrm = getClass().getResourceAsStream("/loot/" + filename);
            if (inStrm == null) throw new RuntimeException("LootItem file not found: " + filename);
            LootItem item = mapper.readValue(inStrm, LootItem.class);
            return item;
        } catch (IOException e) {
            throw new RuntimeException("LootItem file not found: " + filename + " | ", e);
        }
    }
    public List<LootItem> loadAll() {
        try (InputStream inStrm = getClass().getResourceAsStream("/loot/index.json");) {
            List<String> filenames = mapper.readValue(inStrm, new TypeReference<>() {});
            List<LootItem> loot = new ArrayList<>();
            for (String name : filenames) {
                loot.add(load(name));
            }
            validate(loot);
            return loot;
        } catch (IOException e) {
            throw new RuntimeException("No index.json file found", e);
        }
    }

    private void validate(List<LootItem> list) {
        if (list == null) throw new RuntimeException("loot/index.json did not load properly");
        Set<String> seenIds = new HashSet<>();
        for (LootItem x : list) {
            if (seenIds.contains(x.getId())) {
                throw new RuntimeException("Duplicate LootItem loaded: " + x.getId());
            } else {
                seenIds.add(x.getId());
            }
        }
        for (LootItem x : list) {
            if (!sets.hasSet(x.getSet()) && x.getSet() != null) {
                throw new RuntimeException("LootItem " + x.getName() + " references undefined set " + x.getSet());
            }
        }
    }
}