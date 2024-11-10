package com.lobo.onThisDay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * Model para representar pessoas, de inicio esse model terá muita influecia da feature de consulta de nascimentos.
 *
 * @author Gustavo Lobo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    String name;
    String description;
    String livingPeriodOrAge;

}
