package ru.airux.lexer.php.token;

import java.io.IOException;
import ru.airux.lexer.php.reader.FileReader;
import java.util.Collections;
import java.util.List;

public class PhpClassProperty implements Token {
    private String name;
    private String defaultValue; //

    @Override
    public List<Token> getChild() {
        return Collections.emptyList();
    }

    public void read(FileReader fileReader) throws IOException {
        var nameBuilder = new StringBuilder();
        var defaultValueBuilder = new StringBuilder();
        int ch;
        var state = State.NAME;
        while ((ch = fileReader.read()) != -1) {
            if (';' == ch) {
                break;
            }
            if ('=' == ch) {
                state = State.VALUE;
                continue;
            }
            if (!WordSeparatorHelper.isWordSeparator((char) ch)) {
                if (state == State.NAME) {
                    nameBuilder.append((char) ch);
                } else {
                    defaultValueBuilder.append((char) ch);
                }
            }
        }

        name = nameBuilder.toString();
        defaultValue = defaultValueBuilder.toString();
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return "PhpClassProperty{" +
                "name='" + name + '\'' +
                '}';
    }

    private enum State {NAME, VALUE}
}
