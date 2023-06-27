package ru.airux.lexer.php.token;

import java.util.Collections;
import java.util.List;

public record PhpClassFunctionBody() implements Token {
    @Override
    public List<Token> getChild() {
        return Collections.emptyList();
    }
}
