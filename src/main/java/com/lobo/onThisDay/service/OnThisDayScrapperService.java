package com.lobo.onThisDay.service;

import com.lobo.onThisDay.model.PersonDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 *
 * Service que fará os requests e o scrapping da fonte do onthisday.com
 *
 * @author Gustavo Lobo
 */
@Service
public class OnThisDayScrapperService {

    // TODO: Talvez alterar a url base para ser especifica para nascimentos, outros eventos podem ter uma url em formato diferente.
    private static final String BASE_URL = "https://www.onthisday.com/%s/%s/%d";
    private static final String BIRTH_EVENTS_URL = "birthdays";
    private static final Logger LOGGER = LoggerFactory.getLogger(OnThisDayScrapperService.class);

    /**
     * Esse metodo será o responsável por fazer os requests da lista de nascidos na fonte do onthisday, de inicio estamos
     * buscando apenas as pessoas que estão em sessões de destaque na pagina.
     * <p>
     * TODO: Refatorar em pequenos metodos, adicionar validações e criar testes.
     *
     * @param date
     * @return lista de pessoas nascidas em "x" dia.
     */
    public List<PersonDTO> getHighlightPersonsBornIn(MonthDay date) {
        int day = date.getDayOfMonth();
        Month month = date.getMonth();

        List<PersonDTO> returnedPersonDTOS = new ArrayList<>();

        try {
            Document document = Jsoup.connect(String.format(BASE_URL, BIRTH_EVENTS_URL, month.toString(), day)).get();

            // Pegando todas as pessoas que são marcadas como destaque:
            Elements highlights = document.getElementsByClass("section section--highlight section--poi section--poi-b");

            fillPersonsList(highlights, returnedPersonDTOS);

        } catch (Exception e) {
            LOGGER.error("Ocorreu um erro durante o web scrapping: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        returnedPersonDTOS = orderPersonListByName(returnedPersonDTOS);

        return returnedPersonDTOS;
    }

    protected List<PersonDTO> orderPersonListByName(List<PersonDTO> returnedPersonDTOS) {
        returnedPersonDTOS = returnedPersonDTOS.stream().sorted(Comparator.comparing(PersonDTO::getName)).toList();
        return returnedPersonDTOS;
    }

    private void fillPersonsList(Elements highlights, List<PersonDTO> personDTOList) {

        for(Element personElement : highlights) {

            PersonDTO personDTO = new PersonDTO();

            Elements spanTitles = personElement.getElementsByClass("poi__heading-txt");

            fillNameAndLivingPeriod(spanTitles, personDTO);

            Elements gridToGetDescription = personElement.getElementsByClass("grid__item one-half--768 five-twelfths--1024");

            Element grid = gridToGetDescription.stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Não foi encontrado nenhum destaque."));

            String descriptionUnformmated = grid.getElementsByTag("p").toString();

            String description = formatDescriptionElement(descriptionUnformmated);

            personDTO.setDescription(description);

            personDTOList.add(personDTO);
        }
    }

    protected void fillNameAndLivingPeriod(Elements spanTitles, PersonDTO personDTO) {
        spanTitles.stream()
                .map(title -> title.toString().split("<span class=\"poi__date\">"))
                .filter(nameAndDate -> nameAndDate.length == 2)
                .forEach(nameAndDate -> {
                    String name = Optional.of(nameAndDate[0])
                            .map(n -> n.replaceAll("<span class=\"poi__heading-txt\">", "").trim())
                            .orElse(""); // Valor padrão para evitar nulls

                    String date = Optional.of(nameAndDate[1])
                            .map(d -> d.replaceAll("</span></span>", "").trim())
                            .orElse(""); // Valor padrão para evitar nulls

                    personDTO.setName(name);
                    personDTO.setLivingPeriodOrAge(date);
                }
        );
    }

    protected String formatDescriptionElement(String descriptionUnformmated) {
        // Remove a tag <p> e </p>
        String noPTags = descriptionUnformmated.replaceAll("<\\/?p>", "");

        // Remove qualquer tag <a ...> e </a>
        String cleanText = noPTags.replaceAll("<a[^>]*>", "").replaceAll("</a>", "");

        return cleanText;
    }

}
