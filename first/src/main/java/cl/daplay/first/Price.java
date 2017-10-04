package cl.daplay.first;

import java.math.BigDecimal;

public final class Price {

    private final long quantity;
    private final long minQty;

    private final Product product;
    private final BigDecimal amount;

    public Price(long quantity,
                 Product product,
                 BigDecimal amount) {
        this(quantity, product, amount, 1);
    }

    public Price(long quantity,
                 Product product,
                 BigDecimal amount,
                 long minQty) {
        if (minQty <= 0 || quantity <= 0 || null == product || null == amount || amount.compareTo(BigDecimal.ZERO) < 0) {
            final String template = "Price can't not be created with quantity[%s], currency[%s], product[%s], amount[%s], quantity[%s]";
            final String message = String.format(template, quantity, product, amount, quantity);
            throw new IllegalArgumentException(message);
        }

        this.quantity = quantity;
        this.product = product;
        this.amount = amount;
        this.minQty = minQty;
    }

    public boolean appliesToQty(long qty) {
        return qty >= minQty;
    }

    public long getQuantity() {
        return quantity;
    }

    public long getMinQty() {
        return minQty;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Price{");
        sb.append("quantity=").append(quantity);
        sb.append(", minQty=").append(minQty);
        sb.append(", product=").append(product);
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }

    /**
     * two prices are equal if they have the same
     * quantity and the same product and the same "minQty"
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (minQty != price.minQty) return false;
        if (quantity != price.quantity) return false;
        if (!product.equals(price.product)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (quantity ^ (quantity >>> 32));
        result = 31 * result + (int) (minQty ^ (minQty >>> 32));
        result = 31 * result + product.hashCode();
        return result;
    }

}
