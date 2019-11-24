package by.academy.it.springdata.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import by.academy.it.springdata.entities.Cat;

public interface CatCrudRepository extends CrudRepository<Cat, Long> {
    List<Cat> findByName(String name);

    List<Cat> findByAgeBetweenAndNameEndingWith(int arg1, int arg2, String nameEndWith);

    List<Cat> findByOrderByNameDesc();
}
