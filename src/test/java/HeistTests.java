import ca.ACOII.lockstep.Game;
import ca.ACOII.lockstep.campaign.RosterEntry;
import ca.ACOII.lockstep.engine.*;
import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.Target;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HeistTests {
    private static Definitions definitions;
    private static Game game;

    public HeistTests() {}

    @BeforeEach
    public void setupFields() {
        definitions = new Definitions();
        game = new Game(false);
    }

    @Test
    public void test1_cleanSoloRun() {
        game.setMoney(100);
        game.recruit("thief", "Thief A");

        Target target = game.getAvailableTargets().getFirst();

        List<Action> actions = new ArrayList<>();
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new PickLockAction("d3", 2 ));
        actions.add(new MoveAction("VAULT", 1 ));
        actions.add(new CrackAction("Vault Safe", 3));
        actions.add(new GrabAction(definitions.findLoot("safe_contents")));
        actions.add(new GrabAction(definitions.findLoot("candelabra_b")));
        actions.add(new WaitAction(2));
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new MoveAction("OUTSIDE", 1 ));

        game.runHeist(target, Map.of(game.getRoster().getFirst(), new ActionQueue(actions)));

        for (Event e : game.getLog().getEvents()) {
            assertTrue(e.evtType() != EventType.CAUGHT && e.evtType() != EventType.SPOTTED);
        }
        assertSame(EventType.ESCAPED, game.getLog().getEvents().getLast().evtType());

        assertTrue(game.getStash().getItems().contains(definitions.findLoot("candelabra_b")));
        assertTrue(game.getStash().getItems().contains(definitions.findLoot("safe_contents")));
        assertEquals(2, game.getCalendar().getCurrentDay());

    }

    @Test
    public void test2_completeSetRun() {
        game.setMoney(100);
        game.recruit("thief", "Thief A");

        Target target = game.getAvailableTargets().getFirst();

        List<Action> actions = new ArrayList<>();
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new WaitAction(2));
        actions.add(new MoveAction("OFFICE", 1));
        actions.add(new GrabAction(definitions.findLoot("candelabra_a")));
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new PickLockAction("d3", 2 ));
        actions.add(new MoveAction("VAULT", 1 ));
        actions.add(new CrackAction("Vault Safe", 3));
        actions.add(new GrabAction(definitions.findLoot("safe_contents")));
        actions.add(new GrabAction(definitions.findLoot("candelabra_b")));
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new MoveAction("OUTSIDE", 1 ));

        game.runHeist(target, Map.of(game.getRoster().getFirst(), new ActionQueue(actions)));

        for (Event e : game.getLog().getEvents()) {
            assertTrue(e.evtType() != EventType.CAUGHT && e.evtType() != EventType.SPOTTED);
        }
        assertSame(EventType.ESCAPED, game.getLog().getEvents().getLast().evtType());

        assertTrue(game.getStash().getItems().contains(definitions.findLoot("candelabra_b")));
        assertTrue(game.getStash().getItems().contains(definitions.findLoot("candelabra_a")));
        assertTrue(game.getStash().getItems().contains(definitions.findLoot("safe_contents")));
        assertEquals(2, game.getCalendar().getCurrentDay());
    }

    @Test
    public void test3_seenEscaped() {
        game.setMoney(100);
        game.recruit("thief", "Thief A");

        Target target = game.getAvailableTargets().getFirst();

        List<Action> actions = new ArrayList<>();
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new PickLockAction("d3", 2 ));
        actions.add(new MoveAction("VAULT", 1 ));
        actions.add(new CrackAction("Vault Safe", 3));
        actions.add(new GrabAction(definitions.findLoot("safe_contents")));
        actions.add(new GrabAction(definitions.findLoot("candelabra_b")));
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new MoveAction("OUTSIDE", 1 ));

        game.runHeist(target, Map.of(game.getRoster().getFirst(), new ActionQueue(actions)));

        for (Event e : game.getLog().getEvents()) {
            assertNotSame(EventType.CAUGHT, e.evtType());
        }
        assertSame(EventType.SPOTTED, game.getLog().getEvents().get(7).evtType());
        assertSame(EventType.ESCAPED, game.getLog().getEvents().getLast().evtType());
        assertTrue(game.getStash().getItems().contains(definitions.findLoot("candelabra_b")));
        assertTrue(game.getStash().getItems().contains(definitions.findLoot("safe_contents")));
        assertEquals(2, game.getCalendar().getCurrentDay());
    }

    @Test
    public void test4_seenCaught() {
        game.setMoney(100);
        game.recruit("thief", "Thief A");

        Target target = game.getAvailableTargets().getFirst();

        List<Action> actions = new ArrayList<>();
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new PickLockAction("d3", 2 ));
        actions.add(new MoveAction("VAULT", 1 ));
        actions.add(new CrackAction("Vault Safe", 3));
        actions.add(new GrabAction(definitions.findLoot("safe_contents")));
        actions.add(new GrabAction(definitions.findLoot("candelabra_b")));
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new MoveAction("OFFICE", 1));
        actions.add(new WaitAction(1));
        actions.add(new MoveAction("LOBBY", 1));
        actions.add(new MoveAction("OUTSIDE", 1));

        game.runHeist(target, Map.of(game.getRoster().getFirst(), new ActionQueue(actions)));

        assertSame(EventType.SPOTTED, game.getLog().getEvents().get(7).evtType());
        assertSame(EventType.CAUGHT, game.getLog().getEvents().getLast().evtType());
        assertTrue(game.getStash().getItems().isEmpty());
        assertEquals(5, game.getRoster().getFirst().getReleaseDate());
    }

    @Test
    public void test5_twoMemberSync() {
        game.setMoney(100);
        game.recruit("thief", "Thief A");
        game.recruit("thief", "Thief B");

        Target target = game.getAvailableTargets().getFirst();

        Map<RosterEntry, ActionQueue> queues = new HashMap<>();

        List<Action> actions = new ArrayList<>();
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new PickLockAction("d3", 2 ));
        actions.add(new MoveAction("VAULT", 1 ));
        actions.add(new CrackAction("Vault Safe", 3));
        actions.add(new GrabAction(definitions.findLoot("safe_contents")));
        actions.add(new GrabAction(definitions.findLoot("candelabra_b")));
        actions.add(new WaitAction(2));
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new MoveAction("OUTSIDE", 1 ));
        queues.put(game.getRoster().getFirst(), new ActionQueue(actions));

        actions = new ArrayList<>();
        actions.add(new WaitAction(5));
        actions.add(new MoveAction("LOBBY", 1));
        actions.add(new GrabAction(definitions.findLoot("register_cash")));
        actions.add(new MoveAction("OUTSIDE", 1));
        queues.put(game.getRoster().getLast(), new ActionQueue(actions));

        game.runHeist(target, queues);
        for (Event e : game.getLog().getEvents()) {
            assertNotSame(EventType.SPOTTED, e.evtType());
        }


        assertTrue(game.getStash().getItems().contains(definitions.findLoot("candelabra_b")));
        assertTrue(game.getStash().getItems().contains(definitions.findLoot("safe_contents")));
        assertTrue(game.getStash().getItems().contains(definitions.findLoot("register_cash")));
        assertEquals(2, game.getCalendar().getCurrentDay());

    }
}
