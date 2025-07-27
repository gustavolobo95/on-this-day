package com.lobo.onThisDay.service;

import com.lobo.onThisDay.dto.PersonDTO;
import com.lobo.onThisDay.model.Person;
import com.lobo.onThisDay.repository.PersonRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Gustavo Lobo
 */
@Service
public class PersonService {

    /**
     *
     * Pattern utilizado para capturar um numero de 1 à 4 digitos que representaria o ano de nascimento.
     * <p>
     * Ex:
     * <li>(1995-</li>
     * <li>(150-</li>
     * <li>(2005-</li>
     *
    */
    public static final String BIRTH_YEAR_PERIOD_PATTERN = "^\\(\\s*(\\d{1,4})\\s*-";

    /**
     * Pattern utilizado para verificar se uma string representa um periodo (anual) de tempo separado por hifen.
     * <p>
     * Ex:
     * <li>(150-175)</li>
     * <li>(1995-2025)</li>
     * <li>(1945-2008)</li>
     */
    public static final String PERIODIC_DATE_PATTERN = "^\\(\\s*\\d{1,4}\\s*-\\s*\\d{1,4}\\s*\\)$";

    @Autowired
    private PersonRepository personRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    public List<PersonDTO> getHighlightPersonsBornIn(MonthDay date) {
        List<Person> persons = personRepository.findByBirthMonthAndDay(date.getDayOfMonth(), date.getMonthValue());

        if(persons == null || persons.isEmpty())
            return null;

        return parseModelToDTO(persons);
    }

    private List<PersonDTO> parseModelToDTO(List<Person> persons) {
        return persons.stream()
                .map(p -> new PersonDTO(p.getName(),
                        p.getDescription(),
                        p.getLivingPeriodOrAge())
                ).collect(Collectors.toList());
    }

    private LocalDate formatBirthDateFromPersonDTO(PersonDTO dto, MonthDay date) {
        if(StringUtils.isBlank(dto.getLivingPeriodOrAge()))
            return null;

        String livingPeriodOrAge = dto.getLivingPeriodOrAge();
        Optional<Integer> year;
        LocalDate birth;

        if(isPeriodic(livingPeriodOrAge)) {
            year = extractBirthYear(livingPeriodOrAge);
            birth = year.map(integer -> LocalDate.of(integer, date.getMonth(), date.getDayOfMonth())).orElseThrow(() -> new RuntimeException("Erro ao extrair ano de nascimento do periodo: " + livingPeriodOrAge));
            return birth;
        }

        int actualYear = LocalDate.now().getYear();
        int age = extractAge(livingPeriodOrAge);
        birth = LocalDate.of(actualYear - age, date.getMonth(), date.getDayOfMonth());
        return birth;
    }

    public Integer extractAge(String input) {

        if (StringUtils.isBlank(input))
            return null;

        // Caso 1: formato de intervalo de anos, ex: "1899-1985"
        if (input.matches(PERIODIC_DATE_PATTERN)) {

            // Removendo parenteses do periodo.
            input = input.replace("(", "");
            input = input.replace(")", "");

            String[] years = input.split("-");
            try {
                int birthYear = Integer.parseInt(years[0]);
                int deathYear = Integer.parseInt(years[1]);
                return deathYear - birthYear;
            } catch (NumberFormatException e) {
                return null;
            }
        }

        // Caso 2: formato com idade direta, ex: "65 years old"
        if (input.matches("\\d+\\s+years\\s+old")) {
            try {
                return Integer.parseInt(input.split("\\s+")[0]);
            } catch (NumberFormatException e) {
                LOGGER.error("Erro ao fazer o parse da idade: {}", input);
                return null;
            }
        }

        // Caso não reconhecido
        LOGGER.debug("Padrão de idade não reconhecido: {}", input);
        return null;
    }

    private Optional<Integer> extractBirthYear(String livingPeriodOrAge) {
        Pattern pattern = Pattern.compile(BIRTH_YEAR_PERIOD_PATTERN);
        Matcher matcher = pattern.matcher(livingPeriodOrAge);

        if (matcher.find()) {
            return Optional.of(Integer.parseInt(matcher.group(1)));
        }
        return Optional.empty();
    }

    private boolean isPeriodic(String livingPeriodOrAge) {
        return livingPeriodOrAge.matches(PERIODIC_DATE_PATTERN);
    }

    private List<Person> parseDTOtoModel(List<PersonDTO> dtoList, MonthDay date, String source) {

        return dtoList.stream()
                .map(dto -> {
                        LocalDate birth = formatBirthDateFromPersonDTO(dto, date);

                        Person person  = new Person(
                                dto.getName(),
                                dto.getDescription(),
                                source,
                                dto.getLivingPeriodOrAge(),
                                birth,
                                null, // Ajustar posteriormente com logica para definir data da morte.
                                extractAge(dto.getLivingPeriodOrAge())
                        );

                        return person;
                    }
                ).collect(Collectors.toList());
    }


    public void savePersons(List<PersonDTO> returnedPersonDTOS, MonthDay date, String source) {

        if(returnedPersonDTOS == null || returnedPersonDTOS.isEmpty()) {
            LOGGER.info("Não há dados para persistir para a data: {}", date);
            return;
        }

        List<Person> persons = parseDTOtoModel(returnedPersonDTOS, date, source);

        personRepository.saveAll(persons);
    }
}
