package com.kulebiakin.infohandler.service;

import com.kulebiakin.infohandler.component.TextComponent;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class PalindromeService {

    public Set<String> findPalindromes(TextComponent text) {
        Set<String> result = new HashSet<>();
        text.getChildren().forEach(paragraph ->
            paragraph.getChildren().forEach(sentence ->
                sentence.getChildren().forEach(lexeme -> {
                    String word = lexeme.toString()
                        .replaceAll("[^a-zA-Zа-яА-ЯёЁ]", "")
                        .toLowerCase(Locale.ROOT);
                    if (word.length() > 1 && isPalindrome(word)) {
                        result.add(word);
                    }
                })
            )
        );
        return result;
    }

    private static boolean isPalindrome(String word) {
        return word.contentEquals(new StringBuilder(word).reverse());
    }
}
