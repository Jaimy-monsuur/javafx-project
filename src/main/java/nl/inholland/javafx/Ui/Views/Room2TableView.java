package nl.inholland.javafx.Ui.Views;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import nl.inholland.javafx.Data.Database;
import nl.inholland.javafx.Models.Showing;

import java.time.format.DateTimeFormatter;


public class Room2TableView extends VBox {

    private TableView<Showing> table = new TableView<>();
    private ObservableList<Showing> data;

    public Room2TableView(Database db) {
        data = FXCollections.observableList(db.getRoom2().getShowings());

        this.setPadding(new Insets(16));

        Label title = new Label();
        title.setText("Room2");
        title.setFont(new Font("Arial Bold", 20));

        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);
        //table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //time format
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        TableColumn<Showing, String> startTime = new TableColumn("Start");
        startTime.setMinWidth(150);
        startTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBeginTime().toLocalDate() + " " + cellData.getValue().getBeginTime().toLocalTime().format(format)));//anders staat het niet in een mooit format

        TableColumn<Showing, String> endTime = new TableColumn("End");
        endTime.setMinWidth(150);
        endTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndTime().toLocalDate() + " " + cellData.getValue().getEndTime().toLocalTime().format(format))); //anders staat het niet in een mooit format

        // heb het hier anders gedaan want kon anders niet bij de properties in movie
        TableColumn<Showing, String> movieTitle = new TableColumn<>("Title");
        movieTitle.setMinWidth(350);
        movieTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovie().getName()));

        TableColumn tickets = new TableColumn("Seats");
        tickets.setMinWidth(100);
        tickets.setCellValueFactory(new PropertyValueFactory<Showing, String>("numberOfTickets"));

        TableColumn<Showing, String> moviePrice = new TableColumn<>("Price");
        moviePrice.setMinWidth(100);
        moviePrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMovie().getTicketPrice())));

        table.setItems(data);
        table.getColumns().addAll(startTime, endTime, movieTitle, tickets, moviePrice);

        this.getChildren().addAll(title, table);
    }
    public void FilterData(String filterInput){
        FilteredList<Showing> filteredData = new FilteredList<>(data, s -> true);
        String filter = filterInput;
        if(filter == null || filter.length() <= 2) {
            filteredData.setPredicate(s -> true);
        }
        else {
            filteredData.setPredicate(s -> s.getMovie().getName().contains(filter));
        }
        table.setItems(filteredData);
    }

    public TableView<Showing> getTable() {
        return table;
    }
}
