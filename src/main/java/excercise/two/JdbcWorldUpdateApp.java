package excercise.two;

import java.sql.*;
import java.util.Scanner;

public class JdbcWorldUpdateApp {

    private static final String URL = "jdbc:mysql://localhost:3306/world?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection connection;

    public JdbcWorldUpdateApp() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Nie znaleziono klasy sterownika");
        }

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException exception) {
            System.err.println("Nie udało się nawiązać połączenia z bazą danych");
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj nazwę miasta");
        String cityName = scanner.nextLine();

        System.out.println("Podaj nową liczbę ludności");
        int populationFromUser = scanner.nextInt();

        try {
            int citiesUpdated = updatePopulationInCity(cityName, populationFromUser);
            System.out.println(String.format("Zaktualizowane rekordy: %d", citiesUpdated));

            if (citiesUpdated > 0) {
                ResultSet resultSet = fetchCityData(cityName);
                printCitiesInformation(cityName, resultSet);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try {
            connection.close();
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void printCitiesInformation(String cityName, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            long id = resultSet.getLong("ID");
            String name = resultSet.getString("Name");
            int population = resultSet.getInt("Population");
            System.out.println(String.format("ID: %d, nazwa: %s, ludność: %d", id, cityName, population));
        }
    }

    private ResultSet fetchCityData(String cityName) throws SQLException {
        PreparedStatement selectCitiesQuery = connection.prepareStatement("SELECT * FROM city WHERE Name = ?");
        selectCitiesQuery.setString(1, cityName);
        return selectCitiesQuery.executeQuery();
    }

    private int updatePopulationInCity(String cityName, int populationFromUser) throws SQLException {
        PreparedStatement updatePopulationQuery = connection.prepareStatement("UPDATE city SET Population = ? WHERE Name = ?");
        updatePopulationQuery.setInt(1, populationFromUser);
        updatePopulationQuery.setString(2, cityName);

        return updatePopulationQuery.executeUpdate();
    }
}
