package com.lobo.onThisDay.service;

import org.springframework.stereotype.Service;

import java.time.MonthDay;
import java.time.format.DateTimeParseException;

/**
 *
 * Classe que fará validação de datas suportadas pela API
 *
 * @author Gustavo Lobo
 */
@Service
public class DateValidatorService {

    public boolean supportsDateFormat(String date) {
        try {
            // TODO: Verificar um objeto que possa representar data no formato de "dd/MM", essa apesar de cumprir o
            //  proposito precisa que a data venha no formato "--MM-dd".

            MonthDay dateFormatted = MonthDay.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Data informada não tem formato suportado: ", date, 0);
        }
    }

}
