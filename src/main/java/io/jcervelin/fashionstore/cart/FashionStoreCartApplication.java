package io.jcervelin.fashionstore.cart;

import io.jcervelin.fashionstore.cart.domains.Product;
import io.jcervelin.fashionstore.cart.usecases.BuyGoodsByCommandLine;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

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

         convertCommandLineToProductList.convert(args.getNonOptionArgs());

    }
}
