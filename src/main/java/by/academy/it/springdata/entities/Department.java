package by.academy.it.springdata.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"employees"})
@Entity
public class Department {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "department",
            cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();
}
