package ru.airux.lexer.php.token;

import ru.airux.lexer.php.reader.CharsWindow;

import java.util.HashMap;
import java.util.Map;

class MappedChain implements CharsWindow {
    private final Map<String, CharsWindow> map = new HashMap<>();

    public boolean contains(String type) {
        return map.get(type).toString().equals(type);
    }

    public MappedChain add(String type) {
        map.put(type, CharsWindow.newInstance(type.length()));

        return this;
    }

    @Override
    public void put(char ch) {
        map.forEach((s, charsWindow) -> charsWindow.put(ch));
    }
}
