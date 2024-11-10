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
            MonthDay dateFormatted = MonthDay.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            throw new RuntimeException(String.format("Data informada não tem formato suportado: %s", date));
        }
    }

}
