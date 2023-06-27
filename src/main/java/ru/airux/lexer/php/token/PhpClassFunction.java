package ru.airux.lexer.php.token;

import java.io.IOException;
import ru.airux.lexer.php.reader.FileReader;
import java.util.LinkedList;
import java.util.List;

public class PhpClassFunction implements Token {
    private String name;
    private final List<Token> tokens = new LinkedList<>();

    @Override
    public List<Token> getChild() {
        return tokens;
    }

    public void read(FileReader fileReader) throws IOException {
        var nameBuilder = new StringBuilder();
        int ch;
        while ((ch = fileReader.read()) != -1) {
            if ('{' == ch) {
                readPhpClassFunctionBody(fileReader);
                break;
            }

            if ('(' == ch) {
                setName(nameBuilder);
                readPhpClassFunctionArgumentList(fileReader);
                nameBuilder.delete(0, nameBuilder.length());
                continue;
            }

            if (':' == ch) {
                readPhpClassFunctionReturnTypeAndBody(fileReader);
                break;
            }

            if (WordSeparatorHelper.isWordSeparator((char) ch) && !nameBuilder.isEmpty()) {
                setName(nameBuilder);
            } else if (!WordSeparatorHelper.isWordSeparator((char) ch)) {
                nameBuilder.append((char) ch);
            }
        }
    }

    @Override
    public String toString() {
        return "PhpClassFunction{" +
                "name='" + name + '\'' +
                '}';
    }

    private void readPhpClassFunctionReturnTypeAndBody(FileReader fileReader) throws IOException {
        var nameBuilder = new StringBuilder();
        int ch;
        while ((ch = fileReader.read()) != -1) {
            if (WordSeparatorHelper.isWordSeparator((char) ch)) {
                if (nameBuilder.isEmpty()) {
                    continue;
                } else {
                    break;
                }
            }

            if (';' == ch) {
                addReturnType(nameBuilder);
                return;
            }

            if ('{' == ch) {
                if (nameBuilder.isEmpty()) {
                    throw new RuntimeException("Unexpected end at " + fileReader.line() + ":" + fileReader.lineOffset());
                } else {
                    addReturnType(nameBuilder);
                    readPhpClassFunctionBody(fileReader);
                    return;
                }
            }

            nameBuilder.append((char) ch);
        }

        while ((ch = fileReader.read()) != -1) {
            if (WordSeparatorHelper.isWordSeparator((char) ch)) {
                continue;
            }

            if ('{' == ch) {
                addReturnType(nameBuilder);
                readPhpClassFunctionBody(fileReader);
                return;
            }
        }

        throw new RuntimeException("Unexpected eof at " + fileReader.line() + ":" + fileReader.lineOffset());
    }

    private void readPhpClassFunctionBody(FileReader fileReader) throws IOException {
        int ch, counter = 1;
        while ((ch = fileReader.read()) != -1) {
            if ('{' == ch) {
                counter++;
            }
            if ('}' == ch) {
                counter--;
            }

            if (0 == counter) {
                break;
            }
        }

        tokens.add(new PhpClassFunctionBody());
    }

    private void addReturnType(StringBuilder nameBuilder) {
        tokens.add(new PhpClassFunctionReturnType(nameBuilder.toString()));
    }

    private void readPhpClassFunctionArgumentList(FileReader fileReader) throws IOException {
        var argumentList = new PhpClassFunctionArgs();
        argumentList.read(fileReader);
        tokens.add(argumentList);
    }

    private void setName(StringBuilder nameBuilder) {
        name = nameBuilder.toString();
    }

    public String name() {
        return name;
    }


    enum TypeReadState {TYPE, BODY}

    record TypeResult(boolean bodyRead) {
    }
}
