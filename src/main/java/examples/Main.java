package examples;

import java.sql.*;

class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/world?characterEncoding=utf8&serverTimezone=UTC&useSSL=false";
//        String url = "jdbc:mysql://localhost:3306/javastart?characterEncoding=utf8&serverTimezone=UTC&useSSL=false";
        Connection connection = DriverManager.getConnection(url, "root", "root");
//        Statement statement = connection.createStatement();
//        String query = "SELECT * FROM pracownicy";
//        ResultSet resultSet = statement.executeQuery(query);
//
//        while (resultSet.next()) {
//            int id = resultSet.getInt(1);
//            String imie = resultSet.getString("imie");
//            String nazwisko = resultSet.getString(3);
//            int wiek = resultSet.getInt("wiek");
//            System.out.println(String.format("%d - %s %s %d", id, imie, nazwisko, wiek));
//        }
//        statement.close();

        Statement statementTwo = connection.createStatement();
        String insertSql = "INSERT INTO city (Name, CountryCode, District, Population) VALUES ('Psinka Dolna', 'POL', 'Dolny Slask', 30000)";
        int modified = statementTwo.executeUpdate(insertSql);

        statementTwo.close();
        connection.close();
    }
}
