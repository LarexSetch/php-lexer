package ru.airux.lexer.php.token;

import java.util.Collections;
import java.util.List;

public record PhpClassFunctionArgument(String type, String name, String defaultValue, boolean isRef) implements Token {
    @Override
    public List<Token> getChild() {
        return Collections.emptyList();
    }
}
