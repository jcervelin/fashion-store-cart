package io.jcervelin.fashionstore.cart.usecases.campaigns;

import io.jcervelin.fashionstore.cart.domains.CampaignAttributes;
import io.jcervelin.fashionstore.cart.domains.CartResponse;
import io.jcervelin.fashionstore.cart.domains.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Campaign of BuyXProductsPerYDiscountInAnotherProduct
 * Given a list of impacted products [attributes.getProductsAffected()],
 * if there is [attributes.getBuyX()] or more products in the cart
 * you win [attributes.getPercentFactor()]% of discount in the product
 * [attributes.getPromotionalProduct()]
 * Ex.: Buying 2 shirts you have 50% of discount in a Tie.
 * Buying 4 shirts you have 50% of discount in 2 Ties
 */
@Component(value="BuyXProductsPerYDiscountInAnotherProduct")
@RequiredArgsConstructor
public class BuyXProductsPerYDiscountInAnotherProduct implements Campaign {
    @Override
    public CartResponse execute(CartResponse cartResponse, CampaignAttributes attributes) {
        final Integer amountOfProduct = attributes.getBuyX();
        final Integer percent = attributes.getPercentFactor();
        final List<Product> productsAffected = attributes.getProductsAffected();
        final List<Product> productsAffectedIHave = cartResponse.getProducts()
                .stream()
                .filter(productsAffected::contains)
                .collect(toList());

        final Product promotionalProduct = attributes.getPromotionalProduct();
        final Integer howManyProductsWithDiscount = productsAffectedIHave.size() / amountOfProduct;
        final List<Product> promotionalProductIHave = cartResponse.getProducts().stream().filter(promotionalProduct::equals).collect(toList());

        if (howManyProductsWithDiscount > 0 && promotionalProductIHave.size() > 0) {

            double discountByPercent;

            if (promotionalProductIHave.size() > howManyProductsWithDiscount) {
                discountByPercent = getDiscountByPercent(promotionalProductIHave.get(0).getPrice(),howManyProductsWithDiscount,percent);
            } else {
                discountByPercent = getDiscountByPercent(promotionalProductIHave.get(0).getPrice(),promotionalProductIHave.size(),percent);
            }

            cartResponse.setTotal(cartResponse.getTotal() - discountByPercent);
            cartResponse.getOffers().add(String.format(DISCOUNT_FORMAT,promotionalProduct.getName(),percent,discountByPercent));
        }

        return cartResponse;
    }

}
