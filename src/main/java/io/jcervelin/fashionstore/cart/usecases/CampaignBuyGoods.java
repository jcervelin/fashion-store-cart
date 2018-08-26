package io.jcervelin.fashionstore.cart.usecases;

import io.jcervelin.fashionstore.cart.usecases.campaigns.Campaign;
import io.jcervelin.fashionstore.cart.domains.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CampaignBuyGoods extends BuyGoods {

    private final Collection<Campaign> campaigns;

    private final BuyGoods decorated;

    @Override
    public CartResponse execute(final Collection<String> productNames) {
        final CartResponse cartResponse = decorated.execute(productNames);
        campaigns.forEach(offer -> offer.execute(cartResponse));
        return cartResponse;
    }

}
