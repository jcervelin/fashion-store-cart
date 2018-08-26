package io.jcervelin.fashionstore.cart.gateways;

import io.jcervelin.fashionstore.cart.domains.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product,String> {
    Optional<Collection<Product>> findAllByNameIn(Collection<String> productNames);

}
