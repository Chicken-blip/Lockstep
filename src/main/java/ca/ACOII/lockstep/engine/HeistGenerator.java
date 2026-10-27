package ca.ACOII.lockstep.engine;

import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.LootContainer;
import ca.ACOII.lockstep.model.Room;
import ca.ACOII.lockstep.model.Target;

import java.util.Random;

public class HeistGenerator {
    public static HeistInstance generate(Target target, Definitions def, long seed) {
        HeistInstance instance = new HeistInstance(target, def);
        Random rng = new Random(seed);
        for (Room r : target.getRooms()) {
            for (String lootId : r.getRoomLoot().keySet()) {
                Double chance = r.getRoomLoot().get(lootId);
                double actual = rng.nextDouble();
                //System.out.println("item=" + lootId + " chance=" + chance + " rng.nextDouble=" + actual);
                if (actual < chance) {
                    instance.getRoomLoot(r.getId()).add(lootId);
                }
            }
        }
        return instance;
    }
}
