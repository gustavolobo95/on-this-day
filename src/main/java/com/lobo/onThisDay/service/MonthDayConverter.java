package com.lobo.onThisDay.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

/**
 *
 * Conversor de datas para os requests de MonthDay, irá utilizar padrão dd/MM.
 *
 * @author Gustavo Lobo
 */
@Component
public class MonthDayConverter implements Converter<String, MonthDay> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

    @Override
    public MonthDay convert(String source) {
        return MonthDay.parse(source, formatter);
    }

}
