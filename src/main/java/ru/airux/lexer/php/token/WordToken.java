package ru.airux.lexer.php.token;

import java.util.Collections;
import java.util.List;

public record WordToken(String word) implements Token {
    @Override
    public List<Token> getChild() {
        return Collections.emptyList();
    }
}
