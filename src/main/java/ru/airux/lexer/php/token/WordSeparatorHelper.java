package ru.airux.lexer.php.token;

class WordSeparatorHelper {
    public static boolean isWordSeparator(char ch) {
        return ch == ' ' || ch == '\n' || ch == '\t';
    }
}
