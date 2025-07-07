package com.kulebiakin.infohandler.service;

import com.kulebiakin.infohandler.component.TextComponent;
import com.kulebiakin.infohandler.component.TextComponentType;
import com.kulebiakin.infohandler.component.TextComposite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class PalindromeServiceTest {

    private PalindromeService palindromeService;

    @BeforeEach
    void setUp() {
        palindromeService = new PalindromeService();
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
    void findsSinglePalindromeInText() {
        TextComponent text = buildTextComponentWithWords("level", "word", "test");
        Set<String> palindromes = palindromeService.findPalindromes(text);
        Assertions.assertEquals(Set.of("level"), palindromes);
    }

    @Test
    void findsMultiplePalindromesInText() {
        TextComponent text = buildTextComponentWithWords("deed", "noon", "word", "civic");
        Set<String> palindromes = palindromeService.findPalindromes(text);
        Assertions.assertEquals(Set.of("deed", "noon", "civic"), palindromes);
    }

    @Test
    void ignoresSingleCharacterWords() {
        TextComponent text = buildTextComponentWithWords("a", "b", "c");
        Set<String> palindromes = palindromeService.findPalindromes(text);
        Assertions.assertTrue(palindromes.isEmpty());
    }

    @Test
    void ignoresNonAlphabeticCharacters() {
        TextComponent text = buildTextComponentWithWords("!level!", "wo.rd", "te-st");
        Set<String> palindromes = palindromeService.findPalindromes(text);
        Assertions.assertEquals(Set.of("level"), palindromes);
    }

    @Test
    void findsPalindromesCaseInsensitive() {
        TextComponent text = buildTextComponentWithWords("Level", "DeeD", "NoOn");
        Set<String> palindromes = palindromeService.findPalindromes(text);
        Assertions.assertEquals(Set.of("level", "deed", "noon"), palindromes);
    }

    @Test
    void returnsEmptySetForTextWithoutPalindromes() {
        TextComponent text = buildTextComponentWithWords("word", "test", "java");
        Set<String> palindromes = palindromeService.findPalindromes(text);
        Assertions.assertTrue(palindromes.isEmpty());
    }

    @Test
    void findsPalindromesInMultipleParagraphsAndSentences() {
        TextComposite text = new TextComposite(TextComponentType.TEXT);

        TextComposite paragraph1 = new TextComposite(TextComponentType.PARAGRAPH);
        TextComposite sentence1 = new TextComposite(TextComponentType.SENTENCE);
        TextComposite lexeme1 = new TextComposite(TextComponentType.LEXEME) {
            @Override
            public String toString() {
                return "madam";
            }
        };
        sentence1.add(lexeme1);
        paragraph1.add(sentence1);

        TextComposite paragraph2 = new TextComposite(TextComponentType.PARAGRAPH);
        TextComposite sentence2 = new TextComposite(TextComponentType.SENTENCE);
        TextComposite lexeme2 = new TextComposite(TextComponentType.LEXEME) {
            @Override
            public String toString() {
                return "racecar";
            }
        };
        sentence2.add(lexeme2);
        paragraph2.add(sentence2);

        text.add(paragraph1);
        text.add(paragraph2);

        Set<String> palindromes = palindromeService.findPalindromes(text);
        Assertions.assertEquals(Set.of("madam", "racecar"), palindromes);
    }

    @Test
    void ignoresWordsWithDigitsOrSymbols() {
        TextComponent text = buildTextComponentWithWords("12321", "abba", "noon!");
        Set<String> palindromes = palindromeService.findPalindromes(text);
        Assertions.assertEquals(Set.of("noon", "abba"), palindromes);
    }

    @Test
    void supportsCyrillicPalindromes() {
        TextComponent text = buildTextComponentWithWords("топот", "дед", "мир");
        Set<String> palindromes = palindromeService.findPalindromes(text);
        Assertions.assertEquals(Set.of("топот", "дед"), palindromes);
    }
}
