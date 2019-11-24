package by.academy.it.springdata;

import by.academy.it.springdata.config.HibernateConfig;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.academy.it.springdata.entities.Cat;
import by.academy.it.springdata.repository.CatCrudRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateConfig.class)
//@ContextConfiguration("test-data.xml")
public class CatCrudTest {
    @Autowired
    CatCrudRepository catRepository;

    @BeforeEach
    public void init() {
        catRepository.save(new Cat(null, "Matis", 11));
        catRepository.save(new Cat(null, "Proshka", 3));
        catRepository.save(new Cat(null, "Tomas", 4));
        catRepository.save(new Cat(null, "Geek", 1));
        catRepository.save(new Cat(null, "Grom", 2));
        catRepository.save(new Cat(null, "Basya", 7));
        catRepository.save(new Cat(null, "Masya", 2));
    }

    @Test
    public void queryMethodTest() {
        catRepository.findByName("Matis").forEach(System.out::println);
        catRepository.findByAgeBetweenAndNameEndingWith(3, 8, "a").forEach(System.out::println);
        catRepository.findByOrderByNameDesc().forEach(System.out::println);
    }

    @Test
	public void crudRepositoryTest() {
        System.out.println(catRepository.existsById(1L));
        Cat cat = catRepository.findById(1L).orElse(null);
        System.out.println(cat);
        cat.setAge(12);
        catRepository.save(cat);
        System.out.println(cat);
        Cat boris = catRepository.save(new Cat(null, "Boris", 3));
        catRepository.delete(boris);
        catRepository.findByName("Matis").forEach(System.out::println);
    }
}
