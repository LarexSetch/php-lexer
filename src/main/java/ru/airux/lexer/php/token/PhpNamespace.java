package ru.airux.lexer.php.token;

import java.io.IOException;
import ru.airux.lexer.php.reader.FileReader;
import java.util.Collections;
import java.util.List;

public class PhpNamespace implements Token {
    private String statement;

    @Override
    public List<Token> getChild() {
        return Collections.emptyList();
    }

    public void read(FileReader fileReader) throws IOException {
        var statementBuilder = new StringBuilder();
        int ch;
        while ((ch = fileReader.read()) != -1) {
            if (ch == ';') {
                break;
            }

            if (ch != ' ' && ch != '\n' && ch != '\t') {
                statementBuilder.append((char)ch);
            }
        }

        statement = statementBuilder.toString();
    }

    public String statement() {
        return statement;
    }
}
