package nl.inholland.javafx.Ui.Forms;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import nl.inholland.javafx.Data.Database;
import java.util.function.UnaryOperator;


public class ManageMoviesForm extends GridPane {
    //objects needen outside class
    private Button addMoviesBtn;
    private Button clearBtn;
    private Label errorLabel;
    private TextField movieName;
    private TextField movieLength;

    //getters and setters
    public Button getAddMoviesBtn() {
        return addMoviesBtn;
    }

    public TextField getMovieName() {
        return movieName;
    }

    public TextField getMovieLength() {
        return movieLength;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public Button addMoviesBtn() {
        return addMoviesBtn;
    }

    public Button getClearBtn() {
        return clearBtn;
    }

    public ManageMoviesForm(Database db) {
        //making all labels and stuff
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setVgap(20);
        this.setHgap(30);
        SetData(db);
    }

    private void SetData(Database db) {
        //labels
        errorLabel = new Label();
        this.add(errorLabel, 0, 3, 2, 3);
        Label movieNameLbl = new Label("Movie name");
        this.add(movieNameLbl, 0, 0);
        Label moviePriceLbl = new Label("Movie price");
        this.add(moviePriceLbl, 0, 1);
        Label moviePrice = new Label("12.50");
        this.add(moviePrice, 1, 1);
        Label movie_lengthLBL = new Label("Movie length (in minutes)");
        this.add(movie_lengthLBL, 0, 2);
        movieName = new TextField();
        this.add(movieName, 1, 0);
        movieLength = new TextField();
        //limit text field input
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (!change.getText().matches("[0-9]*")) {
                errorLabel.setText("Input must be 0-9");
                errorLabel.setTextFill(Color.rgb(255, 0, 0));
                return null;
            } else if (change.getControlNewText().matches("0")) {
                errorLabel.setText("Input can not begin with 0");
                errorLabel.setTextFill(Color.rgb(255, 0, 0));
                return null;
            } else if (change.getControlNewText().length() > 3) {
                errorLabel.setText("Length can not be bigger than 3 digits");
                errorLabel.setTextFill(Color.rgb(255, 0, 0));
                return null;
            } else {
                errorLabel.setText("");
                return change;
            }
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);// limit input
        movieLength.setTextFormatter(textFormatter);

        this.add(movieLength, 1, 2);
        clearBtn = new Button("Clear");
        this.add(clearBtn, 2, 2);
        addMoviesBtn = new Button("Add movies");
        this.add(addMoviesBtn, 2, 1);
    }

    public void Clear() {//clear input
        errorLabel.setText("");
        movieName.setText("");
        movieLength.setText("");
    }
}
