package ca.ACOII.lockstep.ui;

import ca.ACOII.lockstep.Game;
import ca.ACOII.lockstep.campaign.Campaign;
import ca.ACOII.lockstep.campaign.RosterEntry;
import ca.ACOII.lockstep.engine.*;
import ca.ACOII.lockstep.load.Definitions;
import ca.ACOII.lockstep.model.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class App extends Application {
    FloorPlanView mapPane;
    Game game = new Game(false);
    Definitions def = new Definitions();
    //TODO: Update when more actions are added
    private static final List<String> ACTIONS = List.of(
            "MOVE", "WAIT", "PICK LOCK", "CRACK SAFE", "GRAB"
    );
    Target target;
    List<RosterEntry> crew;

    Scene scene;
    public static void main(String[] args) {launch(args);}
    @Override
    public void start(Stage stage) {

        target = def.targets.getFirst();

        game.setMoney(100);
        game.recruit("thief", "Thief A");

        scene = new Scene(buildScreen(Screen.MENU), 800, 600);

        stage.setScene(scene);
        stage.setTitle("Lockstep");
        stage.setResizable(false);
        stage.show();
    }

    public void showScreen(Screen screen) {
        scene.setRoot(buildScreen(screen));
        if (scene.getRoot() instanceof CampaignView) ((CampaignView) scene.getRoot()).refresh();
    }

    private Parent buildScreen(Screen screen) {
        return switch (screen) {
            case HEIST -> new HeistView(target, crew, this);
            case CAMPAIGN -> new CampaignView(this);
            case MENU -> new MenuView(this);
        };
    }

    public void startHeist(Target target, List<RosterEntry> selectedCrew) {
        this.target = target;
        this.crew = selectedCrew;
        showScreen(Screen.HEIST);
    }
}
