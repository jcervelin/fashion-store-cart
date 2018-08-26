package io.jcervelin.fashionstore.cart.usecases.campaigns;

import io.jcervelin.fashionstore.cart.config.UnitTestingSupport;
import io.jcervelin.fashionstore.cart.domains.CampaignAttributes;
import io.jcervelin.fashionstore.cart.domains.CartResponse;
import io.jcervelin.fashionstore.cart.domains.Product;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Arrays;

public class BuyXProductsPerYDiscountInAnotherProductTest extends UnitTestingSupport {

    @InjectMocks
    private BuyXProductsPerYDiscountInAnotherProduct buyX;

    @Test
    public void executeShouldReturnCartWithvalidDiscount() {
        // GIVEN
        CampaignAttributes attrValid = CampaignAttributes.builder()
                .buyX(2)
                .percentFactor(50)
                .productsAffected(Arrays.asList(Product
                        .builder()
                        .sku("2")
                        .name("Shirt")
                        .price(12.50)
                        .build()))
                .expires(LocalDateTime.of(2018,10,10,10,10))
                .starts(LocalDateTime.of(2017,10,10,10,10))
                .promotionalProduct(Product
                        .builder()
                        .sku("3")
                        .name("Tie")
                        .price(9.50)
                        .build())
                .campaignType("BuyXProductsPerYDiscountInAnotherProduct")
                .build();

        final Product shirt1 = Product
                .builder()
                .sku("2")
                .name("Shirt")
                .price(12.50)
                .build();
        final Product shirt2 = Product.builder().build();
        final Product tie = Product
                .builder()
                .sku("3")
                .name("Tie")
                .price(9.50)
                .build();
        BeanUtils.copyProperties(shirt1,shirt2);
        final CartResponse cartResponse = CartResponse
                .builder()
                .products(Arrays.asList(shirt1,shirt2,tie))
                .total(106.50)
                .subTotal(106.50)
                .build();

        // WHEN
        final CartResponse result = buyX.execute(cartResponse, attrValid);

        // THEN
        Assertions.assertThat(result.getTotal()).isEqualTo(101.75);
        Assertions.assertThat(result.getSubTotal()).isEqualTo(106.5);
        Assertions.assertThat(result.getOffers().stream().findFirst().get()).isEqualTo("Tie 50% off: -£4,75");
    }

    @Test
    public void executeShouldReturnTake50PercentDiscountIn2Ties() {
        // GIVEN
        CampaignAttributes attrValid = CampaignAttributes.builder()
                .buyX(2)
                .percentFactor(50)
                .productsAffected(Arrays.asList(Product
                        .builder()
                        .sku("2")
                        .name("Shirt")
                        .price(12.50)
                        .build()))
                .expires(LocalDateTime.of(2018,10,10,10,10))
                .starts(LocalDateTime.of(2017,10,10,10,10))
                .promotionalProduct(Product
                        .builder()
                        .sku("3")
                        .name("Tie")
                        .price(9.50)
                        .build())
                .campaignType("Discount")
                .build();

        final Product shirt1 = Product
                .builder()
                .sku("2")
                .name("Shirt")
                .price(12.50)
                .build();
        final Product shirt2 = Product.builder().build();
        final Product shirt3 = Product.builder().build();
        final Product shirt4 = Product.builder().build();
        final Product tie1 = Product
                .builder()
                .sku("3")
                .name("Tie")
                .price(9.50)
                .build();
        final Product tie2 = Product.builder().build();
        BeanUtils.copyProperties(tie1,tie2);
        BeanUtils.copyProperties(shirt1,shirt2);
        BeanUtils.copyProperties(shirt1,shirt3);
        BeanUtils.copyProperties(shirt1,shirt4);
        final CartResponse cartResponse = CartResponse
                .builder()
                .products(Arrays.asList(shirt1,shirt2,shirt3,shirt4,tie1,tie2))
                .total(106.50)
                .subTotal(106.50)
                .build();

        // WHEN
        final CartResponse result = buyX.execute(cartResponse, attrValid);

        // THEN
        Assertions.assertThat(result.getTotal()).isEqualTo(97.0);
        Assertions.assertThat(result.getSubTotal()).isEqualTo(106.5);
        Assertions.assertThat(result.getOffers().stream().findFirst().get()).isEqualTo("Tie 50% off: -£9,50");
    }

}