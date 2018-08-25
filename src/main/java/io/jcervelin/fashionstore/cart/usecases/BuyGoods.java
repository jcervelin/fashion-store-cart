package io.jcervelin.fashionstore.cart.usecases;

import io.jcervelin.fashionstore.cart.io.jcervelin.fashionstore.cart.domains.CartResponse;

import java.util.Collection;

public abstract class BuyGoods {
    public abstract CartResponse execute(final Collection<String> productNames);
}
