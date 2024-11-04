package com.lobo.onThisDay.service;

import org.springframework.stereotype.Service;

import java.time.YearMonth;
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
            YearMonth dateFormatted = YearMonth.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Data informada não tem formato suportado: ", date, 0);
        }
    }

}
