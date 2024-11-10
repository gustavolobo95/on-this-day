package com.lobo.onThisDay.controller;

import com.lobo.onThisDay.model.Person;
import com.lobo.onThisDay.service.BirthService;
import com.lobo.onThisDay.service.DateValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
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

    @Autowired
    BirthService birthService;

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
    @GetMapping("/getPersonsBornIn")
    public ResponseEntity<List<?>> getPersonsBornIn(@RequestParam String birthDate) {
        // TODO: aplicar validação em data e criar testes.
        try {
            if (dateValidatorService.supportsDateFormat(birthDate)) {
                MonthDay date = MonthDay.parse(birthDate);
                birthService.getPersonsBornIn(date);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return null;
    }

}
