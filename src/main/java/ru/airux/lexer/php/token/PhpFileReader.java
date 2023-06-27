package ru.airux.lexer.php.token;

import ru.airux.lexer.php.reader.CharsWindow;
import ru.airux.lexer.php.token.PhpTagOpen;
import ru.airux.lexer.php.token.Token;

import java.io.IOException;
import ru.airux.lexer.php.reader.FileReader;
import java.util.LinkedList;
import java.util.List;

public class PhpFileReader {
    private final static String OPEN_TAG = "<?php";

    public static List<Token> read(FileReader fileReader) throws IOException {
        var w = CharsWindow.newInstance(OPEN_TAG.length());
        int ch;
        var tokens = new LinkedList<Token>();
        while ((ch = fileReader.read()) != -1) {
            w.put((char) ch);
            if (w.toString().equals(OPEN_TAG)) {
                var openTag = new PhpTagOpen();
                openTag.read(fileReader);
                tokens.add(openTag);
            }
        }

        return tokens;
    }
}
