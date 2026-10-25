package ca.ACOII.lockstep;

import ca.ACOII.lockstep.campaign.*;
import ca.ACOII.lockstep.engine.*;
import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.*;

import java.util.ArrayList;
import java.util.List;


public class Main {



    public static void main(String[] args) {
        Definitions x = new Definitions();
        Campaign campaign = new Campaign(new Calendar(), new Stash(x), new ArrayList<>(), 0);

        multi_member_arrest_testing(x, campaign);
    }

    private static void training_and_saving_test(Campaign campaign, Definitions x) {
        campaign.getStash().setMoney(100);

        campaign.recruitMember(x.crew.getFirst().getId(), x, "Thief");


        System.out.println("Level before training: " + campaign.getRoster().getFirst().getData().getSkills().get("pick"));

        campaign.trainSkill(campaign.getRoster().getFirst().getData(), "pick", 10);

        System.out.println("Level before save: " + campaign.getRoster().getFirst().getData().getSkills().get("pick"));

        SaveManager save = new SaveManager();
        save.saveGame(campaign, "save.json");
        campaign = save.loadGame("save.json", x);


        System.out.println("Level after save: " + campaign.getRoster().getFirst().getData().getSkills().get("pick"));
    }

    private static void multi_member_arrest_testing(Definitions x, Campaign campaign) {
        multi_member_syncing_test(x, campaign);
        for (int i = 0; i < 4; i ++) {
            campaign.nextDay();
            System.out.println("\nDay " + campaign.getDate() + ": ");
            for (RosterEntry e : campaign.getCrew()) {
                System.out.println(e.getData().getName() + " in jail? " + (e.getReleaseDate() > campaign.getDate()));
            }
        }
    }

    private static void selling_test(Definitions x) {
        CrewMemberState c = new CrewMemberState(x.crew.getFirst(), new ArrayList<>());
        c.addToInventory(x.findLoot("candelabra_a"));
        c.addToInventory(x.findLoot("candelabra_b"));

        Stash stash = new Stash(x);
        stash.addItems(c.getInventory());
        stash.sellSet(x.sets.getSets().getFirst());
        System.out.println("Money: " + stash.getMoney());
        System.out.println("Contents:");
        stash.printStash();
    }

    private static void corner_gallery_solo_error_run(Definitions x, Campaign campaign) {
        campaign.getStash().setMoney(100);

        campaign.recruitMember("thief", x, "Thief");

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

        HeistRunner runner = new HeistRunner(queues, target, campaign);
        runner.run();
        campaign.settleHeist(runner,3);
    }

    private static void corner_gallery_solo_perfect_run(Definitions x, Campaign campaign) {
        campaign.getStash().setMoney(100);

        campaign.recruitMember("thief", x, "Thief");

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

        HeistRunner runner = new HeistRunner(queues, target, campaign);
        runner.run();
        campaign.settleHeist(runner,3);
    }

    private static void multi_member_syncing_test(Definitions x, Campaign campaign) {
        campaign.getStash().setMoney(100);

        campaign.recruitMember("thief", x, "Thief A");
        campaign.recruitMember("thief", x, "Thief B");

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

        HeistRunner runner = new HeistRunner(queues, target, campaign);
        runner.run();
        campaign.settleHeist(runner,3);
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
