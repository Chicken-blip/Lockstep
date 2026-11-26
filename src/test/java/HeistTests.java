import ca.ACOII.lockstep.Game;
import ca.ACOII.lockstep.campaign.RosterEntry;
import ca.ACOII.lockstep.engine.*;
import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.LootItem;
import ca.ACOII.lockstep.model.Target;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

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

        //Getting Corner Gallery target
        Target target = definitions.findTarget("corner_gallery");

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

        game.prepareHeist(target);
        game.runHeist(Map.of(game.getRoster().getFirst(), new ActionQueue(actions)), null);

        for (Event e : game.getLog().getEvents()) {
            assertTrue(e.evtType() != EventType.CAUGHT && e.evtType() != EventType.GUARD_SPOTTED);
        }
        assertSame(EventType.ESCAPED, game.getLog().getEvents().getLast().evtType());

        List<String> inv = game.getStash().getItems().stream().map(LootItem::getId).toList();

        assertTrue(inv.contains("candelabra_b"));
        assertTrue(inv.contains("safe_contents"));
        assertEquals(2, game.getCalendar().getCurrentDay());

    }

    @Test
    public void test2_completeSetRun() {
        game.setMoney(100);
        game.recruit("thief", "Thief A");

        //Getting Corner Gallery target
        Target target = definitions.findTarget("corner_gallery");

        List<Action> actions = new ArrayList<>();
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new WaitAction(2));
        actions.add(new MoveAction("OFFICE", 1));
        actions.add(new WaitAction(1));
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new PickLockAction("d3", 2 ));
        actions.add(new MoveAction("VAULT", 1 ));
        actions.add(new CrackAction("Vault Safe", 3));
        actions.add(new GrabAction(definitions.findLoot("safe_contents")));
        actions.add(new GrabAction(definitions.findLoot("candelabra_b")));
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new GrabAction(definitions.findLoot("candelabra_a")));
        actions.add(new MoveAction("OUTSIDE", 1 ));

        game.prepareHeist(target);
        game.runHeist(Map.of(game.getRoster().getFirst(), new ActionQueue(actions)), null);

        for (Event e : game.getLog().getEvents()) {
            assertNotSame(EventType.CAUGHT, e.evtType());
        }
        assertSame(EventType.ESCAPED, game.getLog().getEvents().getLast().evtType());

        List<String> inv = game.getStash().getItems().stream().map(LootItem::getId).toList();

        assertTrue(inv.contains("candelabra_b"));
        assertTrue(inv.contains("candelabra_a"));
        assertTrue(inv.contains("safe_contents"));
        assertEquals(2, game.getCalendar().getCurrentDay());
    }

    @Test
    public void test3_seenEscaped() {
        game.setMoney(100);
        game.recruit("thief", "Thief A");

        //Getting Corner Gallery target
        Target target = definitions.findTarget("corner_gallery");

        List<Action> actions = new ArrayList<>();
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new PickLockAction("d3", 2 ));
        actions.add(new MoveAction("VAULT", 1 ));
        actions.add(new CrackAction("Vault Safe", 3));
        actions.add(new GrabAction(definitions.findLoot("safe_contents")));
        actions.add(new GrabAction(definitions.findLoot("candelabra_b")));
        actions.add(new MoveAction("LOBBY", 1 ));
        actions.add(new MoveAction("OUTSIDE", 1 ));

        game.prepareHeist(target);
        game.runHeist(Map.of(game.getRoster().getFirst(), new ActionQueue(actions)), null);

        for (Event e : game.getLog().getEvents()) {
            assertNotSame(EventType.CAUGHT, e.evtType());
        }
        assertSame(EventType.GUARD_SPOTTED, game.getLog().getEvents().get(10).evtType());
        assertSame(EventType.ESCAPED, game.getLog().getEvents().getLast().evtType());

        List<String> inv = game.getStash().getItems().stream().map(LootItem::getId).toList();

        assertTrue(inv.contains("candelabra_b"));
        assertTrue(inv.contains("safe_contents"));
        assertEquals(2, game.getCalendar().getCurrentDay());
    }

    @Test
    public void test4_seenCaught() {
        game.setMoney(100);
        game.recruit("thief", "Thief A");

        //Getting Corner Gallery target
        Target target = definitions.findTarget("corner_gallery");

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

        game.prepareHeist(target);
        game.runHeist(Map.of(game.getRoster().getFirst(), new ActionQueue(actions)), null);

        assertSame(EventType.GUARD_SPOTTED, game.getLog().getEvents().get(10).evtType());
        assertSame(EventType.CAUGHT, game.getLog().getEvents().getLast().evtType());
        assertTrue(game.getStash().getItems().isEmpty());
        assertEquals(5, game.getRoster().getFirst().getReleaseDate());
    }

    @Test
    public void test5_twoMemberSync() {
        game.setMoney(100);
        game.recruit("thief", "Thief A");
        game.recruit("thief", "Thief B");

        //Getting Corner Gallery target
        Target target = definitions.findTarget("corner_gallery");

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
        actions.add(new WaitAction(1));
        actions.add(new MoveAction("OUTSIDE", 1));
        queues.put(game.getRoster().getLast(), new ActionQueue(actions));

        game.prepareHeist(target);
        game.runHeist(queues, null);
        for (Event e : game.getLog().getEvents()) {
            assertNotSame(EventType.GUARD_SPOTTED, e.evtType());
        }

        List<String> inv = game.getStash().getItems().stream().map(LootItem::getId).toList();

        assertTrue(inv.contains("candelabra_b"));
        assertTrue(inv.contains("safe_contents"));
        assertEquals(2, game.getCalendar().getCurrentDay());

    }

    @Test
    public void test_invalidActionDoesNotStopRemainingQueue() {
        game.setMoney(100);
        game.recruit("thief", "Thief A");

        Target target = definitions.findTarget("corner_gallery");
        List<Action> actions = new ArrayList<>();
        actions.add(new GrabAction(definitions.findLoot("safe_contents")));
        actions.add(new WaitAction(1));

        game.prepareHeist(target);
        game.runHeist(Map.of(game.getRoster().getFirst(), new ActionQueue(actions)), null);

        List<EventType> eventTypes = game.getLog().getEvents().stream().map(Event::evtType).toList();
        assertTrue(eventTypes.contains(EventType.ACTION_FAILED));
        assertTrue(eventTypes.contains(EventType.WAIT));
    }

    @Test
    public void test1_heistInstanceGeneration() {
        HeistInstance instance = HeistGenerator.generate(definitions.findTarget("corner_gallery"), definitions, 42L);
        assertEquals("safe_contents", instance.getMainScoreId());
        assertTrue(instance.getContainerLoot("Vault Safe").contains("safe_contents"));
        instance = HeistGenerator.generate(definitions.findTarget("corner_gallery"), definitions, 42L);
        assertEquals("safe_contents", instance.getMainScoreId());
        assertTrue(instance.getContainerLoot("Vault Safe").contains("safe_contents"));
    }

    /*
    @Test
    public void test2_heistInstanceGeneration() {
        HeistInstance instance = null;
        Set<Boolean> bools = new HashSet<>();
        int count = 0;
        for (long i = 1L; Math.abs(i) < Long.MAX_VALUE;) {
            instance = HeistGenerator.generate(definitions.targets.getFirst(), definitions, i);
            bools.add(instance.getRoomLoot("LOBBY").contains("register_cash"));
            count++;
            if (count > 100) i = Long.MAX_VALUE; else  i += Long.MAX_VALUE / 100;
        }
        assertEquals(2, bools.size());
    }
     */
}
