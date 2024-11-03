package com.lobo.onThisDay.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 *
 * Controller que lidará com os requests envolvendo eventos de nascimento.
 *
 * @author Gustavo Lobo
 */
@Controller
public class BirthdayController {

    /**
     * Esse será o primeiro endpoint do projeto!
     * <p>
     * ᕕ(⌐■_■)ᕗ ♪♬
     *
     * @param birthday - data de nascimento que temporariamente deverá ser em formato "dd/MM",
     *                 outros formatos iremos validar apenas futuramente.
     * @return Lista de pessoas nascidas nessa data, (caso haja), ou uma exception caso não encontre ou caso a data esteja
     * num formato não suportado.
     */
    @GetMapping
    public ResponseEntity<List<?>> getPersonsBornIn(String birthday) {
        return null;
    }

}
