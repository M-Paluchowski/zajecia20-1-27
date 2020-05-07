package examples.sqlinjection;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/world?serverTimezone=UTC";
        Connection connection = DriverManager.getConnection(url, "root", "root");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj nazwę miasta: ");
        String cityName = scanner.nextLine();

//        Statement statement = connection.createStatement();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM city WHERE Name = ?");
        statement.setString(1, cityName);
//        String selectSql = "SELECT * FROM city WHERE Name='" + cityName + "'";

//        ResultSet resultSet = statement.executeQuery(selectSql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString("Name");
            System.out.println(name);
        }

        scanner.close();
        statement.close();
        connection.close();
    }
}
