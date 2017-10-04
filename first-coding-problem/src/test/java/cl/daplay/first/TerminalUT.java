package cl.daplay.first;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Comparator;

import static cl.daplay.first.Main.newExampleSetOfPrices;
import static org.junit.Assert.assertEquals;

public class TerminalUT {

    private final Comparator<Price> pricingStrategy = Comparator.comparing(Price::getQuantity).reversed();
    private final Store store = Store.newStore(newExampleSetOfPrices(), pricingStrategy);

    @Test
    public void test_ABCDABAA() throws TerminalException {
        final Terminal terminal = store.newTerminal();
        final String input = "ABCDABAA";

        for (final char code : input.toCharArray()) {
            terminal.scan(Character.toString(code));
        }

        final BigDecimal expected = new BigDecimal("32.40");

        assertEquals("total should equals $32.40", expected, terminal.newTotal());
    }

    @Test
    public void test_CCCCCCC() throws TerminalException {
        final Terminal terminal = store.newTerminal();
        final String input = "CCCCCCC";

        for (final char code : input.toCharArray()) {
            terminal.scan(Character.toString(code));
        }

        final BigDecimal expected = new BigDecimal("7.25");

        assertEquals("total should equals $7.25", expected, terminal.newTotal());
    }

    @Test
    public void test_ABCD() throws TerminalException {
        final Terminal terminal = store.newTerminal();
        final String input = "ABCD";

        for (final char code : input.toCharArray()) {
            terminal.scan(Character.toString(code));
        }

        final BigDecimal expected = new BigDecimal("15.40");

        assertEquals("total should equals $15.40", expected, terminal.newTotal());
    }

}
