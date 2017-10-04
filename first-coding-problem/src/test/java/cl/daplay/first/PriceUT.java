package cl.daplay.first;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PriceUT {

    private final Product exampleProduct = new Product("a");

    @Test
    public void test_construction() {
        // given: a known instance (zero is a valid price)
        final Price price = new Price(1, exampleProduct, BigDecimal.ZERO);

        // then:
        assertEquals("quantity should be 1", 1, price.getQuantity());
        assertEquals("product should be exampleProduct", exampleProduct, price.getProduct());
        assertEquals("amount should be 0 (a gift, for example)", BigDecimal.ZERO, price.getAmount());
    }

    @Test
    public void test_construction2() {
        // given: a known instance (>= 0 is a valid price)
        final Price price = new Price(1, exampleProduct, BigDecimal.ONE);

        // then:
        assertEquals("quantity should be 1", 1, price.getQuantity());
        assertEquals("product should be exampleProduct", exampleProduct, price.getProduct());
        assertEquals("amount should be 1", BigDecimal.ONE, price.getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_invalid_construction1() {
        // given: quantity <= 0 should fail
        new Price(0, exampleProduct, BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_invalid_construction2() {
        // given: quantity <= 0 should fail
        new Price(-1, exampleProduct, BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_invalid_construction3() {
        // given: null product should fail
        new Price(1, null, BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_invalid_construction4() {
        // given: less than zero amount should fail
        new Price(1, exampleProduct, BigDecimal.valueOf(-1));
    }

    @Test
    public void test_equality() {
        // given: a price with an amount for 1 exampleProduct
        final Price aPrice = new Price(1, exampleProduct, BigDecimal.valueOf(1));
        // given: another price with another amount for 1 exampleProduct
        final Price anotherPrice = new Price(1, exampleProduct, BigDecimal.valueOf(100));

        // then: they should be equal (this allow us to use Set<> to move info around)
        assertEquals("prices are equal only on quantity and product", aPrice, anotherPrice);
    }

    @Test
    public void test_inquality() {
        // given: a price with an amount for 1 exampleProduct
        final Price aPrice = new Price(1, exampleProduct, BigDecimal.valueOf(1));
        // given: another price with the same amount for 2 exampleProducts
        final Price anotherPrice = new Price(2, exampleProduct, BigDecimal.valueOf(1));

        // then: they shouldn't be equal (this allow us to use Set<> to move info around)
        assertNotEquals("prices are equal only on quantity and product", aPrice, anotherPrice);
    }

}
