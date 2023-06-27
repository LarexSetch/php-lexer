package ru.airux.lexer.php.token;

import java.io.IOException;
import ru.airux.lexer.php.reader.FileReader;
import java.util.LinkedList;
import java.util.List;

public class PhpClassBody implements Token {
    private final static String PRIVATE = "private";
    private final static String PUBLIC = "public";
    private final static String PROTECTED = "public";
    private final static String STATIC = "static";
    private final static String CONST = "const";
    private final static String FUNCTION = "function";
    private final static String PHP_DOC = "/**";
    private final List<Token> tokens = new LinkedList<>();

    @Override
    public List<Token> getChild() {
        return tokens;
    }

    public void read(FileReader fileReader) throws IOException {
        var mc = new MappedChain()
                .add(PRIVATE)
                .add(PUBLIC)
                .add(PROTECTED)
                .add(STATIC)
                .add(CONST)
                .add(FUNCTION)
                .add(PHP_DOC);
        int ch;
        var wordBuilder = new StringBuilder();
        while ((ch = fileReader.read()) != -1) {
            mc.put((char) ch);
            if ('}' == ch) {
                wordBuilder.delete(0, wordBuilder.length());
                break;
            }

            if (checkAndAddKeyModifiers(mc)) {
                wordBuilder.delete(0, wordBuilder.length());
                continue;
            }
            if (mc.contains(CONST)) {
                wordBuilder.delete(0, wordBuilder.length());
                readConst(fileReader);
                continue;
            }

            if ('$' == ch) {
                wordBuilder.delete(0, wordBuilder.length());
                readProperty(fileReader);
                continue;
            }

            if (mc.contains(FUNCTION)) {
                 wordBuilder.delete(0, wordBuilder.length());
                readFunction(fileReader);
                continue;
            }

            if (mc.contains(PHP_DOC)) {
                wordBuilder.delete(0, wordBuilder.length());
                readPhpDoc(fileReader);
                continue;
            }
            checkWordToken(wordBuilder, (char) ch);
        }
    }

    private void checkWordToken(StringBuilder wordBuilder, char ch) {
        if (WordSeparatorHelper.isWordSeparator(ch) && !wordBuilder.isEmpty()) {
            tokens.add(new WordToken(wordBuilder.toString()));
            wordBuilder.delete(0, wordBuilder.length());
        } else if (!WordSeparatorHelper.isWordSeparator(ch)) {
            wordBuilder.append((char) ch);
        }
    }

    private void readProperty(FileReader fileReader) throws IOException {
        var token = new PhpClassProperty();
        token.read(fileReader);
        tokens.add(token);
    }

    private void readPhpDoc(FileReader fileReader) throws IOException {
        var token = new PhpDoc();
        token.read(fileReader);
        tokens.add(token);
    }

    private void readFunction(FileReader fileReader) throws IOException {
        var token = new PhpClassFunction();
        token.read(fileReader);
        tokens.add(token);
    }

    private void readConst(FileReader fileReader) throws IOException {
        var token = new PhpClassConstant();
        token.read(fileReader);
        tokens.add(token);
    }

    private boolean checkAndAddKeyModifiers(MappedChain mc) {
        if (mc.contains(PRIVATE)) {
            tokens.add(new PhpPrivate());
            return true;
        }
        if (mc.contains(PUBLIC)) {
            tokens.add(new PhpPublic());
            return true;
        }
        if (mc.contains(PROTECTED)) {
            tokens.add(new PhpPublic());
            return true;
        }
        if (mc.contains(STATIC)) {
            tokens.add(new PhpStatic());
            return true;
        }
        return false;
    }
}
