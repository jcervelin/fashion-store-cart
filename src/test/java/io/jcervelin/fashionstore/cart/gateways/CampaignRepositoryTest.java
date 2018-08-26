package io.jcervelin.fashionstore.cart.gateways;

import io.jcervelin.fashionstore.cart.config.IntegratedTestingSupport;
import io.jcervelin.fashionstore.cart.domains.CampaignAttributes;
import io.jcervelin.fashionstore.cart.domains.Product;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import static java.time.LocalDateTime.now;

public class CampaignRepositoryTest extends IntegratedTestingSupport {

    @Autowired
    private CampaignRepository repository;

    @Test
    public void findByStartsGreaterThanLocalDateTimeAndExpiresAfterEqualsLocalDateTime() {
        CampaignAttributes attrValid = CampaignAttributes.builder()
                .buyX(3)
                .percentFactor(10)
                .productsAffected(Arrays.asList(Product
                        .builder()
                        .sku("1")
                        .name("Trousers")
                        .price(35.50)
                        .build()))
                .expires(LocalDateTime.of(2030,10,10,10,10))
                .starts(LocalDateTime.of(2017,10,10,10,10))
                .campaignType("Discount")
                .build();
        CampaignAttributes attrValidBuyX = CampaignAttributes.builder()
                .buyX(2)
                .percentFactor(50)
                .productsAffected(Arrays.asList(Product
                        .builder()
                        .sku("3")
                        .name("Shirt")
                        .price(12.50)
                        .build()))
                .expires(LocalDateTime.of(2030,10,10,10,10))
                .starts(LocalDateTime.of(2017,10,10,10,10))
                .campaignType("BuyXProductsPerYDiscountInAnotherProduct")
                .promotionalProduct(Product
                        .builder()
                        .sku("4")
                        .name("Tie")
                        .price(9.50)
                        .build())
                .build();

        final Collection<CampaignAttributes> result
                = repository.findValidCampaigns(now()).get();

        Assertions.assertThat(result).isNotNull();
        final CampaignAttributes discount = result.stream().filter(attributes -> attrValid.getCampaignType().equalsIgnoreCase(attributes.getCampaignType())).findFirst().get();
        final CampaignAttributes buyX = result.stream().filter(attributes -> attrValidBuyX.getCampaignType().equalsIgnoreCase(attributes.getCampaignType())).findFirst().get();

        Assertions.assertThat(discount).isEqualToIgnoringGivenFields(attrValid, "id");
        Assertions.assertThat(buyX).isEqualToIgnoringGivenFields(attrValidBuyX, "id");

    }
}