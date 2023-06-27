package ru.airux.lexer.php.token;

import ru.airux.lexer.php.reader.FileReader;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public abstract class ObjectOrientedElement implements Token {
    private final static String EXTENSION = "extends";
    private final List<Token> tokens = new LinkedList<>();
    private String name;

    @Override
    public List<Token> getChild() {
        return tokens;
    }

    public void read(FileReader fileReader) throws IOException {
        var mc = new MappedChain().add(EXTENSION);
        keyWords().forEach(mc::add);
        int ch;
        readName(fileReader);
        while ((ch = fileReader.read()) != -1) {
            mc.put((char) ch);
            if ('{' == (char) ch) {
                readBody(fileReader);
                break;
            }

            if (mc.contains(EXTENSION)) {
                readExtension(fileReader);
                continue;
            }


            var keyWord = keyWords().stream().filter(mc::contains).findFirst();
            if (keyWord.isPresent()) {
                handle(keyWord.get(), fileReader);
            }
        }
    }

    abstract protected List<String> keyWords();

    abstract protected void handle(String keyWord, FileReader fileReader) throws IOException;

    protected void addToken(Token token) {
        tokens.add(token);
    }

    protected void readBody(FileReader fileReader) throws IOException {
        var body = new PhpClassBody();
        body.read(fileReader);
        tokens.add(body);
    }

    private void readName(FileReader fileReader) throws IOException {
        var nameBuilder = new StringBuilder();
        int ch;
        while ((ch = fileReader.read()) != -1) {
            if ('{' == ch) {
                setName(nameBuilder);
                readBody(fileReader);
                break;
            }

            if (WordSeparatorHelper.isWordSeparator((char) ch)) {
                if (!nameBuilder.isEmpty()) {
                    break;
                } else {
                    continue;
                }
            }

            nameBuilder.append((char) ch);
        }

        // TODO exception if builder empty
        setName(nameBuilder);
    }

    private void readExtension(FileReader fileReader) throws IOException {
        var extensionBuilder = new StringBuilder();
        int ch;
        while ((ch = fileReader.read()) != -1) {
            if ('{' == ch) {
                createExtension(extensionBuilder);
                readBody(fileReader);
                break;
            }

            if (WordSeparatorHelper.isWordSeparator((char) ch)) {
                if (!extensionBuilder.isEmpty()) {
                    break;
                } else {
                    continue;
                }
            }

            extensionBuilder.append((char) ch);
        }

        createExtension(extensionBuilder);
    }

    private void createExtension(StringBuilder extensionBuilder) {
        // TODO exception if empty
        tokens.add(new PhpClassExtends(extensionBuilder.toString()));
    }

    private void setName(StringBuilder nameBuilder) {
        // TODO exception if builder empty
        name = nameBuilder.toString();
    }

    public String name() {
        return name;
    }
}
