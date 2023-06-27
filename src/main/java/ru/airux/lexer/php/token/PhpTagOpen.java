package ru.airux.lexer.php.token;

import java.io.IOException;
import ru.airux.lexer.php.reader.FileReader;
import java.util.LinkedList;
import java.util.List;

public class PhpTagOpen implements Token {
    private final List<Token> tokens = new LinkedList<>();

    @Override
    public List<Token> getChild() {
        return tokens;
    }

    public void read(FileReader fileReader) throws IOException {
        String NAMESPACE = "namespace";
        String CLAZZ = "class";
        String INTERFACE = "interface";
        String USE = "use";
        String FINAL = "final";
        String ABSTRACT = "abstract";
        var windows = new MappedChain()
                .add(CLAZZ)
                .add(INTERFACE)
                .add(FINAL)
                .add(NAMESPACE)
                .add(ABSTRACT)
                .add(USE);

        int ch;
        while ((ch = fileReader.read()) != -1) {
            windows.put((char) ch);
            if (windows.contains(NAMESPACE)) {
                var ns = new PhpNamespace();
                ns.read(fileReader);
                tokens.add(ns);
                continue;
            }

            if (windows.contains(USE)) {
                var u = new PhpUse();
                u.read(fileReader);
                tokens.add(u);
                continue;
            }

            if (windows.contains(FINAL)) {
                tokens.add(new PhpClassFinal());
                continue;
            }

            if (windows.contains(ABSTRACT)) {
                tokens.add(new PhpClassAbstract());
                continue;
            }

            if (windows.contains(CLAZZ)) {
                var cl = new PhpClass();
                cl.read(fileReader);
                tokens.add(cl);
            }

            if (windows.contains(INTERFACE)) {
                var i = new PhpInterface();
                i.read(fileReader);
                tokens.add(i);
            }
        }
    }
}
