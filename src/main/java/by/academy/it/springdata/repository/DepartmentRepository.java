package by.academy.it.springdata.repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import by.academy.it.springdata.entities.Department;
import org.springframework.transaction.annotation.Transactional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("select distinct(d) from Department d join d.employees e where e.firstName = ?1")
    List<Department> getByJoinCondition(String name);

    @Query("select d from Department d join d.employees e where e.age = (select max(e.age) from Employee e)")
    List<Department> getByMaxAge();

    //    @Query(value = "select d.* from Department d join Employee e on e.department_id = d.id where e.lastName = :lastName", nativeQuery = true)
    @Query("select d from Department d join d.employees e where e.lastName = :lastName")
    List<Department> getByEmployeesLastName(@Param("lastName") String familyName);

    @Query(value = " select d.* from Department d" +
            " left join Employee e on d.id = e.department_id" +
            " where e.firstName = ?1", nativeQuery = true)
    List<Department> getByJoinConditionNative(String name);

    @Modifying
    @Transactional
    @Query(value = "update Department set name = :name where id = :id")
    void updateName(@Param("id") Long id, @Param("name") String name);

}
