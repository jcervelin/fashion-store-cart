package io.jcervelin.fashionstore.cart.usecases;

import io.jcervelin.fashionstore.cart.domains.CartResponse;
import io.jcervelin.fashionstore.cart.domains.Product;
import io.jcervelin.fashionstore.cart.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultBuyGoods extends BuyGoods {
    private final ProductRepository repository;

    @Override
    public CartResponse execute(final Collection<String> productNames) {
        final Collection<Product> allProductsById = repository.findAllByNameIn(productNames);

        final List<Product> productList = CollectionUtils.emptyIfNull(productNames).stream()
                .filter(s -> allProductsById.stream()
                        .anyMatch(verifyIfProductExists(s)))
                .map(s -> allProductsById
                        .stream()
                        .filter(product -> product.getName().equals(s))
                        .findAny()
                        .orElse(Product.builder().build())
                ).collect(Collectors.toList());

        return CartResponse
                .builder()
                .products(productList)
                .total(getTotal(productList))
                .build();
    }
}
