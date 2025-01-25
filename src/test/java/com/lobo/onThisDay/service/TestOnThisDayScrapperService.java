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

    private List<PersonDTO> expectedPersonsBornIn25December() {
        return List.of(
            new PersonDTO("Ahmed Ben Bella",
                    "Algerian socialist revolutionary and 1st President of Algeria (1963-65), born in Maghnia, Algeria",
                    "(1918-2012)"
            ),

            new PersonDTO(
                    "Anwar Sadat",
                    "3rd President of Egypt (1970-81, Nobel 1978), born in Monufia, Egypt",
                    "(1918-1981)"
            ),

            new PersonDTO(
                    "Atal Bihari Vajpayee",
                    "Indian politician, 10th Prime Minister of India (1996, 1998-2004), born in Gwalior, India",
                    "(1924-2018)"
            ),

            new PersonDTO(
                    "Barbara Mandrell",
                    "1948 American country singer and TV host (Mandrell Sisters), born in Houston, Texas",
                    "(76 years old)"
            ),

            new PersonDTO(
                    "Cab Calloway",
                    "American singer, bandleader (\"Minnie the Moocher\"; \"The Jumpin' Jive\"), writer, radio host, and actor (The Blues Brothers), born in Rochester, New York",
                    "(1907-1994)"
            ),

            new PersonDTO(
                    "Clara Barton",
                    "American nurse and founder of the American Red Cross, born in North Oxford, Massachusetts",
                    "(1821-1912)"
            ),

            new PersonDTO(
                    "Clarrie Grimmett",
                    "Australian cricket spin bowler (37 Tests; 216 wickets @ 24.21; ICC Cricket Hall of Fame), born in Dunedin, New Zealand",
                    "(1891-1980)"
            ),

            new PersonDTO(
                    "Humphrey Bogart",
                    "American actor (Casablanca - \"Here's looking at you, kid\"), born in New York City",
                    "(1899-1957)"
            ),

            new PersonDTO(
                    "Jimmy Buffett",
                    "American country rock singer-songwriter (\"Margaritaville\") and restaurant entrepreneur (Margaritaville Cafe), born in Pascagoula, Mississippi [1]",
                    "(1946-2023)"
            ),

            new PersonDTO(
                    "Louis Chevrolet",
                    "Swiss auto racer and co-founder of Chevrolet Motor Car Company, born in La Chaux-de-Fonds, Switzerland",
                    "(1878-1941)"
            ),

            new PersonDTO(
                    "Marie Krogh",
                    "Danish physician (co-founder of Novo Nordisk), born in Vosegaard, Denmark",
                    "(1874-1943)"
            ),

            new PersonDTO(
                    "Mohammed Ali Jinnah",
                    "Founder of Pakistan who led the All-India Muslim League (1913-47) until he achieved his dream of Pakistan and became its 1st Governor-General (1947-48), born in Karachi, Bombay Presidency, British India",
                    "(1876-1948)"
            ),

            new PersonDTO(
                    "Nawaz Sharif",
                    "1949 Pakistani politician, Prime Minister (1990-93, 1997-99, 2013-17), born in Lahore, Pakistan",
                    "(75 years old)"
            ),

            new PersonDTO(
                    "Pius VI",
                    "Italian Pope (1775-99), born in Cesena, Emilia-Romagna, Papal States",
                    "(1717-1799)"
            ),

            new PersonDTO(
                    "Sissy Spacek",
                    "1949 American Academy Award-winning actress (Carrie; Badlands; Coal Miner's Daughter; The Help; Bloodline), born in Quitman, Texas",
                    "(75 years old)"
            )
        );
    }

}
