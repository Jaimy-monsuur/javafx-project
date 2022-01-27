package nl.inholland.javafx.Ui.Views;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import nl.inholland.javafx.Data.Database;
import nl.inholland.javafx.Models.Movie;
import nl.inholland.javafx.Models.Showing;


public class MoviesTableView extends VBox {

    private TableView<Movie> table = new TableView<>();

    public MoviesTableView(Database db) {
        ObservableList<Movie> data = FXCollections.observableList(db.getMovies());

        this.setPadding(new Insets(16));
        Label title = new Label();
        title.setText("Movies");
        title.setFont(new Font("Arial Bold", 20));

        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);
        //table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn movieName = new TableColumn("Movie name");
        movieName.setMinWidth(350);
        movieName.setCellValueFactory(new PropertyValueFactory<Showing, String>("name"));

        TableColumn ticketPrice = new TableColumn("Price");
        ticketPrice.setMinWidth(150);
        ticketPrice.setCellValueFactory(new PropertyValueFactory<Showing, String>("ticketPrice"));

        TableColumn<Movie, String> duration = new TableColumn<>("Duration");
        duration.setMinWidth(150);
        duration.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDuration().toMinutes() + " minutes")));

        table.setItems(data);
        table.getColumns().addAll(movieName, ticketPrice, duration);

        this.getChildren().addAll(title, table);
    }

    public TableView<Movie> getTable() {
        return table;
    }

    public void Update() {
        table.refresh();
    }

}
