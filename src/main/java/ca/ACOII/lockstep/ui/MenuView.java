package ca.ACOII.lockstep.ui;

import ca.ACOII.lockstep.Game;
import ca.ACOII.lockstep.campaign.RosterEntry;
import ca.ACOII.lockstep.model.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MenuView extends BorderPane {
    Game game;
    App app;
    public MenuView(App app) {
        this.app = app;
        this.game = app.game;
        build();
    }

    private void build() {
        setBanner();
        setCenterContent();
    }

    private void setBanner() {
        Label label1 = new Label("Day: " + game.getCalendar().getCurrentDay());
        label1.setMinWidth(Region.USE_PREF_SIZE);
        Label label2 = new Label("Money: " + game.getStash().getMoney());
        label2.setMinWidth(Region.USE_PREF_SIZE);
        Button btn = new Button("Back to Campaign");
        btn.setOnAction(e -> {
            app.showScreen(Screen.CAMPAIGN);
        });
        btn.setMinWidth(Region.USE_PREF_SIZE);

        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);

        HBox banner = new HBox(10, label1, label2, space, btn);
        banner.setPadding(new Insets(20));
        setTop(banner);
    }
    private void setCenterContent() {
        TabPane pane = new TabPane(rosterTab(), stashTab(), recruitTab());
        setCenter(pane);
    }

    private Tab rosterTab() {
        VBox content = new VBox(10);
        HBox detailsList = new HBox(50);

        ListView<RosterEntry> entries = getRosterEntryListView();
        entries.getItems().addAll(game.getRoster());
        entries.getSelectionModel().clearSelection();

        TextArea entryDetails = new TextArea();
        entryDetails.setEditable(false);

        HBox actionList = new HBox(50);

        VBox skillBox = new VBox(10);
        ComboBox<Skill> skills = getSkillComboBox();

        skills.getItems().addAll(app.def.skills.getSkills());
        skills.getSelectionModel().selectFirst();
        skills.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> entries.getSelectionModel().clearSelection());

        Button train = new Button("Train Skill");
        train.setOnAction(e -> {
            game.train(entries.getSelectionModel().getSelectedItem(), skills.getSelectionModel().getSelectedItem().getId());
            refresh();
        });
        train.setDisable(true);
        skillBox.setAlignment(Pos.CENTER);

        VBox gearBox = new VBox(10);
        ComboBox<Gear> gear = getGearComboBox();
        gear.getItems().addAll(app.def.gear.getGear());
        gear.getSelectionModel().selectFirst();
        Button buyGear = new Button("Buy Gear");
        buyGear.setOnAction(e -> {
            game.buyGear(entries.getSelectionModel().getSelectedItem(), gear.getSelectionModel().getSelectedItem());
            refresh();
        });
        buyGear.setDisable(true);
        entries.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            Skill skill = skills.getSelectionModel().getSelectedItem();
            if (newVal != null) {
                String txt = skill.getName() + " (Cost: " + (skill.getTrainingCost()) * newVal.getData().getSkills().get(skill.getId()) + ")";
                skills.getButtonCell().setText(txt);
                entryDetails.setText(getEntryDetails(newVal));
                buyGear.setDisable(false);
                train.setDisable(false);
            } else {
                skills.getButtonCell().setText(skill.getName());
                entryDetails.setText("");
                buyGear.setDisable(true);
                train.setDisable(true);
            }
        } );
        gearBox.setAlignment(Pos.CENTER);


        skillBox.getChildren().addAll(skills, train);
        gearBox.getChildren().addAll(gear, buyGear);
        detailsList.getChildren().addAll(entries, entryDetails);
        actionList.getChildren().addAll(skillBox, gearBox);
        actionList.setAlignment(Pos.CENTER);

        content.getChildren().addAll(detailsList, actionList);
        content.setPadding(new Insets(30, 100, 30, 100));

        Tab tab = new Tab();
        tab.setContent(content);
        tab.setText("Roster");
        tab.setClosable(false);
        return tab;
    }

    private String getEntryDetails(RosterEntry entry) {
        StringBuilder txt = new StringBuilder(entry.getData().getName() + " (" + entry.getData().getId() + ")");
        txt.append("\n\nSkills:");
        for (String str : entry.getData().getSkills().keySet()) {
            Skill s = app.def.findSkill(str);
            txt.append("\n").append(s.getName()).append(" level: ").append(entry.getData().getSkills().get(str));
        }
        txt.append("\n\nGear");
        for (Gear g : entry.getGear()) {
            txt.append("\n").append(g.getName()).append(" - ");
            for (GearEffect eff : g.getEffects()) {
                txt.append(app.def.findSkill(eff.getSkill()).getName()).append(": +").append(-eff.getStepDelta()).append(", ");
            }
            txt.delete(txt.length() - 2, txt.length());
        }
        return txt.toString();
    }

    private ListView<RosterEntry> getRosterEntryListView() {
        ListView<RosterEntry> entries = new ListView<>();

        entries.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(RosterEntry entry, boolean empty) {
                super.updateItem(entry, empty);
                if (empty || entry == null) {
                    setText(null);
                } else {
                    StringBuilder txt = new StringBuilder();
                    if (entry.getReleaseDate() > game.getCalendar().getCurrentDay()) {
                        txt.append(" [ARRESTED UNTIL Day ").append(entry.getReleaseDate()).append("]");
                    }
                    setText(entry.getData().getName() + " " + txt.toString().trim());
                }
            }
        });
        return entries;
    }

    private ComboBox<Gear> getGearComboBox() {
        ComboBox<Gear> gear = new ComboBox<>();
        gear.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Gear gear, boolean empty) {
                super.updateItem(gear, empty);
                if (empty || gear == null) {
                    setText(null);
                } else {
                    setText(gear.getName() + " (Price: " + gear.getPrice() + ")");
                }
            }
        });
        gear.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Gear gear, boolean empty) {
                super.updateItem(gear, empty);
                if (empty || gear == null) {
                    setText(null);
                } else {
                    setText(gear.getName() + " (Price: " + gear.getPrice() + ")");
                }
            }
        });
        return gear;
    }

    private ComboBox<Skill> getSkillComboBox() {
        ComboBox<Skill> skills = new ComboBox<>();
        skills.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Skill skill, boolean empty) {
                super.updateItem(skill, empty);
                if (empty || skill == null) {
                    setText(null);
                } else {
                    setText(skill.getName());
                }
            }
        });
        skills.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Skill skill, boolean empty) {
                super.updateItem(skill, empty);
                if (empty || skill == null) {
                    setText(null);
                } else {
                    setText(skill.getName());
                }
            }
        });
        return skills;
    }

    private Tab stashTab() {
        HBox content = new HBox(50);

        VBox itemSelling = new VBox(10);
        ListView<LootItem> items = getLootItemListView();
        items.getItems().addAll(game.getStash().getItems());
        items.getSelectionModel().selectFirst();
        Button sellItemBtn = new Button("Sell Item");
        sellItemBtn.setOnAction(e -> {
            game.getStash().sellItem(items.getSelectionModel().getSelectedItem().getId());
            refresh();
        });
        itemSelling.getChildren().addAll(items, sellItemBtn);
        itemSelling.setAlignment(Pos.CENTER);

        VBox setSelling = new VBox(10);
        ListView<LootSet> sets = new ListView<>();
        sets.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(LootSet set, boolean empty) {
                super.updateItem(set, empty);
                if (empty || set == null) {
                    setText(null);
                } else {
                    setText(set.getName() + " (Worth " + set.getSetValue() + ")");
                }
            }
        });
        sets.getItems().addAll(app.def.sets.getSets());
        sets.getSelectionModel().selectFirst();
        Button sellSetButton = new Button("Sell Set");
        sellSetButton.setOnAction(e -> {
            game.getStash().sellSet(sets.getSelectionModel().getSelectedItem());
            refresh();
        });
        setSelling.getChildren().addAll(sets, sellSetButton);
        setSelling.setAlignment(Pos.CENTER);

        content.getChildren().addAll(itemSelling, setSelling);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30, 100, 30, 100));

        Tab tab = new Tab();
        tab.setContent(content);
        tab.setText("Stash");
        tab.setClosable(false);
        return tab;
    }

    private ListView<LootItem> getLootItemListView() {
        ListView<LootItem> items = new ListView<>();
        items.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(LootItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else if (item.getSet() == null) {
                    setText(item.getName() + " (Worth " + item.getPieceValue() + ")");
                } else {
                    setText(item.getName() + " [" + item.getSet() + " Set] (Worth " + item.getPieceValue() + ")");
                }
            }
        });
        return items;
    }

    private Tab recruitTab() {
        HBox content = new HBox(10);

        TextArea details = new TextArea();
        details.setEditable(false);

        VBox listContainer = new VBox(10);
        ListView<CrewMember> hireList = new ListView<>();
        hireList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(CrewMember member, boolean empty) {
                super.updateItem(member, empty);
                if (empty || member == null) {
                    setText(null);
                } else {
                    setText(member.getName());
                }
            }
        });
        for (CrewMember crew : app.def.crew) {
            if (crew.getDayAvailability() <= game.getCalendar().getCurrentDay()) {
                hireList.getItems().add(crew);
            }
        }
        hireList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            details.clear();
            details.appendText("Name: " + newVal.getName() + "\n");
            details.appendText("Skills:\n");
            for (String str : newVal.getSkills().keySet()) {
                details.appendText(str + " Level: " + newVal.getSkills().get(str) + "\n");
            }
            details.appendText("Price: " + newVal.getRecruitFee());
        });

        HBox nameBox = new HBox(10);
        Label label = new Label("Name: ");
        TextField field = new TextField();
        nameBox.getChildren().addAll(label, field);

        Button recruitBtn = new Button("Hire Crew Member");
        recruitBtn.setOnAction(e -> {
            game.recruit(hireList.getSelectionModel().getSelectedItem().getId(), field.getText());
            refresh();
        });
        listContainer.getChildren().addAll(hireList, nameBox, recruitBtn);

        content.getChildren().addAll(listContainer, details);

        Tab tab = new Tab();
        tab.setContent(content);
        tab.setText("Recruitment");
        tab.setClosable(false);
        return tab;
    }

    public void refresh() {
        this.getChildren().removeAll();
        this.build();
    }
}
