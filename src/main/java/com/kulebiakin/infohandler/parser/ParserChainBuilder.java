package com.kulebiakin.infohandler.parser;

import com.kulebiakin.infohandler.parser.impl.LexemeParser;
import com.kulebiakin.infohandler.parser.impl.ParagraphParser;
import com.kulebiakin.infohandler.parser.impl.SentenceParser;
import com.kulebiakin.infohandler.parser.impl.WordParser;

public class ParserChainBuilder {

    public TextParser build() {
        TextParser wordParser = new WordParser();
        TextParser lexemeParser = new LexemeParser();
        TextParser sentenceParser = new SentenceParser();
        TextParser paragraphParser = new ParagraphParser();

        lexemeParser.setNext(wordParser);
        sentenceParser.setNext(lexemeParser);
        paragraphParser.setNext(sentenceParser);

        return paragraphParser;
    }
}
