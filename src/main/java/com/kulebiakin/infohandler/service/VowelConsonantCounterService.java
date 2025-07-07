package com.kulebiakin.infohandler.service;

import com.kulebiakin.infohandler.component.TextComponent;

import java.util.function.Predicate;

public class VowelConsonantCounterService {

    private static final String VOWELS = "aeiouаеёиоуыэюяAEIOUАЕЁИОУЫЭЮЯ";

    public int countVowels(TextComponent text) {
        return countCharacters(text, c -> VOWELS.indexOf(c) >= 0);
    }

    public int countConsonants(TextComponent text) {
        return countCharacters(text, c -> Character.isLetter(c) && VOWELS.indexOf(c) < 0);
    }

    private int countCharacters(TextComponent text, Predicate<Character> predicate) {
        return text.getChildren().stream()
            .flatMap(paragraph -> paragraph.getChildren().stream())
            .flatMap(sentence -> sentence.getChildren().stream())
            .flatMap(lexeme -> lexeme.getChildren().stream())
            .map(symbol -> symbol.toString().charAt(0))
            .filter(predicate)
            .mapToInt(c -> 1)
            .sum();
    }
}
