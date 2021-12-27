import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            ManagerDB.initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
