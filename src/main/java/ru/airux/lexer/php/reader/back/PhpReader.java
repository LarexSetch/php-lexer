package ru.airux.lexer.php.reader.back;

import java.util.List;

public interface PhpReader {
    void read();
    boolean eof();
    Words words();
    char currentCarriage();
    interface Words {
        String next();
        String last();
        void clear();
        List<String> all();
    }
}
