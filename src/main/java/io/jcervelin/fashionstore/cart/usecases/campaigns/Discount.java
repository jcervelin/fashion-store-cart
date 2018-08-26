package io.jcervelin.fashionstore.cart.usecases.campaigns;

import io.jcervelin.fashionstore.cart.domains.CampaignAttributes;
import io.jcervelin.fashionstore.cart.domains.CartResponse;
import io.jcervelin.fashionstore.cart.domains.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Campaign of Discount
 * Given a list of impacted products [attributes.getProductsAffected()]
 * and a percent factor [attributes.getPercentFactor()], a discount is given for each element in that list
 */
@Component(value="Discount")
@RequiredArgsConstructor
public class Discount implements Campaign {

    private static final String DISCOUNT_FORMAT = "%s %d%% off: -£%.2f";

    @Override
    public CartResponse execute(CartResponse cartResponse, CampaignAttributes attributes) {
        Integer x = attributes.getPercentFactor();
        final List<Product> productsAffected = attributes.getProductsAffected();

        final Map<String, List<Product>> productsAffectedGroupedByName = cartResponse.getProducts()
                .stream()
                .filter(productsAffected::contains)
                .collect(Collectors.groupingBy(Product::getName));

        productsAffectedGroupedByName.forEach((productName, products) -> {
            final Double discountByPercent = getDiscountByPercent(products.get(0).getPrice(), products.size(), x);
            cartResponse.setTotal(cartResponse.getTotal() - discountByPercent);
            cartResponse.getOffers().add(String.format(DISCOUNT_FORMAT,productName,x,discountByPercent));
        });

        return cartResponse;
    }

    private Double getDiscountByPercent(Double price, Integer amount, Integer percentage){
        return (price * amount) * (percentage / 100.0);
    }
}
