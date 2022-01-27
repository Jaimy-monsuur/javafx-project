package nl.inholland.javafx.Ui.Windows;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nl.inholland.javafx.Data.Database;
import nl.inholland.javafx.Models.Movie;
import nl.inholland.javafx.Models.User;
import nl.inholland.javafx.Ui.Forms.ManageMoviesForm;
import nl.inholland.javafx.Ui.Menus.ManageMoviesMenu;
import nl.inholland.javafx.Ui.StyledScene;
import nl.inholland.javafx.Ui.Views.MoviesTableView;

import java.time.Duration;
import java.util.Objects;


public class ManageMoviesScene {
    private Stage stage;
    private Database db;

    public ManageMoviesScene(Database db, User user) {
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
        ManageMoviesMenu manageMoviesMenu = new ManageMoviesMenu();
        //open purchase ticket
        manageMoviesMenu.getPurchaseTicketItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MainScene mainScene = new MainScene(getDb(), user);
                mainScene.getStage().show();
                getStage().close();
            }
        });
        manageMoviesMenu.getManageShowingsItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ManageShowingsScene manageShowingsScene = new ManageShowingsScene(getDb(), user);
                manageShowingsScene.getStage().show();
                getStage().close();
            }
        });
        //logout
        manageMoviesMenu.getLogoutItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LogIn logIn = new LogIn(getDb());
                logIn.getStage().show();
                getStage().close();
            }
        });
        //make rooms and room layout
        MoviesTableView tableViewMovies = new MoviesTableView(db);
        HBox roomLayout = new HBox();
        roomLayout.getStyleClass().add("border");
        roomLayout.getChildren().addAll(tableViewMovies);
        // SellTicketForm
        ManageMoviesForm form = new ManageMoviesForm(getDb());
        form.getStyleClass().add("border");
        //form events
        //add movie
        form.getAddMoviesBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //stuff
                if (!Objects.equals(form.getMovieName().getText(), "") && !Objects.equals(form.getMovieLength().getText(), "")) {
                    String name = form.getMovieName().getText();
                    Duration duration = Duration.ofMinutes(Long.parseLong(form.getMovieLength().getText()));
                    Movie newMovie = new Movie(name, 12.50, duration);
                    getDb().getMovies().add(newMovie);
                    form.Clear();
                    tableViewMovies.getTable().refresh();
                } else {
                    form.getErrorLabel().setText("fill in all fields");
                    form.getErrorLabel().setTextFill(Color.rgb(255, 0, 0));
                }
            }
        });
        //clear
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
        layout.getChildren().addAll(manageMoviesMenu, title, roomLayout, form);
        // complete stage
        stage.setTitle("Fantastic cinema - Manage showings");
        stage.setScene(showingsScene);
    }

    public Database getDb() {
        return db;
    }

    public Stage getStage() {
        return stage;
    }
}
