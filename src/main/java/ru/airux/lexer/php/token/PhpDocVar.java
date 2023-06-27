package ru.airux.lexer.php.token;

import java.util.Collections;
import java.util.List;

public class PhpDocVar implements Token {
    private String type;
    private String variable;
    private String comment;

    PhpDocVar(List<String> statements) {
        for (int i = 0; i < statements.size(); i++) {
            switch (i) {
                case 0 -> type = statements.get(i);
                case 1 -> variable = statements.get(i);
                case 2 -> comment = statements.get(i);
            }
        }
    }

    @Override
    public List<Token> getChild() {
        return Collections.emptyList();
    }

    public String type() {
        return type;
    }

    public String variable() {
        return variable;
    }

    public String comment() {
        return comment;
    }

    @Override
    public String toString() {
        return "PhpDocVar{" +
                "type='" + type + '\'' +
                ", variable='" + variable + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
