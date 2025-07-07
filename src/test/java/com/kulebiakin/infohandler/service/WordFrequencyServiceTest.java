package com.kulebiakin.infohandler.service;

import com.kulebiakin.infohandler.component.TextComponent;
import com.kulebiakin.infohandler.component.TextComponentType;
import com.kulebiakin.infohandler.component.TextComposite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class WordFrequencyServiceTest {

    private WordFrequencyService wordFrequencyService;

    @BeforeEach
    void setUp() {
        wordFrequencyService = new WordFrequencyService();
    }

    private TextComponent buildTextComponentWithWords(String... words) {
        TextComposite text = new TextComposite(TextComponentType.TEXT);
        TextComposite paragraph = new TextComposite(TextComponentType.PARAGRAPH);
        TextComposite sentence = new TextComposite(TextComponentType.SENTENCE);
        for (String word : words) {
            TextComposite lexeme = new TextComposite(TextComponentType.LEXEME) {
                @Override
                public String toString() {
                    return word;
                }
            };
            sentence.add(lexeme);
        }
        paragraph.add(sentence);
        text.add(paragraph);
        return text;
    }

    @Test
    void countsFrequenciesOfWordsIgnoringCaseAndSymbols() {
        TextComponent text = buildTextComponentWithWords("Word", "word!", "test", "Test.", "word");
        Map<String, Integer> frequencies = wordFrequencyService.countWordFrequencies(text);
        Assertions.assertEquals(Map.of("word", 3, "test", 2), frequencies);
    }

    @Test
    void returnsEmptyMapForTextWithNoWords() {
        TextComponent text = buildTextComponentWithWords("123", "!!!", "...");
        Map<String, Integer> frequencies = wordFrequencyService.countWordFrequencies(text);
        Assertions.assertTrue(frequencies.isEmpty());
    }

    @Test
    void countsFrequenciesOfCyrillicWords() {
        TextComponent text = buildTextComponentWithWords("тест", "Тест", "пример", "пример!");
        Map<String, Integer> frequencies = wordFrequencyService.countWordFrequencies(text);
        Assertions.assertEquals(Map.of("тест", 2, "пример", 2), frequencies);
    }

    @Test
    void ignoresEmptyAndNonAlphabeticLexemes() {
        TextComponent text = buildTextComponentWithWords("", " ", "!!!", "word");
        Map<String, Integer> frequencies = wordFrequencyService.countWordFrequencies(text);
        Assertions.assertEquals(Map.of("word", 1), frequencies);
    }

    @Test
    void countsFrequenciesAcrossMultipleParagraphsAndSentences() {
        TextComposite text = new TextComposite(TextComponentType.TEXT);

        TextComposite paragraph1 = new TextComposite(TextComponentType.PARAGRAPH);
        TextComposite sentence1 = new TextComposite(TextComponentType.SENTENCE);
        sentence1.add(new TextComposite(TextComponentType.LEXEME) {
            @Override
            public String toString() {
                return "hello";
            }
        });
        sentence1.add(new TextComposite(TextComponentType.LEXEME) {
            @Override
            public String toString() {
                return "world";
            }
        });
        paragraph1.add(sentence1);

        TextComposite paragraph2 = new TextComposite(TextComponentType.PARAGRAPH);
        TextComposite sentence2 = new TextComposite(TextComponentType.SENTENCE);
        sentence2.add(new TextComposite(TextComponentType.LEXEME) {
            @Override
            public String toString() {
                return "hello!";
            }
        });
        paragraph2.add(sentence2);

        text.add(paragraph1);
        text.add(paragraph2);

        Map<String, Integer> frequencies = wordFrequencyService.countWordFrequencies(text);
        Assertions.assertEquals(Map.of("hello", 2, "world", 1), frequencies);
    }
}
