package com.lobo.onThisDay.service;

import com.lobo.onThisDay.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.MonthDay;
import java.util.List;

/**
 *
 * Service que conterá a logica para trazer os dados de nasicmento.
 *
 * @author Gustavo Lobo
 */
@Service
public class BirthService {

    @Autowired
    private OnThisDayScrapperService onThisDayScrapperService;

    /**
     * Esse metodo temporariamente ainda busca apenas as pessoas de destaque nas paginas de nascimento, será incrementado
     * futuramente para buscar a todos.
     * @param date
     * @return
     * @throws IOException
     */
    public List<Person> getPersonsBornIn(MonthDay date) {
        return onThisDayScrapperService.getHighlightPersonsBornIn(date);
    }

}
