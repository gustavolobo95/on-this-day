package com.lobo.onThisDay.component;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 *
 * Conversor de datas para os requests de MonthDay, irá utilizar padrão dd/MM.
 *
 * @author Gustavo Lobo
 */
@Component
public class MonthDayConverter implements Converter<String, MonthDay> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

    public Optional<MonthDay> convertDate(String source) {
        return StringUtils.isNotBlank(source) ? Optional.of(convert(source)) : Optional.empty();
    }

    @Override
    public MonthDay convert(String source) {
        return MonthDay.parse(source, formatter);
    }

}
