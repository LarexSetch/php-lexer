package ru.airux.lexer.php.token;

import ru.airux.lexer.php.reader.FileReader;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PhpInterface extends ObjectOrientedElement {

    @Override
    protected List<String> keyWords() {
        return Collections.emptyList();
    }

    @Override
    protected void handle(String keyWord, FileReader fileReader) throws IOException {

    }
}
