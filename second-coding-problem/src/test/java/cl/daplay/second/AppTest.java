package cl.daplay.second;

import static java.lang.String.format;

import java.io.InputStream;
import java.util.Scanner;

import org.junit.Test;

public class AppTest {

    @Test
    public void first_test() {
        final String justified = App.justify(10, loadResource("first-initial"));
        final String expected = loadResource("first-expected");

        assertion(justified, expected);
    }

    @Test
    public void second_test() {
        final String justified = App.justify(20, loadResource("second-initial"));
        final String expected = loadResource("second-expected");

        assertion(justified, expected);
    }

    private void assertion(final String justified, final String expected) {
        assert expected.length() == justified.length() : format("expected (length: %d) '%s' %n justified (length: %d) '%s' ",
                                                                expected.length(), expected, justified.length(), justified);
    }

    private String loadResource(final String resourceName) {
        final InputStream resourceAsStream = this.getClass().getResourceAsStream(format("/%s.txt", resourceName));

        final Scanner scanner = new Scanner(resourceAsStream);
        scanner.useDelimiter("\\Z");

        return scanner.next().trim();
    }

}
