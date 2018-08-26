package io.jcervelin.fashionstore.cart.usecases.campaigns;

import io.jcervelin.fashionstore.cart.domains.CampaignAttributes;
import io.jcervelin.fashionstore.cart.domains.CartResponse;

public interface Campaign {
    String DISCOUNT_FORMAT = "%s %d%% off: -£%.2f";

    CartResponse execute(CartResponse cartResponse, CampaignAttributes attributes);

    default Double getDiscountByPercent(Double price, Integer amount, Integer percentage){
        return (price * amount) * (percentage / 100.0);
    }}
