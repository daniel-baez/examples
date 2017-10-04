package cl.daplay.first;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ExtensionUT {

    private final Product F = new Product("F");
    private final Price unitF = new Price(1, F, BigDecimal.valueOf(1));
    private final Price promoF = new Price(1, F, BigDecimal.valueOf(0.9), 10);

    private final Product A = new Product("A");
    private final Price unitA = new Price(1, A, BigDecimal.valueOf(2));
    private final Price promoA = new Price(4, A, BigDecimal.valueOf(7));

    private final Set<Price> prices = new HashSet<>(asList(unitA, promoA, promoF, unitF));

    private final Comparator<Price> pricingStrategy =
            Comparator.comparing(Price::getMinQty)
                    .reversed()
                    .thenComparing(Comparator.comparing(Price::getQuantity).reversed());

    @Test
    public void test_aaaaa() throws TerminalException {
        final Store store = Store.newStore(prices, pricingStrategy);

        final Terminal terminal = store.newTerminal();
        final String input = "AAAAA";

        for (final char code : input.toCharArray()) {
            terminal.scan(Character.toString(code));
        }

        final BigDecimal expected = BigDecimal.valueOf(9);

        assertEquals("lalala ", expected, terminal.newTotal());
    }

    @Test
    public void test_ffffffffffaaaaa() throws TerminalException {
        final Store store = Store.newStore(prices, pricingStrategy);

        final Terminal terminal = store.newTerminal();
        final String input = "FFFFFFFFFFAAAAA";

        for (final char code : input.toCharArray()) {
            terminal.scan(Character.toString(code));
        }

        final BigDecimal expected = BigDecimal.valueOf(18);

        assertTrue("lalala ", 0 == expected.compareTo(terminal.newTotal()));
    }


    @Test
    public void test_f() throws TerminalException {
        final Store store = Store.newStore(prices, pricingStrategy);

        final Terminal terminal = store.newTerminal();
        final String input = "F";

        for (final char code : input.toCharArray()) {
            terminal.scan(Character.toString(code));
        }

        final BigDecimal expected = BigDecimal.valueOf(1);

        assertEquals("F totals 1.00", expected, terminal.newTotal());
    }

    @Test
    public void test_fff() throws TerminalException {
        final Store store = Store.newStore(prices, pricingStrategy);

        final Terminal terminal = store.newTerminal();
        final String input = "FFF";

        for (final char code : input.toCharArray()) {
            terminal.scan(Character.toString(code));
        }

        final BigDecimal expected = BigDecimal.valueOf(3);

        assertEquals("FFF totals 3.00", expected, terminal.newTotal());
    }

    @Test
    public void test_10_f() throws TerminalException {
        final Store store = Store.newStore(prices, pricingStrategy);

        final Terminal terminal = store.newTerminal();
        final String input = "FFFFFFFFFF";

        for (final char code : input.toCharArray()) {
            terminal.scan(Character.toString(code));
        }

        final BigDecimal expected = BigDecimal.valueOf(9);

        assertTrue("FFFFFFFFFF totals 9.00", 0 == expected.compareTo(terminal.newTotal()));
    }


    @Test
    public void test_12_f() throws TerminalException {
        final Store store = Store.newStore(prices, pricingStrategy);

        final Terminal terminal = store.newTerminal();
        final String input = "FFFFFFFFFFFF";

        for (final char code : input.toCharArray()) {
            terminal.scan(Character.toString(code));
        }

        final BigDecimal expected = BigDecimal.valueOf(10.8);

        assertTrue("FFFFFFFFFFFF totals 10.80", 0 == expected.compareTo(terminal.newTotal()));
    }

}
