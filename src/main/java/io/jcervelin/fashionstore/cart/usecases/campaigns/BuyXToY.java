package io.jcervelin.fashionstore.cart.usecases.campaigns;

import io.jcervelin.fashionstore.cart.domains.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuyXToY implements Campaign {

    @Override
    public CartResponse execute(CartResponse cartResponse) {

        return null;
    }
}
