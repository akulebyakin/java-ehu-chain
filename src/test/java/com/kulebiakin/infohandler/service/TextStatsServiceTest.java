package com.kulebiakin.infohandler.service;

import com.kulebiakin.infohandler.component.TextComponent;
import com.kulebiakin.infohandler.component.TextComponentType;
import com.kulebiakin.infohandler.component.TextComposite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextStatsServiceTest {

    private TextStatsService textStatsService;

    @BeforeEach
    void setUp() {
        textStatsService = new TextStatsService();
    }

    private TextComponent buildText(int paragraphs, int sentencesPerParagraph, int wordsPerSentence, int charsPerWord) {
        TextComposite text = new TextComposite(TextComponentType.TEXT);
        for (int p = 0; p < paragraphs; p++) {
            TextComposite paragraph = new TextComposite(TextComponentType.PARAGRAPH);
            for (int s = 0; s < sentencesPerParagraph; s++) {
                TextComposite sentence = new TextComposite(TextComponentType.SENTENCE);
                for (int w = 0; w < wordsPerSentence; w++) {
                    TextComposite lexeme = new TextComposite(TextComponentType.LEXEME) {
                        @Override
                        public String toString() {
                            return "a".repeat(charsPerWord);
                        }
                    };
                    sentence.add(lexeme);
                }
                paragraph.add(sentence);
            }
            text.add(paragraph);
        }
        return text;
    }

    @Test
    void countsSentencesInSingleParagraph() {
        TextComponent text = buildText(1, 3, 2, 4);
        Assertions.assertEquals(3, textStatsService.countSentences(text));
    }

    @Test
    void countsSentencesInMultipleParagraphs() {
        TextComponent text = buildText(2, 2, 1, 1);
        Assertions.assertEquals(4, textStatsService.countSentences(text));
    }

    @Test
    void countsWordsAcrossAllSentences() {
        TextComponent text = buildText(2, 2, 3, 2);
        Assertions.assertEquals(12, textStatsService.countWords(text));
    }

    @Test
    void countsCharactersAcrossAllWords() {
        TextComponent text = buildText(1, 2, 2, 5);
        Assertions.assertEquals(20, textStatsService.countCharacters(text));
    }

    @Test
    void returnsZeroForEmptyTextComponent() {
        TextComposite text = new TextComposite(TextComponentType.TEXT);
        Assertions.assertEquals(0, textStatsService.countSentences(text));
        Assertions.assertEquals(0, textStatsService.countWords(text));
        Assertions.assertEquals(0, textStatsService.countCharacters(text));
    }

    @Test
    void handlesTextWithEmptyParagraphs() {
        TextComposite text = new TextComposite(TextComponentType.TEXT);
        text.add(new TextComposite(TextComponentType.PARAGRAPH));
        Assertions.assertEquals(0, textStatsService.countSentences(text));
        Assertions.assertEquals(0, textStatsService.countWords(text));
        Assertions.assertEquals(0, textStatsService.countCharacters(text));
    }

    @Test
    void handlesTextWithEmptySentences() {
        TextComposite text = new TextComposite(TextComponentType.TEXT);
        TextComposite paragraph = new TextComposite(TextComponentType.PARAGRAPH);
        paragraph.add(new TextComposite(TextComponentType.SENTENCE));
        text.add(paragraph);
        Assertions.assertEquals(1, textStatsService.countSentences(text));
        Assertions.assertEquals(0, textStatsService.countWords(text));
        Assertions.assertEquals(0, textStatsService.countCharacters(text));
    }

    @Test
    void handlesTextWithEmptyWords() {
        TextComposite text = new TextComposite(TextComponentType.TEXT);
        TextComposite paragraph = new TextComposite(TextComponentType.PARAGRAPH);
        TextComposite sentence = new TextComposite(TextComponentType.SENTENCE);
        sentence.add(new TextComposite(TextComponentType.LEXEME) {
            @Override
            public String toString() {
                return "";
            }
        });
        paragraph.add(sentence);
        text.add(paragraph);
        Assertions.assertEquals(1, textStatsService.countSentences(text));
        Assertions.assertEquals(1, textStatsService.countWords(text));
        Assertions.assertEquals(0, textStatsService.countCharacters(text));
    }
}
