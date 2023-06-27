package ru.airux.lexer.php.token;

import java.util.List;

public interface Token {
    public List<Token> getChild();
}
