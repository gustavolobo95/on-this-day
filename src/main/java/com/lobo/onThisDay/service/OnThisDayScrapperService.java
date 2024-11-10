package com.lobo.onThisDay.service;

import com.lobo.onThisDay.model.Person;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Month;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Service que fará os requests e o scrapping da fonte do onthisday.com
 *
 * @author Gustavo Lobo
 */
@Service
public class OnThisDayScrapperService {

    private static final String BASE_URL = "https://www.onthisday.com/%s/%s/%d";
    private static final String BIRTH_EVENTS_URL = "birthdays";

    /**
     * Esse metodo será o responsável por fazer os requests da lista de nascidos na fonte do onthisday, de inicio estamos
     * buscando apenas as pessoas que estão em sessões de destaque na pagina.
     * <p>
     * TODO: Refatorar em pequenos metodos, adicionar validações e criar testes.
     *
     * @param date
     * @return lista de pessoas nascidas em "x" dia.
     */
    public List<Person> getRequestForPersonsBornIn(MonthDay date) throws IOException {
        int day = date.getDayOfMonth();
        Month month = date.getMonth();

        String personName;
        String livingPeriodOrAge;
        List<Person> returnedPersons = new ArrayList<>();

        Document document = Jsoup.connect(String.format(BASE_URL, BIRTH_EVENTS_URL, month.toString(), day)).get();

        // Pegando todas as pessoas que são marcadas como destaque:
        Elements highlights = document.getElementsByClass("section section--highlight section--poi section--poi-b");

        for(Element personElement : highlights) {

            Person person = new Person();

            Elements spanTitles = personElement.getElementsByClass("poi__heading-txt");

            for(Element title : spanTitles) {
                    String[] nameAndDate = title.toString().split("<span class=\"poi__date\">");
                    if (nameAndDate.length == 2) {
                        personName = nameAndDate[0].replaceAll("<span class=\"poi__heading-txt\">", "").trim();
                        livingPeriodOrAge = nameAndDate[1].replaceAll("</span></span>", "").trim();
                        person.setName(personName);
                        person.setLivingPeriodOrAge(livingPeriodOrAge);
                    }
            }

            Elements gridToGetDescription = personElement.getElementsByClass("grid__item one-half--768 five-twelfths--1024");

            Element grid = gridToGetDescription.stream()
                                                .findFirst()
                                                .orElseThrow(() -> new RuntimeException("Não foi encontrado nenhum destaque."));

            String descriptionUnformmated = grid.getElementsByTag("p").toString();

            String description = formatDescriptionElement(descriptionUnformmated);

            person.setDescription(description);

            returnedPersons.add(person);
        }

        return returnedPersons;
    }

    // TODO: Adicionar teste unitario.
    private String formatDescriptionElement(String descriptionUnformmated) {
        // Remove a tag <p> e </p>
        String noPTags = descriptionUnformmated.replaceAll("<\\/?p>", "");

        // Remove qualquer tag <a ...> e </a>
        String cleanText = noPTags.replaceAll("<a[^>]*>", "").replaceAll("</a>", "");

        return cleanText;
    }

}
