package io.jcervelin.fashionstore.cart.usecases;

import io.jcervelin.fashionstore.cart.config.UnitTestingSupport;
import io.jcervelin.fashionstore.cart.domains.CartResponse;
import io.jcervelin.fashionstore.cart.domains.Product;
import io.jcervelin.fashionstore.cart.gateways.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.*;

public class DefaultBuyGoodsTest extends UnitTestingSupport {

    @InjectMocks
    private DefaultBuyGoods target;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void executeShouldReturnEmptyCart() {
        final CartResponse cartResponse = CartResponse
                .builder()
                .products(new ArrayList<>())
                .total(0.0)
                .build();
        Mockito.when(productRepository.findAllBySku(null)).thenReturn(new ArrayList<>());
        final CartResponse result = target.execute(null);

        Assertions.assertThat(result).isEqualToComparingFieldByField(cartResponse);
    }

    @Test
    public void executeShouldReturnValidCartResponse() {
        final Product trouser = Product
                .builder()
                .sku("1")
                .name("Trousers")
                .price(35.50)
                .build();
        final CartResponse cartResponse = CartResponse
                .builder()
                .products(new ArrayList<>())
                .total(35.50)
                .build();
        cartResponse.getProducts().add(trouser);

        Collection<String> productNames = Arrays.asList("Trousers");

        Mockito.when(productRepository.findAllBySku(productNames)).thenReturn(Collections.singletonList(trouser));

        final CartResponse result = target.execute(productNames);

        Assertions.assertThat(result).isEqualToComparingFieldByField(cartResponse);
    }
}