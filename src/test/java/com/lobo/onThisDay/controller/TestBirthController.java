package com.lobo.onThisDay.controller;

import com.lobo.onThisDay.component.MonthDayConverter;
import com.lobo.onThisDay.handler.GlobalExceptionHandler;
import com.lobo.onThisDay.model.PersonDTO;
import com.lobo.onThisDay.service.BirthService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.DateTimeException;
import java.time.Month;
import java.time.MonthDay;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 *
 * Classe de teste do controller de nascimentos
 *
 * @author Gustavo Lobo
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest({ BirthController.class, GlobalExceptionHandler.class })
public class TestBirthController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MonthDayConverter monthDayConverter;

    @SpyBean
    private GlobalExceptionHandler exceptionHandler;

    @MockBean
    private BirthService birthService;

    @Test
    public void testShouldReturnPersonsWhenDateIsValid() throws Exception {
        MonthDay date = MonthDay.of(Month.DECEMBER, 25);

        PersonDTO claraBarton = new PersonDTO("Clara Barton", "American nurse and founder of" +
                " the American Red Cross, born in North Oxford, Massachusetts", "(1821-1912)");

        List<PersonDTO> personList = List.of(claraBarton);

        when(birthService.getPersonsBornIn(date)).thenReturn(personList);

        mockMvc.perform(MockMvcRequestBuilders.get("/getPersonsBornIn").param("birthDate", "25/12"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(claraBarton.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(claraBarton.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].livingPeriodOrAge").value(claraBarton.getLivingPeriodOrAge()));
    }

    @Test
    public void testShouldThrowExceptionWhenDateInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getPersonsBornIn").param("birthDate", "31/02"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string((Matchers.containsString("A data informada não está num formato suportado: "))));

        ArgumentCaptor<DateTimeException> captor = ArgumentCaptor.forClass(DateTimeException.class);
        verify(exceptionHandler).handleDateTimeException(captor.capture());

        DateTimeException capturedException = captor.getValue();
        assert capturedException != null : "Esperava-se uma DateTimeException, mas não foi capturada nenhuma excessão.";

        verify(exceptionHandler, never()).handleGeneralException(any(Exception.class));
    }

}
