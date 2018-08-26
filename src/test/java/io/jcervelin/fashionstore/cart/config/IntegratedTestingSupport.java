package io.jcervelin.fashionstore.cart.config;

import io.jcervelin.fashionstore.cart.domains.CampaignAttributes;
import io.jcervelin.fashionstore.cart.domains.Product;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Arrays;


@RunWith(SpringJUnit4ClassRunner.class)
@DataMongoTest
@ComponentScan(basePackages = {"io.jcervelin.fashionstore.cart"})
public class IntegratedTestingSupport {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void setUp() {
        mongoTemplate
                .getCollectionNames()
                .forEach(mongoTemplate::dropCollection);
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
        mongoTemplate.save(attrValidBuyX);
    }

}
