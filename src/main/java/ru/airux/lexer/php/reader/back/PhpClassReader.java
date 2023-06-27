package ru.airux.lexer.php.reader.back;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class PhpClassReader implements PhpReader {
    private static final char[] defaultWordSeparators = {' ', '\n', '\t'};
    private final InputStream inputStream;
    private final PhpClassReaderWords words;
    private final Window window = new Window();
    private boolean eof = false;
    private final StringBuilder wordBuilder = new StringBuilder();

    public PhpClassReader(File file) {
        try {
            inputStream = new FileInputStream(file);
            words = new PhpClassReaderWords(this);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void read() {
        try {
            var ch = inputStream.read();
            if (ch == -1) {
                eof = true;
            } else {
                window.put((char) ch);
                if (isWordSeparator((char) ch)) {
                    words.wordList.add(wordBuilder.toString());
                    wordBuilder.delete(0, wordBuilder.length());
                } else {
                    wordBuilder.append((char) ch);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public char currentCarriage() {
        return window.curr;
    }

    public boolean eof() {
        return eof;
    }

    @Override
    public Words words() {
        return words;
    }

    private static boolean isWordSeparator(char ch) {
        for (char c : defaultWordSeparators) {
            if (c == ch) {
                return true;
            }
        }

        return false;
    }

    public static class PhpClassReaderWords implements PhpReader.Words {
        private final PhpClassReader reader;
        List<String> wordList = new LinkedList<>();

        PhpClassReaderWords(PhpClassReader reader) {
            this.reader = reader;
        }

        @Override
        public String next() {
            var initialWordListSize = wordList.size();
            while (wordList.size() == initialWordListSize) {
                reader.read();
                if (reader.eof) {
                    break;
                }
            }

            return wordList.get(wordList.size() - 1);
        }

        @Override
        public String last() {
            if (wordList.size() == 0) {
                return null;
            }

            return wordList.get(wordList.size() - 1);
        }

        @Override
        public void clear() {
            wordList.clear();
        }

        @Override
        public List<String> all() {
            return wordList;
        }
    }

    private static class Window {
        private final char[] chars = new char[2];
        private char curr = 0;

        void put(char ch) {
            for (int i = chars.length - 1; i > 0; i--) {
                chars[i - 1] = chars[i];
            }
            chars[chars.length - 1] = ch;
            curr = ch;
        }

        public String toString() {
            return new String(chars);
        }
    }
}