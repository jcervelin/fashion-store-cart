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

public class DiscountTest extends UnitTestingSupport {

    @InjectMocks
    private Discount discount;

    @Test
    public void executeShouldReturnCartWithvalidDiscount() {
        // GIVEN
        CampaignAttributes attrValid = CampaignAttributes.builder()
                .buyX(3)
                .percentFactor(10)
                .productsAffected(Arrays.asList(Product
                        .builder()
                        .sku("1")
                        .name("Trousers")
                        .price(35.50)
                        .build()))
                .expires(LocalDateTime.of(2018,10,10,10,10))
                .starts(LocalDateTime.of(2017,10,10,10,10))
                .campaignType("Discount")
                .build();

        final Product trousers1 = Product
                .builder()
                .sku("1")
                .name("Trousers")
                .price(35.50)
                .build();
        final Product trousers2 = Product.builder().build();
        final Product trousers3 = Product.builder().build();

        BeanUtils.copyProperties(trousers1,trousers2);
        BeanUtils.copyProperties(trousers1,trousers3);
        final CartResponse cartResponse = CartResponse
                .builder()
                .products(Arrays.asList(trousers1,trousers2,trousers3))
                .total(106.50)
                .subTotal(106.50)
                .build();

        // WHEN
        final CartResponse result = discount.execute(cartResponse, attrValid);

        // THEN
        Assertions.assertThat(result.getTotal()).isEqualTo(95.85);
        Assertions.assertThat(result.getSubTotal()).isEqualTo(106.5);
        Assertions.assertThat(result.getOffers().stream().findFirst().get()).isEqualTo("Trousers 10% off: -£10,65");
    }

    @Test
    public void executeShoudReturnCartWithoutDiscounts() {
        // GIVEN
        CampaignAttributes attrInvalid = CampaignAttributes.builder()
                .buyX(3)
                .percentFactor(10)
                .productsAffected(Arrays.asList(Product
                        .builder()
                        .sku("1")
                        .name("Trousers")
                        .price(35.50)
                        .build()))
                .expires(LocalDateTime.of(2017,10,10,10,10))
                .starts(LocalDateTime.of(2016,10,10,10,10))
                .campaignType("Discount")
                .build();

        final Product shirt1 = Product
                .builder()
                .sku("1")
                .name("Shirt")
                .price(35.50)
                .build();
        final Product shirt2 = Product.builder().build();
        final Product shirt3 = Product.builder().build();

        BeanUtils.copyProperties(shirt1,shirt2);
        BeanUtils.copyProperties(shirt1,shirt3);
        final CartResponse cartResponse = CartResponse
                .builder()
                .products(Arrays.asList(shirt1,shirt2,shirt3))
                .total(106.50)
                .subTotal(106.50)
                .build();

        // WHEN
        final CartResponse result = discount.execute(cartResponse, attrInvalid);

        // THEN
        Assertions.assertThat(result.getTotal()).isEqualTo(106.5);
        Assertions.assertThat(result.getSubTotal()).isEqualTo(106.5);
        Assertions.assertThat(result.getOffers()).isEmpty();
    }
}