package nl.inholland.javafx;

import javafx.application.Application;
import javafx.stage.Stage;
import nl.inholland.javafx.Ui.Windows.LogIn;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //open login form
        LogIn logIn = new LogIn();
        logIn.getStage().show();
    }
}
