package ca.ACOII.lockstep;

import ca.ACOII.lockstep.engine.*;
import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.*;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        Definitions x = new Definitions();

    }

    private static void corner_gallery_solo_error_run(Definitions x) {
        ArrayList<CrewMemberState> crew = new ArrayList<>();
        crew.add(new CrewMemberState(x.crew.getFirst()));
        crew.getLast().setId("Thief");

        List<Action> actions = new ArrayList<>();
        actions.add(new GrabAction(x.findLoot("safe_contents")));
        actions.add(new MoveAction("LOBBY", 1));
        actions.add(new PickLockAction("d3", 2));
        actions.add(new MoveAction("VAULT", 1));
        actions.add(new CrackAction("Vault Safe", 3));
        actions.add(new GrabAction(x.findLoot("candelabra_b")));
        actions.add(new WaitAction(2));
        actions.add(new MoveAction("LOBBY", 1));
        actions.add(new MoveAction("OUTSIDE", 1));
        ActionQueue queue = new ActionQueue(actions);

        List<ActionQueue> queues = new ArrayList<>();
        queues.add(queue);

        Target target = x.targets.getFirst();

        HeistRunner runner = new HeistRunner(crew, queues, target);
        runner.run();
    }

    private static void corner_gallery_solo_perfect_run(Definitions x) {

        ArrayList<CrewMemberState> crew = new ArrayList<>();
        crew.add(new CrewMemberState(x.crew.getFirst()));
        crew.getLast().setId("Thief");

        List<Action> actions = new ArrayList<>();
        actions.add(new MoveAction("LOBBY", 1));
        actions.add(new PickLockAction("d3", 2));
        actions.add(new MoveAction("VAULT", 1));
        actions.add(new CrackAction("Vault Safe", 3));
        actions.add(new GrabAction(x.findLoot("safe_contents")));
        actions.add(new GrabAction(x.findLoot("candelabra_b")));
        actions.add(new WaitAction(2));
        actions.add(new MoveAction("LOBBY", 1));
        actions.add(new MoveAction("OUTSIDE", 1));
        ActionQueue queue = new ActionQueue(actions);

        List<ActionQueue> queues = new ArrayList<>();
        queues.add(queue);

        Target target = x.targets.getFirst();

        HeistRunner runner = new HeistRunner(crew, queues, target);
        runner.run();
    }

    private static void multi_member_syncing_test(Definitions x) {

        ArrayList<CrewMemberState> crew = new ArrayList<>();
        crew.add(new CrewMemberState(x.crew.getFirst()));
        crew.getLast().setId("Thief A");
        crew.add(new CrewMemberState(x.crew.getFirst()));
        crew.getLast().setId("Thief B");

        List<Action> actions = new ArrayList<>();
        actions.add(new WaitAction(1));
        actions.add(new MoveAction("OFFICE", 1));
        actions.add(new WaitAction(1));
        actions.add(new MoveAction("VAULT", 1));
        actions.add(new WaitAction(1));
        ActionQueue queue1 = new ActionQueue(actions);

        actions = new ArrayList<>();
        actions.add(new WaitAction(1));
        actions.add(new MoveAction("LOBBY", 1));
        actions.add(new MoveAction("VAULT", 1));
        actions.add(new WaitAction(1));
        actions.add(new MoveAction("OUTSIDE", 1));
        ActionQueue queue2 = new ActionQueue(actions);

        List<ActionQueue> queues = new ArrayList<>();
        queues.add(queue1);
        queues.add(queue2);

        Target target = x.targets.getLast();

        HeistRunner run = new HeistRunner(crew, queues, target);
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
