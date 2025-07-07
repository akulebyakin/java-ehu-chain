package com.kulebiakin.infohandler;

import com.kulebiakin.infohandler.component.TextComponent;
import com.kulebiakin.infohandler.parser.ParserChainBuilder;
import com.kulebiakin.infohandler.parser.TextParser;
import com.kulebiakin.infohandler.util.TextLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.Objects;

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
        } catch (Exception e) {
            log.error("Application failed: ", e);
        }
    }
}
