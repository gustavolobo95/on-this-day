package com.lobo.onThisDay.service;

import com.lobo.onThisDay.component.MonthDayConverter;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MonthDayConverter monthDayConverter;

    public boolean supportsDateFormat(String date) {
        try {
            MonthDay dateFormatted = monthDayConverter.convert(date);
            return true;
        } catch (DateTimeParseException e) {
            throw new RuntimeException(String.format("Data informada não tem formato suportado: %s", date));
        }
    }

}
