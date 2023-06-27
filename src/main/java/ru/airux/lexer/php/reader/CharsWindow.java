package ru.airux.lexer.php.reader;


public interface CharsWindow {
    void put(char ch);

    String toString();

    static CharsWindow newInstance(int capacity) {
        return new CharsWindow() {
            private final char[] chars = new char[capacity];

            @Override
            public void put(char ch) {
                for (int i = 0; i < chars.length - 1; i++) {
                    chars[i] = chars[i + 1];
                }
                chars[chars.length - 1] = ch;
            }

            public String toString() {
                return new String(chars);
            }
        };
    }
}
