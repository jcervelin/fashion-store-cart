package io.jcervelin.fashionstore.cart.usecases.campaigns;

import io.jcervelin.fashionstore.cart.domains.CampaignAttributes;
import io.jcervelin.fashionstore.cart.domains.CartResponse;

public interface Campaign {
    CartResponse execute(CartResponse cartResponse, CampaignAttributes attributes);
}
