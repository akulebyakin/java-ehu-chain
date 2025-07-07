package com.kulebiakin.infohandler.parser.impl;

import com.kulebiakin.infohandler.component.TextComponent;
import com.kulebiakin.infohandler.component.TextSymbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WordParserTest {

    private WordParser wordParser;

    @BeforeEach
    void setUp() {
        wordParser = new WordParser();
    }

    @Test
    void parsesSimpleWordIntoSymbols() {
        TextComponent result = wordParser.parse("word");
        Assertions.assertEquals(4, result.getChildren().size());
        Assertions.assertTrue(result.getChildren().stream().allMatch(c -> c instanceof TextSymbol));
        Assertions.assertEquals('w', ((TextSymbol) result.getChildren().get(0)).getSymbol());
        Assertions.assertEquals('d', ((TextSymbol) result.getChildren().get(3)).getSymbol());
    }

    @Test
    void parsesEmptyStringToEmptyComposite() {
        TextComponent result = wordParser.parse("");
        Assertions.assertEquals(0, result.getChildren().size());
    }

    @Test
    void parsesSingleCharacterWord() {
        TextComponent result = wordParser.parse("a");
        Assertions.assertEquals(1, result.getChildren().size());
        Assertions.assertEquals('a', ((TextSymbol) result.getChildren().get(0)).getSymbol());
    }

    @Test
    void parsesWhitespaceOnlyStringToEmptyComposite() {
        TextComponent result = wordParser.parse("   ");
        Assertions.assertEquals(3, result.getChildren().size());
        Assertions.assertEquals(' ', ((TextSymbol) result.getChildren().get(0)).getSymbol());
    }

    @Test
    void interpretsArithmeticExpressionAndParsesResult() {
        TextComponent result = wordParser.parse("2+2");
        Assertions.assertEquals(3, result.getChildren().size());
        Assertions.assertEquals('4', ((TextSymbol) result.getChildren().get(0)).getSymbol());
    }

    @Test
    void parsesWordWithSpecialCharacters() {
        TextComponent result = wordParser.parse("a-b_c!");
        Assertions.assertEquals(6, result.getChildren().size());
        Assertions.assertEquals('-', ((TextSymbol) result.getChildren().get(1)).getSymbol());
        Assertions.assertEquals('!', ((TextSymbol) result.getChildren().get(5)).getSymbol());
    }
}
