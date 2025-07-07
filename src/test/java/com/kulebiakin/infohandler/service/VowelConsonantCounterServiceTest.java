package com.kulebiakin.infohandler.service;

import com.kulebiakin.infohandler.component.TextComponent;
import com.kulebiakin.infohandler.component.TextComponentType;
import com.kulebiakin.infohandler.component.TextComposite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VowelConsonantCounterServiceTest {

    private VowelConsonantCounterService counterService;

    @BeforeEach
    void setUp() {
        counterService = new VowelConsonantCounterService();
    }

    private TextComponent buildTextWithSymbols(String... symbols) {
        TextComposite text = new TextComposite(TextComponentType.TEXT);
        TextComposite paragraph = new TextComposite(TextComponentType.PARAGRAPH);
        TextComposite sentence = new TextComposite(TextComponentType.SENTENCE);
        TextComposite lexeme = new TextComposite(TextComponentType.LEXEME);
        for (String symbol : symbols) {
            TextComposite symbolComponent = new TextComposite(TextComponentType.SYMBOL) {
                @Override
                public String toString() {
                    return symbol;
                }
            };
            lexeme.add(symbolComponent);
        }
        sentence.add(lexeme);
        paragraph.add(sentence);
        text.add(paragraph);
        return text;
    }

    @Test
    void countsVowelsInEnglishAndCyrillic() {
        TextComponent text = buildTextWithSymbols("a", "e", "и", "o", "b", "c");
        Assertions.assertEquals(4, counterService.countVowels(text));
    }

    @Test
    void countsConsonantsInEnglishAndCyrillic() {
        TextComponent text = buildTextWithSymbols("b", "c", "д", "ж", "a", "e");
        Assertions.assertEquals(4, counterService.countConsonants(text));
    }

    @Test
    void returnsZeroForTextWithNoLetters() {
        TextComponent text = buildTextWithSymbols("1", "2", "!", "?");
        Assertions.assertEquals(0, counterService.countVowels(text));
        Assertions.assertEquals(0, counterService.countConsonants(text));
    }

    @Test
    void handlesEmptyTextComponent() {
        TextComposite text = new TextComposite(TextComponentType.TEXT);
        Assertions.assertEquals(0, counterService.countVowels(text));
        Assertions.assertEquals(0, counterService.countConsonants(text));
    }

    @Test
    void countsVowelsAndConsonantsCaseInsensitive() {
        TextComponent text = buildTextWithSymbols("A", "E", "O", "B", "C", "Д", "Ж");
        Assertions.assertEquals(3, counterService.countVowels(text));
        Assertions.assertEquals(4, counterService.countConsonants(text));
    }

    @Test
    void ignoresSymbolsAndDigitsMixedWithLetters() {
        TextComponent text = buildTextWithSymbols("a", "1", "b", "!", "e", "2", "c", "?");
        Assertions.assertEquals(2, counterService.countVowels(text));
        Assertions.assertEquals(2, counterService.countConsonants(text));
    }
}
