package nl.inholland.javafx.Ui.Forms;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import nl.inholland.javafx.Models.Showing;

public class SellTicketForm extends GridPane {

    private Label roomData;
    private Label startData;
    private Label endData;
    private Label nameData;
    private ComboBox noTickets;
    private TextField buyerName;
    private Button purchaseBtn;
    private Button clearBtn;
    private Label errorLabel;
    private Showing selectedShowing;

    public SellTicketForm() {
        //making all labels and stuff
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setVgap(20);
        this.setHgap(30);
        SetTextLabels();
        SetData();
    }

    public Label getRoomData() {
        return roomData;
    }

    public Showing getSelectedShowing() {
        return selectedShowing;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public ComboBox getNoTickets() {
        return noTickets;
    }

    public TextField getBuyerName() {
        return buyerName;
    }

    public Button getPurchaseBtn() {
        return purchaseBtn;
    }

    public Button getClearBtn() {
        return clearBtn;
    }

    private void SetData() {
        //labels with data
        roomData = new Label();
        this.add(roomData, 1, 0);
        startData = new Label();
        this.add(startData, 1, 1);
        endData = new Label();
        this.add(endData, 1, 2);
        //row 4
        nameData = new Label();
        this.add(nameData, 4, 0);
        noTickets = new ComboBox();
        noTickets.setVisibleRowCount(5);
        this.add(noTickets, 4, 1);
        buyerName = new TextField();
        this.add(buyerName, 4, 2);
        purchaseBtn = new Button("Purchase");
        this.add(purchaseBtn, 5, 1);
        clearBtn = new Button("Clear");
        this.add(clearBtn, 5, 2);
        noTickets.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        errorLabel = new Label();
        this.add(errorLabel, 0, 3, 5, 3);
    }

    private void SetTextLabels() {
        //text labels
        Label room = new Label("Room:");
        room.getStyleClass().add("bold");
        this.add(room, 0, 0);
        Label start = new Label("Start:");
        start.getStyleClass().add("bold");
        this.add(start, 0, 1);
        Label end = new Label("End:");
        end.getStyleClass().add("bold");
        this.add(end, 0, 2);
        //row 2
        Label movieName = new Label("Movie title:");
        movieName.getStyleClass().add("bold");
        this.add(movieName, 2, 0);
        Label noSeatsTxt = new Label("No. seats:");
        noSeatsTxt.getStyleClass().add("bold");
        this.add(noSeatsTxt, 2, 1);
        Label name = new Label("name:");
        name.getStyleClass().add("bold");
        this.add(name, 2, 2);
    }

    public void clear() {
        this.noTickets.getSelectionModel().clearSelection();
        this.buyerName.clear();
    }

    public void SetData(Showing s, String roomName) {
        this.selectedShowing = s;
        //fill with showing data
        roomData.setText(roomName);
        startData.setText(s.getBeginTime().toLocalDate().toString() + " " + s.getBeginTime().getHour() + ":" + s.getBeginTime().getMinute());
        endData.setText(s.getEndTime().toLocalDate().toString() + " " + s.getEndTime().getHour() + ":" + s.getEndTime().getMinute());
        nameData.setText(s.getMovie().getName());
    }
}
