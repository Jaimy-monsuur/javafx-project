package nl.inholland.javafx.Ui.Menus;


import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MainMenu extends MenuBar {

    private MenuItem logoutItem;
    private Menu admin;
    private MenuItem manageShowingsItem;
    private MenuItem manageMoviesItem;
    private MenuItem exportShowings;

    public MainMenu() {
        //Creating Admin menu
        admin = new Menu("Admin");
        //Creating admin menu items
        manageShowingsItem = new MenuItem("Manage showings");
        manageMoviesItem = new MenuItem("Manage movies");
        exportShowings = new MenuItem("Export Showings");
        admin.getItems().addAll(manageShowingsItem, manageMoviesItem, exportShowings);
        //Creating help menu
        Menu help = new Menu("Help");
        MenuItem item3 = new MenuItem("About");
        help.getItems().addAll(item3);
        //Creating help menu
        Menu logout = new Menu("Logout");
        logoutItem = new MenuItem("Logout");
        logout.getItems().addAll(logoutItem);
        //Adding all the menus to the menu bar
        this.getMenus().addAll(admin, help, logout);
        // openfile chooser
    }

    public MenuItem getExportShowings() {
        return exportShowings;
    }
    public MenuItem getManageMoviesItem() {
        return manageMoviesItem;
    }

    public MenuItem getManageShowingsItem() {
        return manageShowingsItem;
    }

    public Menu getAdmin() {
        return admin;
    }

    public MenuItem getLogoutItem() {
        return logoutItem;
    }
}
