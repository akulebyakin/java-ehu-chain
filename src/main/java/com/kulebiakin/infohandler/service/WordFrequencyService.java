package com.kulebiakin.infohandler.service;

import com.kulebiakin.infohandler.component.TextComponent;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class WordFrequencyService {

    public Map<String, Integer> countWordFrequencies(TextComponent text) {
        return text.getChildren().stream()
            .flatMap(paragraph -> paragraph.getChildren().stream())
            .flatMap(sentence -> sentence.getChildren().stream())
            .map(lexeme -> lexeme.toString()
                .replaceAll("[^a-zA-Zа-яА-ЯёЁ]", "")
                .toLowerCase(Locale.ROOT))
            .filter(word -> !word.isEmpty())
            .collect(Collectors.toMap(
                word -> word,
                word -> 1,
                Integer::sum
            ));
    }
}
