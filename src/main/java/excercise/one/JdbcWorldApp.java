package excercise.one;

import java.sql.*;
import java.util.Scanner;

public class JdbcWorldApp {

    private Connection connection;
    private Scanner scanner;

    public void run() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Sterownik nie odnaleziony");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/world?serverTimezone=UTC";

        try {
            connection = DriverManager.getConnection(url, "root", "root");
        } catch (SQLException e) {
            System.err.println("Nie udało się nawiązać połączenia");
            return;
        }

        while(true) {
            System.out.println("Co chcesz zrobić?");
            System.out.println("0. Wyjście");
            System.out.println("1. Wyświetl miasta Polskie");
            System.out.println("2. Wyświetl miasta o podanym kodzie");
            System.out.println("3. Wyświetl statystyki języka");

            scanner = new Scanner(System.in);
            String operation = scanner.nextLine();

            switch (operation) {
                case "1":
                    printCitiesForPoland();
                    break;
                case "2":
                    printCitiesForGivenCountryCode();
                    break;
                case "3":
                    printLanguageInfo();
                    break;
                case "0":
                    close();
                    return;
                default:
                    System.out.println("Zła operacja");
            }

            scanner.close();
        }
    }

    private void printLanguageInfo() {
        System.out.println("Podaj język: ");
        String language = scanner.nextLine();

        String sql = "SELECT Name, IsOfficial, Percentage FROM countrylanguage lang JOIN country c ON c.Code = lang.CountryCode WHERE Language = '"
                + language + "'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String isOfficial = resultSet.getString("IsOfficial");
                double percentage = resultSet.getDouble("Percentage");

                System.out.println(String.format("Name: %s, is official language: %s, percentage: %f.2", name, isOfficial, percentage));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            connection.close();
            scanner.close();
        } catch (SQLException e) {
            System.err.println("Bład podczas zamykania połączenia");
        }
    }

    private void printCitiesForGivenCountryCode() {
        System.out.println("Podaj kod kraju");
        String countryCode = scanner.nextLine();

        printCitiesForCountryCode(countryCode);
    }

    private void printCitiesForPoland() {
        printCitiesForCountryCode("POL");
    }

    private void printCitiesForCountryCode(String countryCode) {
        String sql = "SELECT * FROM city WHERE CountryCode ='" + countryCode + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                System.out.println(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
