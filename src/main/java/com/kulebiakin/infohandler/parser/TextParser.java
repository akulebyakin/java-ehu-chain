package com.kulebiakin.infohandler.parser;

import com.kulebiakin.infohandler.component.TextComponent;

public interface TextParser {
    void setNext(TextParser nextParser);

    TextComponent parse(String data);
}
