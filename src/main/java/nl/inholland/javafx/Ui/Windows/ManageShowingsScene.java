package nl.inholland.javafx.Ui.Windows;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nl.inholland.javafx.Data.Database;
import nl.inholland.javafx.Models.Showing;
import nl.inholland.javafx.Models.User;
import nl.inholland.javafx.Ui.Forms.ManageShowingsForm;
import nl.inholland.javafx.Ui.Menus.ManageShowingsMenu;
import nl.inholland.javafx.Ui.StyledScene;
import nl.inholland.javafx.Ui.Views.Room1TableView;
import nl.inholland.javafx.Ui.Views.Room2TableView;


public class ManageShowingsScene {
    private Stage stage;
    private Database db;

    public ManageShowingsScene(Database db, User user) {
        //make db
        this.db = db;
        // new Stage
        stage = new Stage();
        //layout of stage
        VBox layout = new VBox();
        //Title
        Label title = new Label();
        title.setPadding(new Insets(10, 10, 10, 10));
        title.setText("Manage Showings");
        title.setFont(new Font("Arial Bold", 22));
        // menu
        ManageShowingsMenu manageShowingsMenu = new ManageShowingsMenu();
        //open purchase ticket
        manageShowingsMenu.getPurchaseTicketItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MainScene mainScene = new MainScene(getDb(), user);
                mainScene.getStage().show();
                getStage().close();
            }
        });
        //open manage movies
        manageShowingsMenu.getManageMoviesItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ManageMoviesScene manageMoviesScene = new ManageMoviesScene(getDb(), user);
                manageMoviesScene.getStage().show();
                getStage().close();
            }
        });

        //logout
        manageShowingsMenu.getLogoutItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LogIn logIn = new LogIn(getDb());
                logIn.getStage().show();
                getStage().close();
            }
        });
        //make rooms and room layout
        Room1TableView tableViewRoom1 = new Room1TableView(db);
        Room2TableView tableViewRoom2 = new Room2TableView(db);
        HBox roomLayout = new HBox();
        roomLayout.getStyleClass().add("border");
        roomLayout.getChildren().addAll(tableViewRoom1, tableViewRoom2);
        // SellTicketForm
        ManageShowingsForm form = new ManageShowingsForm(getDb());
        form.getStyleClass().add("border");
        //form events
        form.getAddShowingBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //stuff
                boolean valid = false;
                form.MakeShowing(db);
                // if making form was not allowed
                if (form.getNewShowing() != null) {
                    Showing s = form.getNewShowing();
                    //validating showing time
                    if (s.getNumberOfTickets() == 200) {
                        for (Showing sh : db.getRoom1().getShowings()) {//kijk naar room 1
                            if (form.getStart().isBefore(sh.getBeginTime().minusMinutes(15)) && form.getEnd().isBefore(sh.getBeginTime().minusMinutes(15)) || form.getStart().isAfter(sh.getEndTime().plusMinutes(15)) && form.getEnd().isAfter(sh.getEndTime().plusMinutes(15))) {
                                valid = true;
                            } else {
                                valid = false;
                                break;
                            }
                        }
                        if (valid) {
                            db.getRoom1().getShowings().add(s);
                            tableViewRoom1.getTable().refresh();
                            form.Clear();
                        } else {
                            form.ErrorMessage();
                        }
                    } else {
                        for (Showing sh : db.getRoom2().getShowings()) {//kijk naar room 2
                            if (form.getStart().isBefore(sh.getBeginTime().minusMinutes(15)) && form.getEnd().isBefore(sh.getBeginTime().minusMinutes(15)) || form.getStart().isAfter(sh.getEndTime().plusMinutes(15)) && form.getEnd().isAfter(sh.getEndTime().plusMinutes(15))) {
                                valid = true;
                            } else {
                                valid = false;
                                break;
                            }
                        }
                        if (valid) {
                            db.getRoom2().getShowings().add(s);
                            tableViewRoom2.getTable().refresh();
                            form.Clear();
                        } else {
                            form.ErrorMessage();
                        }
                    }
                }
            }
        });
        //cancel/clear
        form.getClearBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //stuff
                form.Clear();
            }
        });
        // Create styled scene
        Scene showingsScene = new StyledScene(layout);
        // set layout
        layout.getChildren().addAll(manageShowingsMenu, title, roomLayout, form);
        // complete stage
        stage.setTitle("Fantastic cinema - Manage movies");
        stage.setScene(showingsScene);
    }

    public Database getDb() {
        return db;
    }

    public Stage getStage() {
        return stage;
    }
}
