# Lockstep

A turn-based heist planning game with deterministic execution.

You are given a target - including the floor plan, active security, and loot -
and you write a plan. This involves an ordered queue of actions per crew member,
with waits and start delays to synchronise them. Then you watch the outcome,
step by step. Good planning gets you out safely with loot; bad planning can get
you spotted, start an alarm countdown, or even arrested — costing you days on a
constantly shifting campaign calendar.

## Status

Base development complete. JavaFX implemented & playable. More content to come

## Design principles

- **Deterministic:** No variance during a run. The same plan on the same target
with the same map will produce an identical run, down to the step.
- **Full information:** Everything about a target is shown before you plan. The
sole variance is in the map generation - which the player doesn't see.
- **No intervention:** Once execution starts, the plan executed without player
edits or aborts.
- **Jailtime isn't the end:** You can still recover from being arrested, even with
the calendar moving along.

## Technology

- Java (JDK 21)
- Maven
- Jackson (JSON data definitions)
- JUnit 5 (scripted tests)
- JavaFX (planned UI shell)

## Building and testing

For running the main app:
`mvn javafx:run`

For running the tests:
 `mvn test`