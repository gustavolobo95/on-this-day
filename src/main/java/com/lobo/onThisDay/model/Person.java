package com.lobo.onThisDay.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Gustavo Lobo
 */
@Entity
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String source;
    private String livingPeriodOrAge;
    private LocalDate birth;
    private LocalDate death;
    private Integer age;

    public Person(String name, String description, String source, String livingPeriodOrAge, LocalDate birth, LocalDate death, Integer age) {
        this.name = name;
        this.description = description;
        this.source = source;
        this.livingPeriodOrAge = livingPeriodOrAge;
        this.birth = birth;
        this.death = death;
        this.age = age;
    }

    public Person() {};

}
