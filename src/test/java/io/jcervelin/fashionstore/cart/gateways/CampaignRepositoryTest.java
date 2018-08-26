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
                .toY(2)
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

        repository.save(attrValid);
        repository.save(attrInvalid);

        final Collection<CampaignAttributes> result
                = repository.findValidCampaigns(now()).get();
        Assertions.assertThat(result).isNotNull();
        result.forEach(attributes ->
                Assertions.assertThat(attributes).isEqualToIgnoringGivenFields(attrValid,"id")
        );


    }
}