package by.academy.it.springdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.academy.it.springdata.entities.Cat;

public interface CatJpaRepository extends JpaRepository<Cat, Long> {
}
