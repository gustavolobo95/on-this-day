package com.lobo.onThisDay.service;

import com.lobo.onThisDay.model.PersonDTO;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.Month;
import java.time.MonthDay;
import java.util.List;

import static com.lobo.onThisDay.service.FixturePersonExpected.expectedPersonsBornIn25December;
import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("Testa cases de tratamento para descrição, remove tags html e seus conteudos em caso de links.")
    public void testFormatDescriptionElement() {
        String descriptionUnformated = "<p><a href=\"https://cat-bounce.com/\">" +
                "On a distant exoplanet orbiting a red dwarf star, an extraordinary feline species has evolved—known as the Aelurocorus, or \"Dancing Cat.\" " +
                "These cats, about the size of Earth’s domestic cats, have developed an intricate form of movement resembling dance. " +
                "Their evolution was shaped by the rhythmic vibrations of their planet's tectonic activity, which created unique, harmonic seismic patterns.\n" +
                "\n" +
                "Aelurocorus cats possess highly flexible skeletons, elongated tails, and pads on their paws with sensory receptors capable of detecting micro-vibrations. " +
                "These traits allow them to move in complex, choreographed patterns. " +
                "Their \"dancing\" is a social and survival behavior, used to communicate, intimidate predators, and attract mates." +
                "" +
                "</a></p>";

        String descriptionExpected =
                "On a distant exoplanet orbiting a red dwarf star, an extraordinary feline species has evolved—known as the Aelurocorus, or \"Dancing Cat.\" " +
                "These cats, about the size of Earth’s domestic cats, have developed an intricate form of movement resembling dance. " +
                "Their evolution was shaped by the rhythmic vibrations of their planet's tectonic activity, which created unique, harmonic seismic patterns.\n" +
                "\n" +
                "Aelurocorus cats possess highly flexible skeletons, elongated tails, and pads on their paws with sensory receptors capable of detecting micro-vibrations. " +
                "These traits allow them to move in complex, choreographed patterns. " +
                "Their \"dancing\" is a social and survival behavior, used to communicate, intimidate predators, and attract mates.";

        String formatedDescritpion = onThisDayScrapperService.formatDescriptionElement(descriptionUnformated);

        assertEquals(descriptionExpected, formatedDescritpion);
    }

    @Test
    @DisplayName("Testa se a lista de pessoas coletadas para o dia 25 de dezembro é igual a esperada.")
    public void testGetHighlightPersonsBornIn() throws IOException {
        MonthDay date = MonthDay.of(Month.DECEMBER, 25);

        List<PersonDTO> returned = onThisDayScrapperService.getHighlightPersonsBornIn(date);
        List<PersonDTO> expected = expectedPersonsBornIn25December();

        assertEquals(expected, returned);
    }

    @Test
    @DisplayName("Testa o metodo que divide o array de nome e data em 2")
    public void testSplitTitle() {
        Element element = Mockito.mock(Element.class);
        Mockito.when(element.toString()).thenReturn("<span class=\"poi__heading-txt\">Pius VI <span class=\"poi__date\">(1717-1799)</span></span>");
        String[] arrayExpected = {"<span class=\"poi__heading-txt\">Pius VI ", "(1717-1799)</span></span>"};
        String[] arrayReturned = onThisDayScrapperService.splitTitle(element);

        assertEquals(arrayExpected[0], arrayReturned[0]);
        assertEquals(arrayExpected[1], arrayReturned[1]);

    }

    @Test
    @DisplayName("Testa se o array não é nulo e se contem 2 elementos")
    public void testContainsTwoElements() {
        String[] validArray = {"Pius VI", "(1717-1799)"};
        String[] invalidNullArray = null;
        String[] invalidArrayWithDifferentLength = {"Corinthians"};

        assertTrue(onThisDayScrapperService.containsTwoElements(validArray));
        assertFalse(onThisDayScrapperService.containsTwoElements(invalidNullArray));
        assertFalse(onThisDayScrapperService.containsTwoElements(invalidArrayWithDifferentLength));
    }

    @Test
    @DisplayName("Testa o tratamento do array de nome e data, removendo as tags restantes")
    public void testExtractNameAndDate() {
        String[] nameAndDate = {"<span class=\"poi__heading-txt\">Pius VI ", "(1717-1799)</span></span>"};

        Pair<String, String> pairExpected = Pair.of("Pius VI", "(1717-1799)");
        Pair<String, String> pairReturned = onThisDayScrapperService.extractNameAndDate(nameAndDate);

        // Assert do nome
        assertEquals(pairExpected.getKey(), pairReturned.getKey());

        // Assert da data
        assertEquals(pairExpected.getValue(), pairReturned.getValue());
    }

    @Test
    @DisplayName("Teste de verify para garantir que invocou os setters do DTO")
    public void testFillPersonDTOWithNameAndLivingPeriod() {
        PersonDTO personDTO = Mockito.mock(PersonDTO.class);

        Pair<String, String> pairNameAndDate = Pair.of("Pius VI", "(1717-1799)");

        onThisDayScrapperService.fillPersonDTOWithNameAndLivingPeriod(personDTO, pairNameAndDate);

        Mockito.verify(personDTO).setName(pairNameAndDate.getKey());
        Mockito.verify(personDTO).setLivingPeriodOrAge(pairNameAndDate.getValue());
    }

}
