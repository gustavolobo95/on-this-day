package com.lobo.onThisDay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * DTO para representar pessoas, de inicio esse DTO terá muita influecia da feature de consulta de nascimentos.
 *
 * @author Gustavo Lobo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    String name;
    String description;
    String livingPeriodOrAge;

}
