package cl.daplay.first;

import static cl.daplay.first.Utils.isBlank;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class Terminal {

    private final Function<String, Optional<Product>> getProductByCode;
    private final BiFunction<Product, Long, BigDecimal> getPriceFor;

    private final List<Product> products;
    private BigDecimal lastKnownTotal;

    public Terminal(Function<String, Optional<Product>> codeToProduct,
                    BiFunction<Product, Long, BigDecimal> getPriceFor) {
        this(codeToProduct, getPriceFor, new LinkedList<>(), BigDecimal.ZERO);
    }

    Terminal(Function<String, Optional<Product>> codeToProduct,
                    BiFunction<Product, Long, BigDecimal> getPriceFor,
                    List<Product> products,
                    BigDecimal lastKnownTotal) {
        this.getProductByCode = codeToProduct;
        this.getPriceFor = getPriceFor;
        this.products = products;
        this.lastKnownTotal = lastKnownTotal;
    }

    public Terminal scan(final String code) throws TerminalException {
        if (isBlank(code)) {
            throw new TerminalException("Product code can't be empty");
        }

        final Optional<Product> optionalProduct = getProductByCode.apply(code);
        if (!optionalProduct.isPresent()) {
            throw new TerminalException(String.format("Unknown Product code[%s]", code));
        }

        this.products.add(optionalProduct.get());
        this.lastKnownTotal = null;

        return this;
    }

    public BigDecimal newTotal() {
        if (null != lastKnownTotal) {
            return lastKnownTotal;
        }

        final Map<Product, Long> amounts = products
                .stream()
                .collect(groupingBy(identity(), counting()));

        final Set<Product> products = amounts.keySet();

        final Map<Product, BigDecimal> subtotal = products
                .stream()
                .collect(toMap(identity(), product -> getPriceFor.apply(product, amounts.get(product))));

        return this.lastKnownTotal = subtotal.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Terminal clean() {
        this.products.clear();
        this.lastKnownTotal = null;
        return this;
    }

}
