import ui.MainMenu;

/**
 * Entry point for the Hotel Application. Responsible for initializing the main menu UI.
 */
final public class HotelApplication {
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.getMenu();
    }
}
