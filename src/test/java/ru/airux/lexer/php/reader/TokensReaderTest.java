package ru.airux.lexer.php.reader;

import org.junit.jupiter.api.Test;
import ru.airux.lexer.php.token.PhpFileReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class TokensReaderTest {
    @Test
    public void read() throws URISyntaxException, IOException {
        var resource = TokensReaderTest.class.getResource("/First.php");
        assert resource != null;


        var file = new File(resource.toURI());
        var inputStream = new FileInputStream(file);
        var fileReader = new FileReader(inputStream);
        var tokens = PhpFileReader.read(fileReader);

        assert tokens.size() > 0;
    }

    @Test
    public void readAbstract() throws URISyntaxException, IOException {
        var resource = TokensReaderTest.class.getResource("/AbstractFirst.php");
        assert resource != null;

        var file = new File(resource.toURI());
        var inputStream = new FileInputStream(file);
        var fileReader = new FileReader(inputStream);
        var tokens = PhpFileReader.read(fileReader);

        assert tokens.size() > 0;
    }

    @Test
    public void readInterface() throws URISyntaxException, IOException {
        var resource = TokensReaderTest.class.getResource("/InterfaceFirst.php");
        assert resource != null;

        var file = new File(resource.toURI());
        var inputStream = new FileInputStream(file);
        var fileReader = new FileReader(inputStream);
        var tokens = PhpFileReader.read(fileReader);

        assert tokens.size() > 0;
    }
}
