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

public class TargetLoader {
    private final ObjectMapper mapper = new ObjectMapper();
    public Target load(String filename) {
        try {
            InputStream inStrm = getClass().getResourceAsStream("/targets/" + filename);
            if (inStrm == null) throw new RuntimeException("Target file not found: " + filename);
            Target target = mapper.readValue(inStrm, Target.class);
            return target;
        } catch (IOException e) {
            throw new RuntimeException("Target file not found: " + filename + " | ", e);
        }
    }
    public List<Target> loadAll() {
        try (InputStream inStrm = getClass().getResourceAsStream("/targets/index.json");) {
            List<String> filenames = mapper.readValue(inStrm, new TypeReference<>() {});
            List<Target> targets = new ArrayList<>();
            for (String name : filenames) {
                targets.add(load(name));
            }
            //TODO: Reconsider validation for Targets
            validate(targets);
            return targets;
        } catch (IOException e) {
            throw new RuntimeException("No index.json file found", e);
        }
    }

    private void validate(List<Target> targets) {
        for (Target t : targets) {
            if (t.getId() == null || t.getName() == null) throw new RuntimeException("Target identifier not loaded");

            Set<String> roomIDs = new HashSet<>();
            for (Room r : t.getRooms()) {roomIDs.add(r.getId());}

            for (Door d : t.getDoors()) {
                if (!roomIDs.contains(d.getRoomOrigin())) throw new RuntimeException("Door " + d.getId() + " for Target " + t.getName() + " has an invalid room origin");
                if (!roomIDs.contains(d.getRoomDest())) throw new RuntimeException("Door " + d.getId() + " for Target " + t.getName() + " has an invalid room destination");
            }

            for (Patrol p : t.getPatrols()) {
                for (PatrolEntry e : p.getPatrolRoute()) {
                    if (!roomIDs.contains(e.getRoom())) throw new RuntimeException("Patrol Route of Patrol " + p.getId() + " for Target " + t.getName() + " has an invalid patrol entry");
                }
            }

        }
    }
}
