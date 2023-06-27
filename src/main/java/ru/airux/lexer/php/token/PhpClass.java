package ru.airux.lexer.php.token;

import java.io.IOException;

import ru.airux.lexer.php.reader.FileReader;

import java.util.List;

public class PhpClass extends ObjectOrientedElement {
    private final static String IMPLEMENTATION = "implements";

    @Override
    protected List<String> keyWords() {
        return List.of(IMPLEMENTATION);
    }

    @Override
    protected void handle(String keyWord, FileReader fileReader) throws IOException {
        if (keyWord.equals(IMPLEMENTATION)) {
            readImplementations(fileReader);
        }
    }

    private void readImplementations(FileReader fileReader) throws IOException {
        var implementationBuilder = new StringBuilder();
        int ch;
        while ((ch = fileReader.read()) != -1) {
            if ('{' == ch) {
                createImplementation(implementationBuilder);
                readBody(fileReader);
                break;
            }

            if (',' == ch) {
                createImplementation(implementationBuilder);
                continue;
            }

            if (WordSeparatorHelper.isWordSeparator((char) ch)) {
                if (!implementationBuilder.isEmpty()) {
                    break;
                } else {
                    continue;
                }
            }

            implementationBuilder.append((char) ch);
        }

        createImplementation(implementationBuilder);
    }

    private void createImplementation(StringBuilder implementationBuilder) {
        // TODO exception if builder empty
        addToken(new PhpClassImplementation(implementationBuilder.toString()));
    }
}
