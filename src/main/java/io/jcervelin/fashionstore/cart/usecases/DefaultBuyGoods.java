package io.jcervelin.fashionstore.cart.usecases;

import io.jcervelin.fashionstore.cart.domains.CartResponse;
import io.jcervelin.fashionstore.cart.domains.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class DefaultBuyGoods extends BuyGoods {
    @Override
    public CartResponse execute(final Collection<String> productNames) {
        final CartResponse cartResponse = CartResponse
                .builder()
                .products(new HashSet<>())
                .total(35.50)
                .build();
        cartResponse.getProducts().add(Product
                .builder()
                .name("Trousers")
                .price(35.50)
                .build());
        return cartResponse;
    }
}
