package com.kulebiakin.infohandler.parser.impl;

import com.kulebiakin.infohandler.component.TextComponent;
import com.kulebiakin.infohandler.component.TextComponentType;
import com.kulebiakin.infohandler.component.TextComposite;
import com.kulebiakin.infohandler.parser.TextParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceParser implements TextParser {

    private static final String SENTENCE_REGEX = "[^.!?…]+[.!?…]+";
    private static final Pattern SENTENCE_PATTERN = Pattern.compile(SENTENCE_REGEX);

    private TextParser next;

    @Override
    public void setNext(TextParser nextParser) {
        this.next = nextParser;
    }

    @Override
    public TextComponent parse(String data) {
        TextComposite paragraphComposite = new TextComposite(TextComponentType.PARAGRAPH);

        Matcher matcher = SENTENCE_PATTERN.matcher(data);
        while (matcher.find()) {
            String sentence = matcher.group().trim();
            TextComponent sentenceComponent = next.parse(sentence);
            paragraphComposite.add(sentenceComponent);
        }

        return paragraphComposite;
    }
}
