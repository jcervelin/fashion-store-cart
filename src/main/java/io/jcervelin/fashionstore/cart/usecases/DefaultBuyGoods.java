package io.jcervelin.fashionstore.cart.usecases;

import io.jcervelin.fashionstore.cart.io.jcervelin.fashionstore.cart.domains.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DefaultBuyGoods extends BuyGoods {
    @Override
    public CartResponse execute(final Collection<String> productNames) {
        return null;
    }
}
