package ru.airux.lexer.php.reader.back;

import ru.airux.lexer.php.token.*;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class TokensReader {

    public List<Token> read(File file) {
        List<Token> tokens = new LinkedList<>();
        var reader = new PhpClassReader(file);
        while (!reader.eof()) {
            reader.read();
//            parseTagOpen(reader, tokens);
//            parseNamespace(reader, tokens);
//            parseUseList(reader, tokens);
//            parsePhpClass(reader, tokens);
        }

        return tokens;
    }
//
//    private void parseTagOpen(PhpReader reader, List<Token> tokens) {
//        var token = PhpTagOpen.parse(reader);
//        if (null != token) {
//            tokens.add(token);
//        }
//    }
//
//    private void parseNamespace(PhpReader reader, List<Token> tokens) {
//        var token = PhpNamespace.parse(reader);
//        if (null != token) {
//            tokens.add(token);
//        }
//    }
//
//    private void parseUseList(PhpReader reader, List<Token> tokens) {
//        var token = PhpUse.parse(reader);
//        if (null != token) {
//            tokens.add(token);
//        }
//    }
//
//    private void parsePhpClass(PhpReader reader, List<Token> tokens) {
//        var token = PhpClass.parse(reader);
//        if (null != token) {
//            tokens.add(token);
//        }
//    }
}
