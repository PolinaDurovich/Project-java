import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Parser {
    public static ArrayList<City> parseFile(String path) {
        ArrayList<City> cities = new ArrayList<>();
        try (BufferedReader bufReader = Files.newBufferedReader(Paths.get(path))) {
            bufReader.readLine();
            while (bufReader.ready()) {
                String[] cityInfo = bufReader.readLine().split(",");
                cities.add(new City(cityInfo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }
}
