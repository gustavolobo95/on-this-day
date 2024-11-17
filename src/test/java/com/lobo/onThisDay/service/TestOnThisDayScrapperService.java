package com.lobo.onThisDay.service;

import com.lobo.onThisDay.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.Month;
import java.time.MonthDay;
import java.util.List;

/**
 * Classe de teste para o service de Scrapper do onthisday.com
 *
 * @author Gustavo Lobo
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OnThisDayScrapperService.class})
public class TestOnThisDayScrapperService {

    @Autowired
    private OnThisDayScrapperService onThisDayScrapperService;

    @Test
    public void testGetHighlightPersonsBornIn() throws IOException {
        MonthDay date = MonthDay.of(Month.DECEMBER, 25);
        List<Person> returned = onThisDayScrapperService.getHighlightPersonsBornIn(date);
        for (Person person : returned) {
            System.out.println(person);
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }

    }

}
