package io.jcervelin.fashionstore.cart.gateways;

import io.jcervelin.fashionstore.cart.config.IntegratedTestingSupport;
import io.jcervelin.fashionstore.cart.domains.CampaignAttributes;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static java.time.LocalDateTime.now;

public class CampaignRepositoryTest extends IntegratedTestingSupport {

    @Autowired
    private CampaignRepository repository;

    @Test
    public void findByStartsGreaterThanLocalDateTimeAndExpiresAfterEqualsLocalDateTime() {
        CampaignAttributes attrValid = CampaignAttributes.builder()
                .buyX(3)
                .toY(2)
                .expires(LocalDateTime.of(2018,10,10,10,10))
                .starts(LocalDateTime.of(2017,10,10,10,10))
                .campaignType("BuyXToY")
                .build();
        CampaignAttributes attrInvalid = CampaignAttributes.builder()
                .buyX(3)
                .toY(2)
                .expires(LocalDateTime.of(2017,10,10,10,10))
                .starts(LocalDateTime.of(2016,10,10,10,10))
                .campaignType("BuyXToY")
                .build();

        repository.save(attrValid);
        repository.save(attrInvalid);

        final Optional<Collection<CampaignAttributes>> result
                = repository.findValidCampaigns(now());
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.get()).containsOnly(attrValid);

    }
}