package com.kulebiakin.infohandler.parser.impl;

import com.kulebiakin.infohandler.component.TextComponent;
import com.kulebiakin.infohandler.component.TextComponentType;
import com.kulebiakin.infohandler.component.TextComposite;
import com.kulebiakin.infohandler.parser.TextParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParagraphParserTest {

    private ParagraphParser paragraphParser;
    private StubTextParser nextParser;

    static class StubTextParser implements TextParser {
        private String lastParsed;

        @Override
        public void setNext(TextParser nextParser) {
        }

        @Override
        public TextComponent parse(String data) {
            lastParsed = data;
            return new TextComposite(TextComponentType.PARAGRAPH);
        }

        public String getLastParsed() {
            return lastParsed;
        }
    }

    @BeforeEach
    void setUp() {
        paragraphParser = new ParagraphParser();
        nextParser = new StubTextParser();
        paragraphParser.setNext(nextParser);
    }

    @Test
    void parsesSingleParagraphCorrectly() {
        TextComponent result = paragraphParser.parse("This is a paragraph.");
        Assertions.assertEquals(1, result.getChildren().size());
        Assertions.assertEquals("This is a paragraph.", nextParser.getLastParsed());
    }

    @Test
    void parsesMultipleParagraphsSeparatedByNewline() {
        TextComponent result = paragraphParser.parse("First paragraph.\nSecond paragraph.");
        Assertions.assertEquals(2, result.getChildren().size());
        Assertions.assertEquals("Second paragraph.", nextParser.getLastParsed());
    }

    @Test
    void parsesMultipleParagraphsSeparatedByTabs() {
        TextComponent result = paragraphParser.parse("Para one.\t\tPara two.");
        Assertions.assertEquals(2, result.getChildren().size());
        Assertions.assertEquals("Para two.", nextParser.getLastParsed());
    }

    @Test
    void parsesMultipleParagraphsSeparatedBySpaces() {
        TextComponent result = paragraphParser.parse("Para1.    Para2.");
        Assertions.assertEquals(2, result.getChildren().size());
        Assertions.assertEquals("Para2.", nextParser.getLastParsed());
    }

    @Test
    void trimsInputAndIgnoresLeadingAndTrailingWhitespace() {
        TextComponent result = paragraphParser.parse("   Only one paragraph.   ");
        Assertions.assertEquals(1, result.getChildren().size());
        Assertions.assertEquals("Only one paragraph.", nextParser.getLastParsed());
    }

    @Test
    void returnsEmptyTextCompositeForEmptyInput() {
        TextComponent result = paragraphParser.parse("");
        Assertions.assertEquals(1, result.getChildren().size());
    }

    @Test
    void handlesMultipleDelimitersTogether() {
        TextComponent result = paragraphParser.parse("A.\n\n\t\t    B.");
        Assertions.assertEquals(4, result.getChildren().size());
        Assertions.assertEquals("B.", nextParser.getLastParsed());
    }
}
