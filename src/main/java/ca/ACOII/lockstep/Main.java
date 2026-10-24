package ca.ACOII.lockstep;

import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Definitions x = new Definitions();
        for (Skill skill : x.skills.getSkills()) {
            System.out.println(skill.getId() + " | " + skill.getName());
        }
        for (LootSet set : x.sets.getSets()) {
            System.out.println(set.getId() + " | " + set.getName() + " | Value: " + set.getSetValue());
        }
        for (Gear gear : x.gear.getGear()) {
            System.out.println(gear.getId() + " | " + gear.getName());
        }
        for (LootItem item : x.lootItems) {
            System.out.println(item.getId() + " | " + item.getName() + " | " + item.getSet() + " | Value: " + item.getPieceValue());
        }
        System.out.println();
        for (Target t : x.targets) {
            System.out.println(t.getId() + " | " + t.getName());
            System.out.println("# of Rooms: " + t.getRooms().size());
            System.out.println("# of Doors: " + t.getDoors().size());
            System.out.println("# of Patrol Guards: " + t.getPatrols().size() + "\n");
            System.out.println("Target Contents:");
            for (Room r : t.getRooms()) {
                for (String str : r.getRoomLoot().keySet()) {
                    System.out.println(x.findLoot(str).getName());
                }
            }
        }

    }
}
