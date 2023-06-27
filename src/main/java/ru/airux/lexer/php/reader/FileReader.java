package ru.airux.lexer.php.reader;

import java.io.IOException;
import java.io.InputStream;

public class FileReader {
    private final InputStream inputStream;
    private int lineCounter = 1;
    private int lineOffset = 1;

    public FileReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public int read() throws IOException {
        var ch = inputStream.read();
        if ((char) ch == '\n') {
            lineCounter++;
            lineOffset = 0;
        } else {
            lineOffset++;
        }

        return ch;
    }

    public int line() {
        return lineCounter;
    }

    public int lineOffset() {
        return lineOffset;
    }
}
