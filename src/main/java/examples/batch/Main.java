package examples.batch;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/javastart?characterEncoding=utf8&serverTimezone=UTC&useSSL=false";
        Connection connection = DriverManager.getConnection(url, "root", "root");

        PreparedStatement statement = connection.prepareStatement("INSERT INTO person(first_name, age) VALUES (?, ?)");
        statement.setString(1, "Marcin");
        statement.setInt(2, 26);
        statement.addBatch();

        statement.setString(1, "Rafał");
        statement.setInt(2, 18);
        statement.addBatch();

        statement.executeBatch();

        Statement simpleStatement = connection.createStatement();
        simpleStatement.addBatch("INSERT INTO person(first_name, age) VALUES ('Ania', 22)");
        simpleStatement.addBatch("UPDATE person SET first_name = 'ASF' WHERE first_name = 'Marcin'");
        simpleStatement.addBatch("DELETE FROM person WHERE age = 18");
        simpleStatement.executeBatch();
    }
}
