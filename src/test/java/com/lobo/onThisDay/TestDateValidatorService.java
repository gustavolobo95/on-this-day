package com.lobo.onThisDay;

import com.lobo.onThisDay.service.DateValidatorService;
import com.lobo.onThisDay.service.MonthDayConverter;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

/**
 *
 * Classe de teste para validação dos inputs de data.
 *
 * @author Gustavo Lobo
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DateValidatorService.class, MonthDayConverter.class})
public class TestDateValidatorService {

    @Autowired
    private DateValidatorService dateValidatorService;

    @Autowired
    private MonthDayConverter monthDayConverter;

    private static final String ERRO = "Data informada não tem formato suportado: %s";

    private static Stream<Arguments> badFormatsParameters() {
        return Stream.of(
                Arguments.of("31-07-1995", ERRO),
                Arguments.of("07-31-1995", ERRO),
                Arguments.of("1995-07-31", ERRO),
                Arguments.of("31/07/1995", ERRO),
                Arguments.of("07/31/1995", ERRO),
                Arguments.of("1995/07/31", ERRO)
        );
    }

    @ParameterizedTest
    @MethodSource("badFormatsParameters")
    public void testDataProviderBadFormats(String date, String erro) {

        Exception exception = Assertions.assertThrows(RuntimeException.class, () ->
                dateValidatorService.supportsDateFormat(date));

        Assert.assertEquals(String.format(erro, date), exception.getMessage());
    }
}
