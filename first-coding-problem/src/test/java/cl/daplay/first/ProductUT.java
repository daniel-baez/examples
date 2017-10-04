package cl.daplay.first;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ProductUT {

    @Test
    public void test_construction() {
        // given: a random code
        final String productCode = UUID.randomUUID().toString();

        // when: a new product is created
        final Product product = new Product(productCode);

        // then: a product.getCode() should equal productName
        assertEquals("product.getCode() should be: " + productCode, productCode, product.getCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_blank_code_construction() {
        // given: a blank code
        final String productCode = "";

        // when: a new product is created, exception is thrown
        new Product(productCode);
        fail("product should not be created");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_blank_code_construction2() {
        // given: a blank code
        final String productCode = "  ";

        // when: a new product is created, exception is thrown
        new Product(productCode);
        fail("product should not be created");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_null_code_construction() {
        // given: a null code
        final String productCode = null;

        // when: a new product is created, exception is thrown
        new Product(productCode);
        fail("product should not be created");
    }

}
