package com.lobo.onThisDay.controller;

import com.lobo.onThisDay.model.Person;
import com.lobo.onThisDay.service.DateValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 *
 * Controller que lidará com os requests envolvendo eventos de nascimento.
 *
 * @author Gustavo Lobo
 */
@Controller
public class BirthController {

    @Autowired
    DateValidatorService dateValidatorService;

    /**
     * Esse será o primeiro endpoint do projeto!
     * <p>
     * ᕕ(⌐■_■)ᕗ ♪♬
     *
     * @param birthDate - data de nascimento que temporariamente deverá ser em formato "dd/MM",
     *                 outros formatos iremos validar apenas futuramente.
     * @return Lista de pessoas nascidas nessa data, (caso haja), ou uma exception caso não encontre ou caso a data esteja
     * num formato não suportado.
     */
    @GetMapping
    public ResponseEntity<List<?>> getPersonsBornIn(String birthDate) {
        // TODO: aplicar validação em data e criar testes.
        if (dateValidatorService.supportsDateFormat(birthDate)) {
            Person test = new Person("Lobo",
                                 "Desenvolvedor",
                                           LocalDate.of(2024, Month.JULY, 11),
                                     null);

            List<Person> personList = List.of(test);
            return ResponseEntity.ok(personList);
        }

        return null;
    }

}
