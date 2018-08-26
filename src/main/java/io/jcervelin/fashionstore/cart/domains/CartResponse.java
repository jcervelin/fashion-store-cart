package io.jcervelin.fashionstore.cart.domains;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Data
@Builder
public class CartResponse {
    private Collection<Product> products;
    private Double total;
    private double subTotal;
    private Collection<String> offers;

    public Collection<String> getOffers() {
        return Optional.ofNullable(offers)
                .orElseGet(() -> this.offers = new ArrayList<>());
    }

}
