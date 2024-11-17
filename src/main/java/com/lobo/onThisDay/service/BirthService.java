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

    public List<Person> getPersonsBornIn(MonthDay date) throws IOException {
        return onThisDayScrapperService.getHighlightPersonsBornIn(date);
    }

}
