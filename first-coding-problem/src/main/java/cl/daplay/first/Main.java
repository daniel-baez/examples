package cl.daplay.first;


import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public final class Main {

    public static final void main(String... args) throws TerminalException {
        final Comparator<Price> pricingStrategy = Comparator.comparing(Price::getQuantity).reversed();
        final Set<Price> prices = newExampleSetOfPrices();

        final Store store = Store.newStore(prices, pricingStrategy);
        final Terminal terminal = store.newTerminal();

        terminal.scan("A");

        assert BigDecimal.valueOf(2).compareTo(terminal.newTotal()) == 0;

        terminal.scan("A");
        terminal.scan("A");
        terminal.scan("A");

        assert BigDecimal.valueOf(7).compareTo(terminal.newTotal()) == 0;

        terminal.clean();

        terminal.scan("A");
        terminal.scan("A");
        terminal.scan("A");
        terminal.scan("A");
        terminal.scan("A");
        terminal.scan("A");

        assert BigDecimal.valueOf(11).compareTo(terminal.newTotal()) == 0;

        System.out.println("all assertions are ok");
    }

    public static Set<Price> newExampleSetOfPrices() {
        final Product productA = new Product("A");
        final Product productB = new Product("B");
        final Product productC = new Product("C");
        final Product productD = new Product("D");

        final Set<Price> prices = new HashSet<>();
        // data from the example at "instructions.md"
        prices.add(new Price(1, productA, BigDecimal.valueOf(2)));
        prices.add(new Price(4, productA, BigDecimal.valueOf(7)));

        prices.add(new Price(1, productB, BigDecimal.valueOf(12)));

        prices.add(new Price(1, productC, BigDecimal.valueOf(1.25)));
        prices.add(new Price(6, productC, BigDecimal.valueOf(6)));

        prices.add(new Price(1, productD, BigDecimal.valueOf(0.15)));

        return prices;
    }

}
