package ru.airux.lexer.php.token;

import ru.airux.lexer.php.reader.CharsWindow;

import java.io.IOException;
import ru.airux.lexer.php.reader.FileReader;
import java.util.Collections;
import java.util.List;

public class PhpUse implements Token {
    private String statement;
    private String alias;

    @Override
    public List<Token> getChild() {
        return Collections.emptyList();
    }

    public void read(FileReader fileReader) throws IOException {
        var statementBuilder = new StringBuilder();
        var aliasBuilder = new StringBuilder();
        String AS = "as";
        var windowAs = CharsWindow.newInstance(AS.length());
        int ch;
        var state = State.STATEMENT;

        while ((ch = fileReader.read()) != -1) {
            if (WordSeparatorHelper.isWordSeparator((char) ch) && statementBuilder.isEmpty()) {
                continue;
            }
            if (ch == ';') {
                break;
            }
            if (WordSeparatorHelper.isWordSeparator((char) ch)) {
                state = State.STATEMENT_COMPLETE;
            }
            if (state == State.STATEMENT_COMPLETE && windowAs.toString().equals(AS)) {
                state = State.ALIAS;
            }
            if (!WordSeparatorHelper.isWordSeparator((char) ch)) {
                if (state == State.STATEMENT) {
                    statementBuilder.append((char)ch);
                }
                if (state == State.ALIAS) {
                    aliasBuilder.append((char)ch);
                }
            }
        }

        statement = statementBuilder.toString();
        if (state == State.ALIAS) {
            alias = aliasBuilder.toString();
        }
    }

    public String alias() {
        return alias;
    }

    public String statement() {
        return statement;
    }

    private enum State {STATEMENT, STATEMENT_COMPLETE, ALIAS}
}
