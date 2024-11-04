package com.lobo.onThisDay;

import com.lobo.onThisDay.service.DateValidatorService;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.format.DateTimeParseException;

/**
 * @author Gustavo Lobo
 */
@SpringBootTest
@RunWith(DataProviderRunner.class)
public class TestDateValidatorService {

    @Autowired
    private DateValidatorService dateValidatorService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final String ERRO = "Data informada não tem formato suportado: ";

    @DataProvider
    public static Object[][] dataProviderBadFormats() {
        return new Object[][] {
                {"31-07-1995", new DateTimeParseException(ERRO, "31-07-1995", 0)},
                {"07-31-1995", new DateTimeParseException(ERRO, "07-31-1995", 0)},
                {"1995-07-31", new DateTimeParseException(ERRO, "1995-07-31", 0)},
                {"31/07/1995", new DateTimeParseException(ERRO, "31/07/1995", 0)},
                {"07/31/1995", new DateTimeParseException(ERRO, "07/31/1995", 0)},
                {"1995/07/31", new DateTimeParseException(ERRO, "1995/07/31", 0)},
        };
    }

    @Test
    @UseDataProvider("dataProviderBadFormats")
    public void testDataProviderBadFormats(String date, DateTimeParseException exception) {
        dateValidatorService.supportsDateFormat(date);

        expectedException.expect(exception.getClass());
        expectedException.expectMessage(exception.getMessage());
    }
}
