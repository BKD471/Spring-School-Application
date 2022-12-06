package com.example.school.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.example.school.model.Person;
import java.util.Set;

@Data
@Entity
@Table(name="class")
public class PhoenixClass extends BaseEntity{
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
        @GenericGenerator(name = "native",strategy = "native")
        private int classId;

        @NotBlank(message="Name must not be blank")
        @Size(min=3, message="Name must be at least 3 characters long")
        private String name;

        @OneToMany(mappedBy = "phoenixClass", fetch = FetchType.LAZY,
                cascade = CascadeType.PERSIST,targetEntity = Person.class)
        private Set<Person> persons;

}


