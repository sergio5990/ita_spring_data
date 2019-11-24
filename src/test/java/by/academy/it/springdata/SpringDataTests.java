package by.academy.it.springdata;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import by.academy.it.springdata.config.HibernateConfig;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.academy.it.springdata.entities.Department;
import by.academy.it.springdata.entities.Employee;
import by.academy.it.springdata.repository.DepartmentRepository;
import by.academy.it.springdata.repository.EmployeePagingRepository;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateConfig.class)
public class SpringDataTests {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeePagingRepository employeeRepository;

    @BeforeEach
    public void init() {
        Department qa = new Department(null, "QA", null);
        Department ba = new Department(null, "BA", null);
        Department hr = new Department(null, "HR", null);
        Department devOps = new Department(null, "DevOps", null);
        Department pm = new Department(null, "PM", null);
        Department al = new Department(null, "AL", null);
        Department buch = new Department(null, "Бухгалтерия", null);
        Department canc = new Department(null, "Канцелярия", null);
        departmentRepository.save(qa);
        departmentRepository.save(canc);
        departmentRepository.save(ba);
        departmentRepository.save(hr);
        departmentRepository.save(devOps);
        departmentRepository.save(pm);
        departmentRepository.save(al);
        departmentRepository.save(buch);

        employeeRepository.save(new Employee(null, "Mohammad", "Salek", 25, qa));
        employeeRepository.save(new Employee(null, "Lilia", "Worjec", 31, qa));
        employeeRepository.save(new Employee(null, "Yana", "Kozhedub", 29, hr));
        employeeRepository.save(new Employee(null, "Yuliya", "Samohina", 24, hr));
        employeeRepository.save(new Employee(null, "Gena", "RGB", 28, devOps));
        employeeRepository.save(new Employee(null, "Sasha", "Shi", 45, pm));
        employeeRepository.save(new Employee(null, "Alex", "Trump", 45, al));
        employeeRepository.save(new Employee(null, "Maria", "Shklyar", 37, buch));
        employeeRepository.save(new Employee(null, "Sasha", "Rabushka", 30, ba));
        employeeRepository.save(new Employee(null, "Sasha", "Tverdohlebova", 29, ba));
    }

    @Test
    public void queryTest() {
        departmentRepository.getByJoinCondition("Sasha").forEach(System.out::println);
    }

    @Test
    public void queryParamTest() {
        departmentRepository.getByEmployeesLastName("Shi").forEach(System.out::println);
    }

    @Test
    public void nativeQueryTest() {
        departmentRepository.getByJoinConditionNative("Yana").forEach(System.out::println);
    }

    @Test
    public void maxAgeTest() {
        departmentRepository.getByMaxAge().forEach(System.out::println);
    }

    @Test
    void name() {
        final Department deparment = departmentRepository.getOne(1L);
        assertEquals(deparment.getName() , "QA");

        departmentRepository.updateName(1L, "QA1");

        final Department deparmentAfterUpdate = departmentRepository.getOne(1L);
        assertEquals(deparmentAfterUpdate.getName(), "QA1");
    }

    @Test
    public void pageableTest() {
        List<Long> ids = Stream.of(2L, 3L, 4L).collect(Collectors.toList());
        Page<Employee> employeesPage = employeeRepository
                .findByDepartmentIdIn(ids, PageRequest.of(1, 3, Sort.Direction.DESC, "age"));
        employeesPage.getContent().forEach(System.out::println);
        employeesPage = employeeRepository
                .findByFirstName("Sasha", PageRequest.of(0, 2, Sort.Direction.DESC, "age"));
        employeesPage.getContent().forEach(System.out::println);
        employeesPage = employeeRepository
                .findAll(PageRequest.of(0, 2, Sort.Direction.DESC, "age"));
        employeesPage.getContent().forEach(System.out::println);
        employeesPage = employeeRepository
                .findByDepartmentId(2L, PageRequest.of(0, 2, Sort.Direction.DESC, "age"));
        employeesPage.getContent().forEach(System.out::println);

        employeeRepository.findAll( Sort.by(Sort.Direction.DESC, "department.name")).forEach(System.out::println);
    }

    @Test
    public void findByExampleTest() {
        System.out.println(departmentRepository.findOne(Example.of(new Department(null, "Бухгалтерия", null))));
        List<Department> departments = departmentRepository.findAll(
                Example.of(new Department(null, "рия", null), ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.ENDING)));
        departments.forEach(System.out::print);
    }
}
