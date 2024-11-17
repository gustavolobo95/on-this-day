package com.lobo.onThisDay.controller;

import com.lobo.onThisDay.service.BirthService;
import com.lobo.onThisDay.service.DateValidatorService;
import com.lobo.onThisDay.service.MonthDayConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.MonthDay;
import java.util.Collections;
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
    MonthDayConverter monthDayConverter;

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
        try {
            if (dateValidatorService.supportsDateFormat(birthDate)) {
                MonthDay date = monthDayConverter.convert(birthDate);
                return ResponseEntity.ok(birthService.getPersonsBornIn(date));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonList(e.getMessage()));
        }
        return null;
    }

}
