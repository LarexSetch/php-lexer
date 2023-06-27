package ru.airux.lexer.php.reader.back;

import java.util.List;

public interface Reader {
    String nextWord();
    boolean eof();

    interface Window {
        char character();
        List<String> words();
        String currentWord();
        void clear();
        boolean containsKeyWord(String keyWord);
    }
}
