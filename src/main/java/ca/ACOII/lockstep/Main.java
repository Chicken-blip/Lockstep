package ca.ACOII.lockstep;

import ca.ACOII.lockstep.engine.*;
import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.*;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {//verifyLoading(x);
    public static void main(String[] args) {

        Definitions x = new Definitions();


        EventLog log = new EventLog();
        List<Action> actions = new ArrayList<>();
        actions.add(new MoveAction("LOBBY", 1));
        actions.add(new WaitAction(2));
        actions.add(new MoveAction("VAULT", 1));
        ActionQueue queue = new ActionQueue(actions);
        HeistRunner run = new HeistRunner(new ArrayList<>(List.of(x.crew.getFirst())), queue, "OUTSIDE");
        run.run();

    }

    private static void verifyLoading(Definitions x) {
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
