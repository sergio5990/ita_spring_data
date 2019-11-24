package by.academy.it.springdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.academy.it.springdata.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
