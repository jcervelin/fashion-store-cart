package io.jcervelin.fashionstore.cart;

import io.jcervelin.fashionstore.cart.domains.CampaignAttributes;
import io.jcervelin.fashionstore.cart.domains.CartResponse;
import io.jcervelin.fashionstore.cart.domains.Product;
import io.jcervelin.fashionstore.cart.usecases.BuyGoodsByCommandLine;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
public class FashionStoreCartApplication  implements ApplicationRunner {

    @Autowired
    private final MongoTemplate mongoTemplate;
    @Autowired
    private final BuyGoodsByCommandLine convertCommandLineToProductList;

    public static void main(String[] args) {
        SpringApplication.run(FashionStoreCartApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        mongoTemplate.save(Product
            .builder()
            .sku("1")
            .name("Trousers")
            .price(35.50)
            .build());
        mongoTemplate.save(Product
            .builder()
            .sku("2")
            .name("Jacket")
            .price(49.90)
            .build());
        mongoTemplate.save(Product
            .builder()
            .sku("3")
            .name("Shirt")
            .price(12.50)
            .build());
        mongoTemplate.save(Product
            .builder()
            .sku("4")
            .name("Tie")
            .price(9.50)
            .build());
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
        mongoTemplate.save(attrInvalid);
        mongoTemplate.save(attrValid);
        final CartResponse cartResponse = convertCommandLineToProductList.convert(args.getNonOptionArgs());

        System.out.printf("Subtotal: £%.2f\n",cartResponse.getSubTotal());
        cartResponse.getOffers().forEach(System.out::println);
        System.out.printf("Total: £%.2f\n",cartResponse.getTotal());
    }
}