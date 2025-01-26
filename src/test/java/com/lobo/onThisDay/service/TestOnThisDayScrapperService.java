package com.lobo.onThisDay.service;

import com.lobo.onThisDay.model.PersonDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.Month;
import java.time.MonthDay;
import java.util.List;

import static com.lobo.onThisDay.service.FixturePersonExpected.expectedPersonsBornIn25December;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
