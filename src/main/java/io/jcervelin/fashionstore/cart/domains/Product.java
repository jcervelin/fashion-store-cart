package io.jcervelin.fashionstore.cart.domains;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "products")
@EqualsAndHashCode(exclude = "price")
public class Product {
    @Id
    private String sku;
    private String name;
    private double price;
}
