package ca.ACOII.lockstep.ui;

import ca.ACOII.lockstep.Game;
import ca.ACOII.lockstep.campaign.RosterEntry;
import ca.ACOII.lockstep.model.Target;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;

import java.util.List;

public class CampaignView extends BorderPane {
    App app;
    Game game;
    ListView<Target> targets;

    public CampaignView(App app) {
        this.app = app;
        this.game = app.game;
        build();
    }

    private void build() {
        setBanner();
        setTargetList();
        setFooter();
    }

    private void setFooter() {
        Button btn = new Button("Enter Heist");
        btn.setOnAction(e -> {
            app.startHeist(targets.getSelectionModel().getSelectedItem(), game.getRoster());
        });
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(50));


        Region space1 = new Region();
        Region space2 = new Region();
        HBox.setHgrow(space1, Priority.ALWAYS);
        HBox.setHgrow(space2, Priority.ALWAYS);

        buttonBox.getChildren().addAll(space1, btn, space2);
        setBottom(buttonBox);
    }

    private void setTargetList() {
        targets = new ListView<>();
        targets.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Target target, boolean empty) {
                super.updateItem(target, empty);
                if (empty || target == null) {
                    setText(null);
                } else if (target.getAvailableFrom() == 0 && target.getAvailabilityDuration() == 0) {
                    setText(target.getName() + " (~ PRESENT)");
                } else if (target.getAvailabilityDuration() == 0) {
                    setText(target.getName() + " (Days " + target.getAvailableFrom()
                            + "- PRESENT)");
                } else if (target.getAvailableFrom() == 0) {
                    setText(target.getName() + " (Until Day " + target.getAvailabilityDuration());
                } else  {
                    setText(target.getName() + " (Days " + target.getAvailableFrom()
                            + "–" + (target.getAvailableFrom() + target.getAvailabilityDuration()) + ")");
                }
            }
        });
        targets.getItems().setAll(game.getAvailableTargets());

        VBox container = new VBox(10, targets);
        container.setPadding(new Insets(30, 100, 30, 100));
        setCenter(container);
    }

    private void setBanner() {
        Label label1 = new Label("Day: " + game.getCalendar().getCurrentDay());
        label1.setMinWidth(Region.USE_PREF_SIZE);
        Label label2 = new Label("Money: " + game.getStash().getMoney());
        label2.setMinWidth(Region.USE_PREF_SIZE);
        Button btn = new Button("Menu");
        btn.setOnAction(e -> {
            app.showScreen(Screen.MENU);
        });
        btn.setMinWidth(Region.USE_PREF_SIZE);

        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);

        HBox banner = new HBox(10, label1, label2, space, btn);
        banner.setPadding(new Insets(20));
        setTop(banner);
    }

    public void refresh() {
        this.getChildren().removeAll();
        this.build();
    }
}
