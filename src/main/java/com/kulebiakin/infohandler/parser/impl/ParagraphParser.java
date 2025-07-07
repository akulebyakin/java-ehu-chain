package com.kulebiakin.infohandler.parser.impl;

import com.kulebiakin.infohandler.component.TextComponent;
import com.kulebiakin.infohandler.component.TextComponentType;
import com.kulebiakin.infohandler.component.TextComposite;
import com.kulebiakin.infohandler.parser.TextParser;

public class ParagraphParser implements TextParser {

    private static final String PARAGRAPH_DELIMITER = "\\n+|\\t+| {4,}";

    private TextParser next;

    @Override
    public void setNext(TextParser nextParser) {
        this.next = nextParser;
    }

    @Override
    public TextComponent parse(String data) {
        TextComposite textComposite = new TextComposite(TextComponentType.TEXT);

        String[] paragraphs = data.trim().split(PARAGRAPH_DELIMITER);
        for (String paragraph : paragraphs) {
            TextComponent paragraphComponent = next.parse(paragraph.trim());
            textComposite.add(paragraphComponent);
        }

        return textComposite;
    }
}
