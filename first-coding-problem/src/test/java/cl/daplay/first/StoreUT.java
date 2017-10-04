package cl.daplay.first;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StoreUT {

    private final Price priceA = new Price(1, new Product("a"), BigDecimal.valueOf(2));
    private final Price priceAvolume = new Price(2, new Product("a"), BigDecimal.valueOf(3));
    private final Price priceB = new Price(1, new Product("b"), BigDecimal.valueOf(4));

    private final Set<Price> prices = new HashSet<>(asList(priceA, priceAvolume, priceB));

    private final Comparator<Price> byAmountComparator = Comparator.comparing(Price::getAmount);

    @Test(expected = IllegalArgumentException.class)
    public void test_invalidConstruction() {
        // given: prices can't be null
        Store.newStore(null, byAmountComparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_invalidConstruction2() {
        // given: pricingStrategy can't be null
        Store.newStore(prices, null);
    }

    @Test
    public void test_getProductByCode() {
        // given: a store in a known state
        final Store store = Store.newStore(prices, byAmountComparator);

        // then:
        assertTrue("code 'a' is known", store.getProductByCode("a").isPresent());
        assertTrue("code 'b' is also known", store.getProductByCode("b").isPresent());
        assertFalse("code 'c' is unknown", store.getProductByCode("c").isPresent());
    }

    @Test
    public void test_getPriceFor() {
        // given: a store in a known state
        final Store store = Store.newStore(prices, byAmountComparator);

        // then:
        assertEquals("value for 0 products is also 0", BigDecimal.ZERO, store.getPriceFor(new Product("a"), 0));
        assertEquals("value for 1 product a is 2", BigDecimal.valueOf(2), store.getPriceFor(new Product("a"), 1));
        assertEquals("value for 2 products is 4 (given the current pricing strat)", BigDecimal.valueOf(4), store.getPriceFor(new Product("a"), 2));
    }
}
