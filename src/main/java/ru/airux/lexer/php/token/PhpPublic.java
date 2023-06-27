package ru.airux.lexer.php.token;

import java.util.Collections;
import java.util.List;

public class PhpPublic implements Token {
    @Override
    public List<Token> getChild() {
        return Collections.emptyList();
    }
}
