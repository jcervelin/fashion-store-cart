package io.jcervelin.fashionstore.cart;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FashionStoreCartApplication  implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(FashionStoreCartApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        args.getNonOptionArgs().forEach(System.out::println);
    }
}
