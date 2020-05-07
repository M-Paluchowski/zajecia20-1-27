package examples.dao;

import examples.dao.employee.Employee;
import examples.dao.employee.EmployeeDao;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        EmployeeDao employeeDao = new EmployeeDao();
        Employee employeeToSave = new Employee("Jan", "Kowalski", 30);

//        employeeDao.save(employeeToSave);

//        employeeDao.read(1L)
//            .ifPresent(System.out::println);

//        Employee employee = new Employee("Janusz", "Kowalski", 100);
//        employee.setId(1L);
//        employeeDao.update(employee);


//        employeeDao.read(1L)
//                .ifPresent(System.out::println);

        employeeDao.delete(1L);

        employeeDao.read(1L)
                .ifPresent(System.out::println);

        employeeDao.close();
    }
}
