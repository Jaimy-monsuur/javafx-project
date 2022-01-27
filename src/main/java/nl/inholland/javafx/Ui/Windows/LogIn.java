package nl.inholland.javafx.Ui.Windows;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nl.inholland.javafx.Data.Database;
import nl.inholland.javafx.Models.User;
import nl.inholland.javafx.Ui.StyledScene;

public class LogIn {
    private Stage stage;
    private Database db;

    public LogIn() {
        this.db = new Database();
        stage = new Stage();
        MakeStage();
    }

    public LogIn(Database db) {
        this.db = db;
        stage = new Stage();
        MakeStage();
    }

    public Database getDatabase() {
        return db;
    }

    public Stage getStage() {
        return stage;
    }

    protected void MakeStage() {
        stage.setTitle("Fantastic cinema - Login form");
        // gridpane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(70));
        gridPane.setVgap(30);
        gridPane.setHgap(30);
        //labels
        Label loginLabel = new Label("Log in");
        loginLabel.setFont(new Font("Arial Bold", 20));
        gridPane.add(loginLabel, 0, 0);

        Label userLabel = new Label("Username");
        gridPane.add(userLabel, 0, 1);

        TextField userInput = new TextField();
        gridPane.add(userInput, 1, 1);

        Label passwordLabel = new Label("Password");
        gridPane.add(passwordLabel, 0, 2);

        TextField passwordInput = new PasswordField();
        gridPane.add(passwordInput, 1, 2);

        Button logInButton = new Button("Log in");
        gridPane.add(logInButton, 1, 3);

        Scene scene = new StyledScene(gridPane);
        stage.setScene(scene);

        Label ErrorLabel = new Label("");
        gridPane.add(ErrorLabel, 1, 4);

        logInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                User loggedInUser = null;
                boolean loggedIn = false;
                ErrorLabel.setText("");
                for (User u : db.getUsers()) {
                    if (userInput.getText().equals(u.getUsername()) && passwordInput.getText().equals(u.getPassword())) {
                        loggedIn = true;
                        loggedInUser = u;
                    }
                }
                if (loggedIn) {
                    //open form
                    MainScene mainScene = new MainScene(getDatabase(), loggedInUser);
                    stage.hide();
                    mainScene.getStage().show();
                    stage.close();
                } else {
                    ErrorLabel.setText("Wrong input");
                    ErrorLabel.setTextFill(Color.rgb(255, 0, 0));
                }
            }
        });
    }

}
