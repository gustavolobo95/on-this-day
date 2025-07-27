package com.lobo.onThisDay.controller;

import com.lobo.onThisDay.dto.PersonDTO;
import com.lobo.onThisDay.service.BirthService;
import com.lobo.onThisDay.component.MonthDayConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *
 * Controller que lidará com os requests envolvendo eventos de nascimento.
 *
 * @author Gustavo Lobo
 */
@RequiredArgsConstructor
@Controller
public class BirthController {

    private final MonthDayConverter monthDayConverter;
    private final BirthService birthService;

    /**
     * Esse será o primeiro endpoint do projeto!
     * <p>
     * ᕕ(⌐■_■)ᕗ ♪♬
     *
     * @param birthDate - data de nascimento que temporariamente deverá ser em formato "dd/MM",
     *                  outros formatos iremos validar apenas futuramente.
     * @return Lista de pessoas nascidas nessa data, (caso haja), ou uma exception caso não encontre ou caso a data esteja
     * num formato não suportado.
     */
    @GetMapping("/getPersonsBornIn")
    public ResponseEntity<List<PersonDTO>> getPersonsBornIn(@RequestParam String birthDate) {
        return monthDayConverter.convertDate(birthDate)
                .map(birth ->
                        ResponseEntity.ok(birthService.getPersonsBornIn(birth))
                ).orElse(
                        ResponseEntity.notFound().build()
                );
    }

}
