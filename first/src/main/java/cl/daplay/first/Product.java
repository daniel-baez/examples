package cl.daplay.first;

import static cl.daplay.first.Utils.isBlank;

public final class Product {

    private final String code;

    public Product(final String code) {
        if (isBlank(code)) {
            throw new IllegalArgumentException("Product code should not be null or empty.");
        }
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        cl.daplay.first.Product product = (cl.daplay.first.Product) o;

        if (!code.equals(product.code)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
