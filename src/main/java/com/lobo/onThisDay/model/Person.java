package com.lobo.onThisDay.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

/**
 * @author Gustavo Lobo
 */
@Entity
public class Person {

    @Id
    private Long id;

    private String name;
    private String description;
    private LocalDate birth;
    private LocalDate death;
    private Long age;

}
