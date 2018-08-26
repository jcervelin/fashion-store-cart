package io.jcervelin.fashionstore.cart.config;

import io.jcervelin.fashionstore.cart.gateways.CampaignRepository;
import io.jcervelin.fashionstore.cart.gateways.ProductRepository;
import io.jcervelin.fashionstore.cart.usecases.BuyGoods;
import io.jcervelin.fashionstore.cart.usecases.CampaignBuyGoods;
import io.jcervelin.fashionstore.cart.usecases.DefaultBuyGoods;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class DecoratorConfig {

    @Bean
    @Primary
    public BuyGoods defaultBuyGoods(final ProductRepository productRepository) {
        return new DefaultBuyGoods(productRepository);
    }

    @Bean
    public BuyGoods campaignBuyGoods(final BuyGoods buyGoods, final CampaignRepository campaignRepository, final ApplicationContext applicationContext) {
        return new CampaignBuyGoods(buyGoods, campaignRepository, applicationContext);
    }
}