package com.kulebiakin.infohandler;

import com.kulebiakin.infohandler.component.TextComponent;
import com.kulebiakin.infohandler.parser.ParserChainBuilder;
import com.kulebiakin.infohandler.parser.TextParser;
import com.kulebiakin.infohandler.service.PalindromeService;
import com.kulebiakin.infohandler.service.TextStatsService;
import com.kulebiakin.infohandler.service.VowelConsonantCounterService;
import com.kulebiakin.infohandler.service.WordFrequencyService;
import com.kulebiakin.infohandler.util.TextLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class InfoHandlerApplication {

    private static final Logger log = LoggerFactory.getLogger(InfoHandlerApplication.class);

    public static void main(String[] args) {
        try {
            String pathToFile = "input.txt";
            TextLoader loader = new TextLoader();
            String rawText = loader.load(Paths.get(Objects.requireNonNull(InfoHandlerApplication.class.getClassLoader().getResource(pathToFile)).toURI()));

            TextParser parser = new ParserChainBuilder().build();
            TextComponent text = parser.parse(rawText);

            System.out.println("=== Parsed & Rebuilt Text ===");
            System.out.println(text.toString());

            // 1. Word Frequency
            WordFrequencyService freqService = new WordFrequencyService();
            Map<String, Integer> wordFreq = freqService.countWordFrequencies(text);
            System.out.println("\n=== Word Frequency ===");
            wordFreq.forEach((word, count) -> System.out.printf("%s: %d%n", word, count));

            // 2. Vowels / Consonants
            VowelConsonantCounterService vowelService = new VowelConsonantCounterService();
            int vowels = vowelService.countVowels(text);
            int consonants = vowelService.countConsonants(text);
            System.out.println("\n=== Vowels and Consonants ===");
            System.out.printf("Vowels: %d, Consonants: %d%n", vowels, consonants);

            // 3. Text statistics
            TextStatsService statsService = new TextStatsService();
            int sentences = statsService.countSentences(text);
            int words = statsService.countWords(text);
            int characters = statsService.countCharacters(text);
            System.out.println("\n=== Text Statistics ===");
            System.out.printf("Sentences: %d, Words: %d, Symbols: %d%n", sentences, words, characters);

            // 4. Palindromes
            PalindromeService palindromeService = new PalindromeService();
            Set<String> palindromes = palindromeService.findPalindromes(text);
            System.out.println("\n=== Palindromes ===");
            if (palindromes.isEmpty()) {
                System.out.println("No Palindromes found.");
            } else {
                palindromes.forEach(System.out::println);
            }
        } catch (Exception e) {
            log.error("Application failed: ", e);
        }
    }
}
