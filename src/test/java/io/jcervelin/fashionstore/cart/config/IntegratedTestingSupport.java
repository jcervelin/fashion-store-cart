package io.jcervelin.fashionstore.cart.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@DataMongoTest
@RequiredArgsConstructor
public class IntegratedTestingSupport {
    private final MongoTemplate mongoTemplate;
}
