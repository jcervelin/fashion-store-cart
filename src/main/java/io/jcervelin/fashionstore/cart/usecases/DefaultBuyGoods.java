package io.jcervelin.fashionstore.cart.usecases;

import io.jcervelin.fashionstore.cart.domains.CartResponse;
import io.jcervelin.fashionstore.cart.domains.Product;
import io.jcervelin.fashionstore.cart.domains.exceptions.ContentNotFoundException;
import io.jcervelin.fashionstore.cart.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultBuyGoods extends BuyGoods {
    private final ProductRepository repository;

    /**
     * Given a list of product names this method get a list of these products from the database
     * for each product name it creates a new product with a different reference.
     * If it was the same instance it could not be possible change price from one single product.
     * @param productNames
     * @return CartResponse
     */
    @Override
    public CartResponse execute(final Collection<String> productNames) {
        final Collection<Product> allProductsById = repository.findAllByNameIn(productNames)
                .map(products -> products.size() == 0 ? null : products)
                .orElseThrow(() ->
                        new ContentNotFoundException(
                                String.format("Products: [%s] not found",
                                        CollectionUtils.emptyIfNull(productNames)
                                        .stream()
                                        .map(StringUtils::trimToEmpty)
                                        .filter(StringUtils::isNoneEmpty)
                                        .reduce((s, s2) -> s + ", " + s2).orElse("")
                                )));

        final List<Product> productList = CollectionUtils.emptyIfNull(productNames)
                .stream()
                .flatMap(s -> CollectionUtils.emptyIfNull(allProductsById)
                        .stream()
                        .filter(product -> product.getName().equalsIgnoreCase(s))
                        .map(product -> Product.builder()
                                .sku(product.getSku())
                                .name(product.getName())
                                .price(product.getPrice())
                                .build()
                        )
                ).collect(toList());

        return CartResponse
                .builder()
                .products(productList)
                .total(getTotal(productList))
                .build();
    }
}
