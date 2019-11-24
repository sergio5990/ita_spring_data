package by.academy.it.springdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import by.academy.it.springdata.entities.Employee;

public interface EmployeePagingRepository extends PagingAndSortingRepository<Employee, Long> {
    Page<Employee> findByDepartmentIdIn(List<Long> ids, Pageable pageable);

    Page<Employee> findByDepartmentId(Long id, Pageable pageable);

    Page<Employee> findByFirstName(String name, Pageable pageable);
}
