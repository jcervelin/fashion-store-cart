package io.jcervelin.fashionstore.cart.config;

import io.jcervelin.fashionstore.cart.gateways.ProductRepository;
import io.jcervelin.fashionstore.cart.usecases.BuyGoods;
import io.jcervelin.fashionstore.cart.usecases.CampaignBuyGoods;
import io.jcervelin.fashionstore.cart.usecases.DefaultBuyGoods;
import io.jcervelin.fashionstore.cart.usecases.campaigns.Campaign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collection;

@Configuration
public class DecoratorConfig {

    @Bean
    @Primary
    public BuyGoods defaultBuyGoods(final ProductRepository productRepository) {
        return new DefaultBuyGoods(productRepository);
    }

    @Bean
    public BuyGoods campaignBuyGoods(final Collection<Campaign> campaigns, final BuyGoods buyGoods) {
        return new CampaignBuyGoods(campaigns,buyGoods);
    }
}