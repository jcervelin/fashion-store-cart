package io.jcervelin.fashionstore.cart.usecases;

import io.jcervelin.fashionstore.cart.domains.CartResponse;
import io.jcervelin.fashionstore.cart.domains.Product;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public abstract class BuyGoods {
    public abstract CartResponse execute(final Collection<String> productNames);

    public Predicate<Product> verifyIfProductExists (final String str) {
        return product -> product.getName().equalsIgnoreCase(str);
    }

    public Double getTotal(final List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .reduce(Double::sum)
                .orElse(0d);
    }

}
