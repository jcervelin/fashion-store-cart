package io.jcervelin.fashionstore.cart.config;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@DataMongoTest
@ComponentScan(basePackages = {"io.jcervelin.fashionstore.cart"})
public class IntegratedTestingSupport {
}
