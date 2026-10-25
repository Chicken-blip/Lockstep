package ca.ACOII.lockstep;

import ca.ACOII.lockstep.campaign.*;
import ca.ACOII.lockstep.engine.*;
import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {

    public static void main(String[] args) {
        Definitions x = new Definitions();

        Game game = new Game();
        game.setMoney(100);
        game.recruit("thief", "Thief A");

        Target target = game.getAvailableTargets().getFirst();

        Map<RosterEntry, ActionQueue> queues = new HashMap<>();

        List<Action> actions = new ArrayList<>();
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new PickLockAction("d3", 2 ));
        actions.add(new MoveAction("VAULT", 1 ));
        actions.add(new CrackAction("Vault Safe", 3));
        actions.add(new GrabAction(x.findLoot("safe_contents")));
        actions.add(new GrabAction(x.findLoot("candelabra_b")));
        actions.add(new WaitAction(2));
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new MoveAction("OUTSIDE", 1 ));

        queues.put(game.getRoster().getFirst(), new ActionQueue(actions));

        game.runHeist(target, queues);

        game.buyGear(game.getRoster().getFirst(), x.findGear("fine_lockpicks"));

        game.train(game.getRoster().getFirst(), "pick", 25);
    }

}
