package ru.airux.lexer.php.reader.back;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class PhpClassReader1 implements Reader {
    private final InputStream inputStream;
    private final ReaderWindow window = new ReaderWindow();
    private boolean eof = false;

    public PhpClassReader1(File file) {
        try {
            this.inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
//
//    @Override
//    public Window window() {
//        return window;
//    }
//
//    @Override
//    public void read() {
//        try {
//            var ch = inputStream.read();
//            if (-1 == ch) {
//                eof = true;
//                inputStream.close();
//                window.character = 0;
//            } else {
//                window.setCharacter((char) ch);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public String nextWord() {
        return null;
    }

    public String readWord(char[] allowedChars) {
        var carriage = new Carriage();
        try {
            int ch;
            while ((ch = inputStream.read()) != -1) {
                if (inArray((char)ch, allowedChars)) {
                    carriage.put((char) ch);
                } else {
                    break;
                }
            }

            if (-1 == ch) {
                eof = true;
                inputStream.close();
                window.character = 0;
            } else {
                window.setCharacter((char) ch);
            }

            return carriage.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean eof() {
        return eof;
    }

    private boolean inArray(char ch, char[] chars) {
        for (char aChar : chars) {
            if (ch == aChar) {
                return true;
            }
        }

        return false;
    }

    public static class ReaderWindow implements Window {
        private char character = 0;
        private final List<String> words = new LinkedList<>();
        private final StringBuilder stringBuilder = new StringBuilder();

        private void setCharacter(char newChar) {
            character = newChar;
            if (' ' == character || '\n' == character || ';' == character) {
                if (!stringBuilder.toString().equals("")) {
                    words.add(stringBuilder.toString());
                    stringBuilder.delete(0, stringBuilder.length());
                }
            } else {
                stringBuilder.append(character);
            }
        }

        @Override
        public char character() {
            return character;
        }

        @Override
        public List<String> words() {
            return words;
        }

        @Override
        public String currentWord() {
            return stringBuilder.toString();
        }

        @Override
        public void clear() {
            words.clear();
            stringBuilder.delete(0, stringBuilder.length());
        }

        @Override
        public boolean containsKeyWord(String keyWord) {
            return words.contains(keyWord) || stringBuilder.toString().equals(keyWord);
        }
    }

    private static class Carriage {
        private final char[] chars = new char[2];

        void put(char ch) {
            for (int i = chars.length - 1; i > 0; i--) {
                chars[i] = chars[i - 1];
            }
            chars[0] = ch;
        }

        public String toString() {
            return new String(chars);
        }
    }
}
