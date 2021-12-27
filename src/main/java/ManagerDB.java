import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManagerDB {
    private static Connection conn;
    private static Statement stm;

    public static void initialize() throws SQLException {
        connection();
        createTableCity();
        createTablePeople();
//        ArrayList<City> cities = Parser.parseFile("src/main/resources/happy17.csv");
//        for (City c : cities) {
//            putInfoIntoCity(c);
//            putInfoIntoPeople(c);
//        }
        getAnswers();
        disconnect();
    }

    private static void createTableCity() throws SQLException {
        stm.execute(
                "CREATE TABLE IF NOT EXISTS City (" +
                        "name TEXT PRIMARY KEY," +
                        "region TEXT," +
                        "happinessRank INTEGER," +
                        "happinessScore FLOAT," +
                        "whiskerHigh FLOAT," +
                        "whiskerLow FLOAT," +
                        "economy FLOAT," +
                        "dystopia FLOAT);"
        );
    }

    private static void createTablePeople() throws SQLException {
        stm.execute(
                "CREATE TABLE IF NOT EXISTS People (" +
                        "name TEXT," +
                        "family FLOAT," +
                        "health FLOAT," +
                        "freedom FLOAT," +
                        "generosity FLOAT," +
                        "trust FLOAT);"
        );
    }

    private static void putInfoIntoCity(City c) throws SQLException {
       stm.execute(String.format(
                "INSERT INTO City " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                c.getName(),
                c.getRegion(),
                c.getHappinessRank(),
                c.getHappinessScore(),
                c.getWhiskerHigh(),
                c.getWhiskerLow(),
                c.getEconomy(),
                c.getDystopia()
        ));
    }

    private static void putInfoIntoPeople(City c) throws SQLException {
        stm.execute(String.format(
                "INSERT INTO People " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', '%s');",
                c.getName(),
                c.getFamily(),
                c.getHealth(),
                c.getFreedom(),
                c.getGenerosity(),
                c.getTrust()
        ));
    }

    private static void connection() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database.db");
        stm = conn.createStatement();
    }

    private static void disconnect() throws SQLException {
        stm.close();
        conn.close();
    }

    private static void getAnswers() throws SQLException {
        Map<String, Double> health = firstTask();
        EventQueue.invokeLater(() -> {
            BarChart bc = new BarChart(health);
            bc.setVisible(true);
        });

        System.out.println(secondTask());
        System.out.println(thirdTask());
    }

    private static Map<String, Double> firstTask() throws SQLException {
        String query = "" +
                "SELECT City.name, P.health " +
                "FROM City " +
                "INNER JOIN People P ON City.name = P.name;";

        var queryResult = stm.executeQuery(query);
        Map<String, Double> health = new HashMap<>();
        while (queryResult.next()) {
            String city = queryResult.getString("name");
            double healthValue = Double.parseDouble(queryResult.getString("health"));
            health.put(city, healthValue);
        }

        return health;
    }

    private static String secondTask() throws SQLException {
        String query = "" +
                "SELECT region, AVG(health) AS PeopleHealth " +
                "FROM City " +
                "INNER JOIN People P ON City.name = P.name " +
                "WHERE region IN ('Western Europe', 'Sub-Saharan Africa') " +
                "GROUP BY region;";

        var resultQuery = stm.executeQuery(query);
        double westernHealth = 0;
        double africaHealth = 0;

        while(resultQuery.next()) {
            double peopleHealth = Double.parseDouble(resultQuery.getString("PeopleHealth"));
            if (resultQuery.getString("region").equals("Western Europe"))
                westernHealth = peopleHealth;
            else
                africaHealth = peopleHealth;
        }

        return String.format(
                "Средний показатель здоровья в Western Europe: %f\n" +
                "Средний показатель здоровья в Sub-Saharan Africa: %f\n",
                westernHealth, africaHealth);
    }

    private static String thirdTask() throws SQLException {
        String query = "" +
                "SELECT name, AVG(avgCity) AS averageCity\n" +
                "FROM (SELECT name, (AVG(happinessRank) + AVG(happinessScore) + " +
                "                  AVG(whiskerHigh) + AVG(whiskerLow) + " +
                "                  AVG(economy) + AVG(family) + " +
                "                  AVG(health) + AVG(freedom) + " +
                "                  AVG(generosity) + AVG(trust) + " +
                "                  AVG(dystopia)) / 11.0 AS avgCity " +
                "      FROM (SELECT * FROM City C " +
                "                     JOIN People P ON C.name = P.name " +
                "                 WHERE region IN ('Western Europe', 'Sub-Saharan Africa')) " +
                "      GROUP BY name);";

        var queryResult = stm.executeQuery(query);
        return "Самая средняя страна из всех: " + queryResult.getString("name") + "\n";
    }
}
