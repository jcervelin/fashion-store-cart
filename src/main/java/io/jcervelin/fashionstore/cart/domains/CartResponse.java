package io.jcervelin.fashionstore.cart.domains;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class CartResponse {
    private Collection<Product> products;
    private Double total;

}
