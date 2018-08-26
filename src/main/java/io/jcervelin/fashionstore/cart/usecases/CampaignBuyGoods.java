package io.jcervelin.fashionstore.cart.usecases;

import io.jcervelin.fashionstore.cart.domains.CampaignAttributes;
import io.jcervelin.fashionstore.cart.gateways.CampaignRepository;
import io.jcervelin.fashionstore.cart.usecases.campaigns.Campaign;
import io.jcervelin.fashionstore.cart.domains.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class CampaignBuyGoods extends BuyGoods {

    private final BuyGoods decorated;

    private final CampaignRepository repository;

    private final ApplicationContext applicationContext;

    @Override
    public CartResponse execute(final Collection<String> productNames) {
        final CartResponse cartResponse = decorated.execute(productNames);

        final Optional<Collection<CampaignAttributes>> validCampaigns = repository.findValidCampaigns(now());

        validCampaigns
            .ifPresent(campaignAttributes -> campaignAttributes.forEach(attributes ->
                    applicationContext.getBean(attributes.getCampaignType(),Campaign.class)
                            .execute(cartResponse,attributes)
            ));

        return cartResponse;
    }

}
