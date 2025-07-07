package com.kulebiakin.infohandler.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextLoader {

    public String load(Path path) throws IOException {
        return new String(Files.readAllBytes(path));
    }
}
