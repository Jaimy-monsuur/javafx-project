package nl.inholland.javafx.Ui.Forms;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.converter.LocalTimeStringConverter;
import nl.inholland.javafx.Data.Database;
import nl.inholland.javafx.Models.Movie;
import nl.inholland.javafx.Models.Showing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ManageShowingsForm extends GridPane {

    private Button addShowingBtn;
    private Button clearBtn;
    private Label errorLabel;
    private ComboBox moviesCBX;
    private ComboBox roomCBX;
    private Showing newShowing;
    private Label priceData;
    private Label noSeatsData;
    private Label endTimeLbl;
    private LocalDateTime start;
    private LocalDateTime end;
    private DatePicker datePicker;
    private Movie selectedMovie;
    private Spinner<LocalTime> timePicker;
    private SpinnerValueFactory TimePickerValueFactory = new SpinnerValueFactory<LocalTime>() {
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            setConverter(new LocalTimeStringConverter(formatter, null));
            setValue(LocalTime.now());
        }

        @Override//als tijd omhoog
        public void decrement(int steps) {
            LocalTime time = getValue();
            setValue(time.minusMinutes(steps));
        }

        @Override//als tijd omlaag
        public void increment(int steps) {
            LocalTime time = getValue();
            setValue(time.plusMinutes(steps));
        }
    };

    public ManageShowingsForm(Database db) {
        //making all labels and stuff
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setVgap(20);
        this.setHgap(30);
        SetTextLabels();
        SetData(db);

    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public Movie getSelectedMovie() {
        return selectedMovie;
    }

    public void setSelectedMovie(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
    }

    public Showing getNewShowing() {
        return newShowing;
    }

    public void setNewShowing(Showing newShowing) {
        this.newShowing = newShowing;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public Button getAddShowingBtn() {
        return addShowingBtn;
    }

    public Button getClearBtn() {
        return clearBtn;
    }

    private void SetData(Database db) {
        moviesCBX = new ComboBox();
        //fill combobox with movies
        for (Movie m : db.getMovies()) {
            moviesCBX.getItems().add(m.getName());
        }
        this.add(moviesCBX, 1, 0);
        // moviesCBX listener
        moviesCBX.valueProperty().addListener((observableValue, o, t1) -> {
            if (moviesCBX.getValue() != null) {
                setSelectedMovie(db, t1.toString());
                setEndTime();
            }
        });
        //combobox room
        roomCBX = new ComboBox();
        roomCBX.setVisibleRowCount(5);
        roomCBX.getItems().addAll("Room 1", "Room 2");
        this.add(roomCBX, 1, 1);
        //labels with data
        noSeatsData = new Label();
        this.add(noSeatsData, 1, 2);
        //set listener
        roomCBX.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> {
            if (roomCBX.getValue() != null) {
                if (t1.toString().equals("Room 1")) {
                    noSeatsData.setText("200");
                } else {
                    noSeatsData.setText("100");
                }
            }
        });
        datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        //disable old dates
        LocalDate minDate = LocalDate.now();
        datePicker.setDayCellFactory(d ->
                new DateCell() {// datum mag niet in het verleden zijn
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isBefore(minDate));
                    }
                });
        //add datepicket
        this.add(datePicker, 3, 0);

        timePicker = new Spinner<>();
        timePicker.setValueFactory(TimePickerValueFactory);
        this.add(timePicker, 4, 0);

        endTimeLbl = new Label();
        this.add(endTimeLbl, 3, 1);
        // listener that calculate end time
        datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                setEndTime();
            }
        });

        timePicker.valueProperty().addListener(new ChangeListener<LocalTime>() {
            @Override
            public void changed(ObservableValue<? extends LocalTime> observableValue, LocalTime localTime, LocalTime t1) {
                setEndTime();
            }
        });

        addShowingBtn = new Button("Add showing");
        this.add(addShowingBtn, 5, 1);
        clearBtn = new Button("Clear");
        this.add(clearBtn, 5, 2);
        errorLabel = new Label();
        this.add(errorLabel, 0, 3, 5, 3);
    }

    private void SetTextLabels() {
        //text labels
        Label movieName = new Label("Movie title:");
        movieName.getStyleClass().add("bold");
        this.add(movieName, 0, 0);
        Label room = new Label("Room:");
        room.getStyleClass().add("bold");
        this.add(room, 0, 1);
        Label noSeatsTxt = new Label("No. seats:");
        noSeatsTxt.getStyleClass().add("bold");
        this.add(noSeatsTxt, 0, 2);

        //row 2
        Label start = new Label("Start:");
        start.getStyleClass().add("bold");
        this.add(start, 2, 0);
        Label end = new Label("End:");
        end.getStyleClass().add("bold");
        this.add(end, 2, 1);
        Label price = new Label("Price:");
        price.getStyleClass().add("bold");
        this.add(price, 2, 2);
        priceData = new Label("12.50");
        this.add(priceData, 3, 2);
    }

    public void setSelectedMovie(Database db, String t1) {
        for (Movie m : db.getMovies()) {
            if (m.getName().equals(t1)) {
                this.setSelectedMovie(m);
                break;
            }
        }
    }

    private void setEndTime() {
        //calculate the end time of the showing
        if (selectedMovie != null) {
            LocalDateTime time = LocalDateTime.of(datePicker.getValue(), timePicker.getValue());
            setStart(time);
            time = time.plusMinutes(getSelectedMovie().getDuration().toMinutes());
            setEnd(time);
            endTimeLbl.setText(time.toLocalDate().toString() + " " + time.toLocalTime().getHour() + ":" + time.toLocalTime().getMinute());
        }
    }

    public void Clear() {
        datePicker.setValue(LocalDate.now());
        moviesCBX.valueProperty().set(null);
        roomCBX.valueProperty().set(null);
    }

    public void ErrorMessage() {
        errorLabel.setText("Showing overlaps with already existing showing");
        errorLabel.setTextFill(Color.rgb(255, 0, 0));
    }

    public void MakeShowing(Database db) {
        errorLabel.setText("");
        if (moviesCBX.getValue() != null && roomCBX.getValue() != null && !endTimeLbl.getText().equals("")) {
            setEndTime();
            int seats = 0;
            if (roomCBX.getSelectionModel().getSelectedItem().toString().equals("Room 1")) {
                seats = 200;
            } else {
                seats = 100;
            }
            setNewShowing(new Showing(getSelectedMovie(), getStart(), getEnd(), seats));
        } else {
            setNewShowing(null);
            errorLabel.setText("fill in all fields");
            errorLabel.setTextFill(Color.rgb(255, 0, 0));
        }
    }
}

