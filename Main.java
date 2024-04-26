import db.Database;
import panel.PanelUsername;

public class Main {
    public static void main(String[] args) {
        Database.connectToDB();
        PanelUsername.enterUsername();
    }
}