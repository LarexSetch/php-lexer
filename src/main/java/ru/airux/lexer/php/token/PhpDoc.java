package ru.airux.lexer.php.token;

import java.io.IOException;
import ru.airux.lexer.php.reader.FileReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PhpDoc implements Token {
    private final List<Token> tokens = new LinkedList<>();
    private String body;

    @Override
    public List<Token> getChild() {
        return tokens;
    }

    public void read(FileReader fileReader) throws IOException {
        var END = "*/";
        var VAR_ANNOTATION = "@var";
        var mc = new MappedChain()
                .add(END)
                .add(VAR_ANNOTATION);
        int ch;
        var state = State.COMMENT;
        var tokenBuilder = new TokenBuilder();
        var bodyBuilder = new StringBuilder();
        while ((ch = fileReader.read()) != -1) {
            bodyBuilder.append((char) ch);
            mc.put((char) ch);
            if (mc.contains(END)) {
                tokens.add(tokenBuilder.build(state, fileReader));
                break;
            }

            if ('\n' == ch && state != State.COMMENT) {
                tokens.add(tokenBuilder.build(state, fileReader));
                tokenBuilder = new TokenBuilder();
                state = State.COMMENT;
            }

            if (mc.contains(VAR_ANNOTATION)) {
                tokens.add(tokenBuilder.build(state, fileReader));
                tokenBuilder = new TokenBuilder();
                state = State.VAR;
                continue;
            }
            tokenBuilder.put((char) ch);
        }

        body = bodyBuilder.toString();
    }

    private enum State {COMMENT, VAR}

    private static class TokenBuilder {
        private final List<String> wordStack = new LinkedList<>();
        private final StringBuilder wordBuilder = new StringBuilder();

        public void put(char ch) {
            if (ch == '*') {
                return;
            }
            if (WordSeparatorHelper.isWordSeparator(ch)) {
                if (!wordBuilder.isEmpty()) {
                    wordStack.add(wordBuilder.toString());
                    wordBuilder.delete(0, wordBuilder.length());
                }
            } else {
                wordBuilder.append(ch);
            }
        }

        public Token build(State state, FileReader fileReader) {
            switch (state) {
                case COMMENT -> {
                    return new Unknown();
                }
                case VAR -> {
                    if(!wordBuilder.isEmpty()) {
                        wordStack.add(wordBuilder.toString());
                    }
                    return new PhpDocVar(wordStack);
                }
            }
            throw new RuntimeException("Unsupported at " + fileReader.line() + ":" + fileReader.lineOffset());
        }
    }

    private record Unknown() implements Token {
        @Override
        public List<Token> getChild() {
            return Collections.emptyList();
        }
    }
}
