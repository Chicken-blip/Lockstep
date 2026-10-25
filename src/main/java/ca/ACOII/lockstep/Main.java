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

        Game game = new Game(false);


        game.buyGear(game.getRoster().getFirst(), x.findGear("fine_lockpicks"));

        game.train(game.getRoster().getFirst(), "pick", 25);
    }

}
