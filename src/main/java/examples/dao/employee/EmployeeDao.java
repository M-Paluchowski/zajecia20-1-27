package examples.dao.employee;

import java.sql.*;
import java.util.Optional;

public class EmployeeDao {
    private static final String URL = "jdbc:mysql://localhost:3306/javastart?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Connection connection;

    public EmployeeDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException exception) {
            System.err.println("Nie znaleziono sterownika");
        } catch (SQLException exception) {
            System.err.println("Nie można nawiązać połączenia");
        }
    }

    //CRUD C-reate, R-ead, U-pdate, D-elete

    //C
    public void save(Employee employee) {
        String insertEmployeeSql = "INSERT INTO employee(firstName, lastName, salary) VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(insertEmployeeSql);
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setDouble(3, employee.getSalary());
            statement.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("Nie udało się zapisać rekordu");
            exception.printStackTrace();
        }
    }

    //R
    public Optional<Employee> read(long id) {
        String selectEmployeeSql = "SELECT * FROM employee WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(selectEmployeeSql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("firstName"));
                employee.setLastName(resultSet.getString("lastName"));
                employee.setSalary(resultSet.getDouble("salary"));
                return Optional.of(employee);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    //U
    public void update(Employee employee) {
        String sql = "UPDATE employee SET firstName = ?, lastName = ?, salary = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setDouble(3, employee.getSalary());
            statement.setLong(4, employee.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    //D
    public void delete(long id) {
        String sql = "DELETE FROM employee WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException exception) {
            System.err.println("Nie udało się zamknąć połączenia");
        }
    }
}
