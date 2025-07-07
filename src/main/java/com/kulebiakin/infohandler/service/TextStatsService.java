package com.kulebiakin.infohandler.service;

import com.kulebiakin.infohandler.component.TextComponent;

public class TextStatsService {

    public int countSentences(TextComponent text) {
        return text.getChildren().stream()
            .mapToInt(p -> p.getChildren().size())
            .sum();
    }

    public int countWords(TextComponent text) {
        return text.getChildren().stream()
            .flatMap(p -> p.getChildren().stream())
            .mapToInt(s -> s.getChildren().size())
            .sum();
    }

    public int countCharacters(TextComponent text) {
        return text.getChildren().stream()
            .flatMap(p -> p.getChildren().stream())
            .flatMap(s -> s.getChildren().stream())
            .mapToInt(l -> l.toString().length())
            .sum();
    }
}
