package ru.airux.lexer.php.token;

import java.io.IOException;
import ru.airux.lexer.php.reader.FileReader;
import java.util.LinkedList;
import java.util.List;

public class PhpClassFunctionArgs implements Token {
    private final List<Token> tokens = new LinkedList<>();

    @Override
    public List<Token> getChild() {
        return tokens;
    }

    public void read(FileReader fileReader) throws IOException {
        var nameBuilder = new StringBuilder();
        var typeBuilder = new StringBuilder();
        var defaultValueBuilder = new StringBuilder();
        var isRef = false;
        var state = State.TYPE;
        int ch;
        while ((ch = fileReader.read()) != -1) {
            if (')' == ch) {
                addArgument(typeBuilder, nameBuilder, defaultValueBuilder, isRef);
                break;
            }

            if ('&' == ch) {
                isRef = true;
                continue;
            }

            if (',' == ch) {
                addArgument(typeBuilder, nameBuilder, defaultValueBuilder, isRef);
                state = State.TYPE;
                continue;
            }

            if ('=' == ch) {
                state = State.DEFAULT_VALUE;
                continue;
            }

            if ('$' == ch) {
                state = State.VARIABLE;
                continue;
            }

            if (!WordSeparatorHelper.isWordSeparator((char) ch)) {
                if (state == State.TYPE) {
                    typeBuilder.append((char) ch);
                } else if (state == State.VARIABLE) {
                    nameBuilder.append((char) ch);
                } else if (state == State.DEFAULT_VALUE) {
                    defaultValueBuilder.append((char) ch);
                } else {
                    throw new RuntimeException("Unexpected reading at " + fileReader.line() + ":" + fileReader.lineOffset());
                }
            } else {
                if (state == State.TYPE && !typeBuilder.isEmpty()) {
                    state = State.TYPE_END;
                } else if (state == State.VARIABLE) {
                    state = State.VARIABLE_END;
                }
            }
        }
    }

    private void addArgument(StringBuilder typeBuilder, StringBuilder nameBuilder, StringBuilder defaultValueBuilder, boolean isRef) {
        if (typeBuilder.isEmpty() && nameBuilder.isEmpty()) {
            return;
        }

        tokens.add(new PhpClassFunctionArgument(typeBuilder.toString(), nameBuilder.toString(), defaultValueBuilder.toString(), isRef));
        nameBuilder.delete(0, nameBuilder.length());
        typeBuilder.delete(0, typeBuilder.length());
        defaultValueBuilder.delete(0, typeBuilder.length());
    }

    private enum State {TYPE, TYPE_END, DEFAULT_VALUE, DEFAULT_VALUE_END, VARIABLE, VARIABLE_END}
}
