package cl.daplay.first;

import static cl.daplay.first.Utils.isBlank;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;

public final class Store {

    private final Map<Product, SortedSet<Price>> pricing;
    private final Map<String, Product> codesTable;

    public static Store newStore(final Set<Price> prices, final Comparator<Price> pricingStrategy) {
        if (null == prices) {
            throw new IllegalArgumentException("prices can't be null");
        }

        if (null == pricingStrategy) {
            throw new IllegalArgumentException("pricingStrategy can't be null");
        }

        final Supplier<SortedSet<Price>> storePrices = () -> new TreeSet<>(pricingStrategy);

        final Map<Product, SortedSet<Price>> pricing = prices
                .stream()
                .collect(groupingBy(Price::getProduct, toCollection(storePrices)));

        // check that exists a price for one unit of each product
        pricing.forEach((product, productPrices) -> {
            final boolean hasUnitPrice = productPrices
                    .stream()
                    .filter(price -> 1 == price.getQuantity())
                    .findFirst()
                    .isPresent();

            if (!hasUnitPrice) {
                final String template = "A price for one unit of each product is requried, product[%s] failed to meet this criteria";
                throw new IllegalArgumentException(String.format(template, product.getCode()));
            }
        });

        final Map<String, Product> codesTable = pricing
                .keySet()
                .stream()
                .collect(toMap(Product::getCode, identity()));

        return new Store(pricing, codesTable);
    }

    private Store(Map<Product, SortedSet<Price>> pricing,
                  Map<String, Product> codesTable) {
        this.pricing = pricing;
        this.codesTable = codesTable;
    }

    public Terminal newTerminal() {
        return new Terminal(this::getProductByCode, this::getPriceFor);
    }

    public Optional<Product> getProductByCode(final String code) {
        return isBlank(code) ? Optional.empty() : Optional.ofNullable(codesTable.get(code));
    }

    public BigDecimal getPriceFor(Product product, long amount) {
        if (null == product || 0 == amount) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = BigDecimal.ZERO;
        long remaining = amount;

        for (final Price price : pricing.get(product)) {
            // filter the current price to see if it fits for the qty of
            // products being purchased
            if (price.appliesToQty(amount)) {
                long times = remaining / price.getQuantity();
                total = total.add(price.getAmount().multiply(new BigDecimal(times)));

                remaining -= (times * price.getQuantity());
                if (0 == remaining) {
                    break;
                }
            }
        }

        return total;
    }

}
