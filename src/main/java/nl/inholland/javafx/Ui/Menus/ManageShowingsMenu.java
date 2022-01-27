package nl.inholland.javafx.Ui.Menus;


import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class ManageShowingsMenu extends MenuBar {

    private MenuItem logoutItem;
    private Menu admin;
    private MenuItem purchaseTicketItem;
    private MenuItem manageMoviesItem;
    private MenuItem exportShowings;

    public ManageShowingsMenu() {
        //Creating Admin menu
        admin = new Menu("Admin");
        //Creating admin menu items
        purchaseTicketItem = new MenuItem("Purchase tickets");
        manageMoviesItem = new MenuItem("Manage movies");
        exportShowings = new MenuItem("Export Showings");
        admin.getItems().addAll(purchaseTicketItem, manageMoviesItem, exportShowings);
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
    }

    public MenuItem getExportShowings() {
        return exportShowings;
    }

    public MenuItem getManageMoviesItem() {
        return manageMoviesItem;
    }

    public MenuItem getPurchaseTicketItem() {
        return purchaseTicketItem;
    }

    public Menu getAdmin() {
        return admin;
    }

    public MenuItem getLogoutItem() {
        return logoutItem;
    }
}
