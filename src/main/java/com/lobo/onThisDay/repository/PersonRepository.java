package com.lobo.onThisDay.repository;

import com.lobo.onThisDay.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Gustavo Lobo
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value = "SELECT * FROM person WHERE EXTRACT(MONTH FROM birth) = :month AND EXTRACT(DAY FROM birth) = :day", nativeQuery = true)
    List<Person> findByBirthMonthAndDay(@Param("month") int month, @Param("day") int day);


}
