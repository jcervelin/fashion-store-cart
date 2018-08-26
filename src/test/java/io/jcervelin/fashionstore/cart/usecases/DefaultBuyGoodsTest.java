package io.jcervelin.fashionstore.cart.usecases;

import io.jcervelin.fashionstore.cart.config.UnitTestingSupport;
import io.jcervelin.fashionstore.cart.domains.CartResponse;
import io.jcervelin.fashionstore.cart.domains.Product;
import io.jcervelin.fashionstore.cart.gateways.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.mockito.Mockito.when;

public class DefaultBuyGoodsTest extends UnitTestingSupport {

    @InjectMocks
    private DefaultBuyGoods target;

    @Mock
    private ProductRepository productRepository;

    private List<Product> listOfProducts;

    @Before
    public void setup() {
        listOfProducts = new ArrayList<>();
        listOfProducts.addAll(Arrays.asList(
                Product
                        .builder()
                        .sku("1")
                        .name("Trousers")
                        .price(35.50)
                        .build(),
                Product
                        .builder()
                        .sku("2")
                        .name("Jacket")
                        .price(49.90)
                        .build(),
                Product
                        .builder()
                        .sku("3")
                        .name("Shirt")
                        .price(12.50)
                        .build(),
                Product
                        .builder()
                        .sku("4")
                        .name("Tie")
                        .price(9.50)
                        .build()
        ));
    }

    @Test
    public void executeShouldReturnEmptyCart() {
        // GIVEN
        final CartResponse cartResponse = CartResponse
                .builder()
                .products(new ArrayList<>())
                .total(0.0)
                .build();
        // WHEN
        when(productRepository.findAllByNameIn(null))
                .thenReturn(new ArrayList<>());
        final CartResponse result = target.execute(null);

        // THEN
        Assertions.assertThat(result).isEqualToComparingFieldByField(cartResponse);
    }

    @Test
    public void executeShouldReturnValidCartResponse() {
        // GIVEN
        final CartResponse cartResponse = CartResponse
                .builder()
                .products(listOfProducts)
                .total(35.50)
                .build();

        final Collection<String> productNames = Arrays.asList("Trousers");

        // WHEN
        when(productRepository.findAllByNameIn(productNames))
                .thenReturn(Collections.singletonList(listOfProducts
                        .stream()
                        .filter(product -> product.getName()
                                .equalsIgnoreCase("Trousers"))
                        .findFirst().orElse(null)
                ));

        final CartResponse result = target.execute(productNames);

        // THEN
        Assertions.assertThat(result).isEqualToComparingFieldByField(cartResponse);
    }

    @Test
    public void executeShouldReturnValidCartResponseWith3Trousers() {
        // GIVEN
        final CartResponse cartResponse = CartResponse
                .builder()
                .products(listOfProducts)
                .total(35.50)
                .build();

        final Collection<String> productNames = Arrays.asList("Trousers","Trousers","Trousers");

        // WHEN
        when(productRepository.findAllByNameIn(productNames))
                .thenReturn(Collections.singletonList(listOfProducts
                        .stream()
                        .filter(product -> product.getName()
                                .equalsIgnoreCase("Trousers"))
                        .findFirst().orElse(null)
                ));

        final CartResponse result = target.execute(productNames);

        // THEN
        Assertions.assertThat(result).isEqualToComparingFieldByField(cartResponse);
    }
}