package nl.inholland.javafx.Ui.Windows;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import nl.inholland.javafx.Data.Database;
import nl.inholland.javafx.Models.Showing;
import nl.inholland.javafx.Models.User;
import nl.inholland.javafx.Models.UserType;
import nl.inholland.javafx.Ui.Forms.SellTicketForm;
import nl.inholland.javafx.Ui.Menus.MainMenu;
import nl.inholland.javafx.Ui.StyledScene;
import nl.inholland.javafx.Ui.Views.Room1TableView;
import nl.inholland.javafx.Ui.Views.Room2TableView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class MainScene {
    private Stage stage;
    private Database db;
    private Room1TableView tableViewRoom1;
    private Room2TableView tableViewRoom2;
    private SellTicketForm form;

    public MainScene(Database db, User user) {
        //make db
        this.db = db;
        // new Stage
        stage = new Stage();
        //layout of stage
        VBox layout = new VBox();
        //Title
        Label title = new Label();
        title.setPadding(new Insets(10, 10, 10, 10));
        title.setText("Purchase ticket");
        title.setFont(new Font("Arial Bold", 22));
        // menu
        MainMenu mainMenu = new MainMenu();
        if (user.getType() == UserType.User)//hide admin menu is user is not admin
        {
            mainMenu.getAdmin().setVisible(false);
        }
        //open manage showings
        mainMenu.getManageShowingsItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ManageShowingsScene manageShowingsScene = new ManageShowingsScene(getDb(), user);
                manageShowingsScene.getStage().show();
                getStage().close();
            }
        });
        //open manage movies
        mainMenu.getManageMoviesItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ManageMoviesScene manageMoviesScene = new ManageMoviesScene(getDb(), user);
                manageMoviesScene.getStage().show();
                getStage().close();
            }
        });
        //logout
        mainMenu.getLogoutItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LogIn logIn = new LogIn(getDb());
                logIn.getStage().show();
                getStage().close();
            }
        });
        //export Showings
        mainMenu.getExportShowings().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                File file = null;
                try {
                    file = writeCsv(db);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Showings");
                chooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("*.scv", "*.csv")
                );
                file = chooser.showSaveDialog(stage);
                chooser.setInitialFileName("Showings.csv");



            }
        });
        //make rooms and room layout
        tableViewRoom1 = new Room1TableView(db);
        tableViewRoom2 = new Room2TableView(db);
        HBox roomLayout = new HBox();
        roomLayout.getStyleClass().add("border");
        roomLayout.getChildren().addAll(tableViewRoom1, tableViewRoom2);
        //filter for rooms
        HBox filter = new HBox();
        TextField filterBox = new TextField();
        filter.setMaxWidth(500);
        filterBox.setMinWidth(500);
        filter.getStyleClass().add("border");
        filter.getChildren().add(filterBox);
        filterBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                tableViewRoom1.FilterData(t1);
                tableViewRoom2.FilterData(t1);
                tableViewRoom1.getTable().refresh();
                tableViewRoom2.getTable().refresh();
            }
        });
        // SellTicketForm
        form = new SellTicketForm();
        form.setVisible(false);
        form.getStyleClass().add("border");
        //events
        //open form
        tableViewRoom1.getTable().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //stuff
                if (tableViewRoom1.getTable().getSelectionModel().getSelectedItem() != null) {
                    form.setVisible(true);
                    Showing s = tableViewRoom1.getTable().getSelectionModel().getSelectedItem();
                    form.SetData(s, "Room 1");
                }
            }
        });
        tableViewRoom2.getTable().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //stuff
                if (tableViewRoom2.getTable().getSelectionModel().getSelectedItem() != null) {
                    form.setVisible(true);
                    Showing s = tableViewRoom2.getTable().getSelectionModel().getSelectedItem();
                    form.SetData(s, "Room 2");
                }
            }
        });
        //form events
        // purchase... niet zo mooi
        form.getPurchaseBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //stuff
                if (form.getNoTickets().getValue() != "" && !Objects.equals(form.getBuyerName().getText(), "")) {
                    SelTicket();
                } else {
                    form.getErrorLabel().setText("Fill in all fields");
                    form.getErrorLabel().setTextFill(Color.rgb(255, 0, 0));
                }
            }
        });
        //cancel/clear
        form.getClearBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //stuff
                form.setVisible(false);
                form.clear();
            }
        });
        // Create styled scene
        Scene mainScene = new StyledScene(layout);
        // set layout
        layout.getChildren().addAll(mainMenu, title,filter ,roomLayout, form);
        // complete stage
        stage.setTitle("Fantastic cinema - Purchase Ticket");
        stage.setScene(mainScene);
    }

    public Database getDb() {
        return db;
    }

    public Stage getStage() {
        return stage;
    }

    private void SelTicket() {
        //allert with style
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(alert.getDialogPane().getScene());
        alert.setTitle("Confirm payment");
        alert.setHeaderText("Confirm this payment?");
        form.getErrorLabel().setText("");
        //get room data
        String room = form.getRoomData().getText();
        int nrTickets = (int) form.getNoTickets().getValue();
        Optional<ButtonType> result = alert.showAndWait();
        //falidaet purchase
        if (room.equals("Room 1") && result.get() == ButtonType.OK) {
            for (Showing s : db.getRoom1().getShowings()) {
                if (s.equals(form.getSelectedShowing())) {
                    if (s.getNumberOfTickets() - nrTickets >= 0) {
                        s.setNumberOfTickets(s.getNumberOfTickets() - nrTickets);
                        tableViewRoom1.getTable().refresh();
                        form.setVisible(false);
                        form.clear();
                    } else {
                        //not valid
                        form.getErrorLabel().setText("Not enough seats available");
                        form.getErrorLabel().setTextFill(Color.rgb(255, 0, 0));
                    }
                }
            }
        } else if (room.equals("Room 2") && result.get() == ButtonType.OK) {//validate purchase room2
            for (Showing s : db.getRoom2().getShowings()) {
                if (s.equals(form.getSelectedShowing())) {
                    if (s.getNumberOfTickets() - nrTickets >= 0) {
                        s.setNumberOfTickets(s.getNumberOfTickets() - nrTickets);
                        tableViewRoom2.getTable().refresh();
                        form.setVisible(false);
                        form.clear();
                    } else {
                        //not valid
                        form.getErrorLabel().setText("Not enough seats available");
                        form.getErrorLabel().setTextFill(Color.rgb(255, 0, 0));
                    }
                }
            }
        } else {
            //if purchase is canceled
            form.getErrorLabel().setText("Purchase canceled");
            form.getErrorLabel().setTextFill(Color.rgb(255, 0, 0));
        }
    }
    public File writeCsv(Database db) throws Exception {
        System.out.println("test");
        BufferedWriter writer = null;
        File file = null;
        try {
            file = new File(String.valueOf(new File(System.getProperty("java.io.tmpdir"), "filename.tmp")));
            writer = new BufferedWriter(new FileWriter(file));
            for (Showing s : db.getRoom1().getShowings()) {
                String text = s.getBeginTime() + "," + s.getEndTime() + "," + "Room 1" + "," + s.getMovie().getName() + "," + s.getNumberOfTickets()+ "," + s.getMovie().getTicketPrice() + "\n";
                writer.write(text);
            }
            for (Showing s : db.getRoom2().getShowings()) {
                String text = s.getBeginTime() + "," + s.getEndTime() + "," + "Room 1" + "," + s.getMovie().getName() + "," + s.getNumberOfTickets()+ "," + s.getMovie().getTicketPrice() + "\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            writer.close();
        }
        return file;
    }
}
