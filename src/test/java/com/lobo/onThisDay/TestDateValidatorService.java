package com.lobo.onThisDay;

import com.lobo.onThisDay.service.DateValidatorService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

/**
 * @author Gustavo Lobo
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DateValidatorService.class})
public class TestDateValidatorService {

    // TODO: verificar uma forma de conseguir que esse bean seja injetado no contexto de testes
    @Autowired
    private DateValidatorService dateValidatorService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final String ERRO = "Data informada não tem formato suportado: %s";

    private static Stream<Arguments> badFormatsParameters() {
        return Stream.of(
                Arguments.of("31-07-1995", String.format(ERRO, "31-07-1995")),
                Arguments.of("07-31-1995", String.format(ERRO, "07-31-1995")),
                Arguments.of("1995-07-31", String.format(ERRO, "1995-07-31")),
                Arguments.of("31/07/1995", String.format(ERRO, "31/07/1995")),
                Arguments.of("07/31/1995", String.format(ERRO, "07/31/1995")),
                Arguments.of("1995/07/31", String.format(ERRO, "1995/07/31"))
        );
    }

    @ParameterizedTest
    @MethodSource("badFormatsParameters")
    public void testDataProviderBadFormats(String date, String erro) {

        Exception exception = Assertions.assertThrows(RuntimeException.class, () ->
                dateValidatorService.supportsDateFormat(date));

        Assert.assertEquals(erro, exception.getMessage());
    }
}
