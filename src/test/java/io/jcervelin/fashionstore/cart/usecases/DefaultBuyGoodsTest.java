package io.jcervelin.fashionstore.cart.usecases;

import io.jcervelin.fashionstore.cart.config.UnitTestingSupport;
import io.jcervelin.fashionstore.cart.domains.CartResponse;
import io.jcervelin.fashionstore.cart.domains.Product;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.HashSet;

public class DefaultBuyGoodsTest extends UnitTestingSupport {

    @InjectMocks
    private DefaultBuyGoods target;

    @Test
    public void executeShouldReturnNull() {
//        final CartResponse result = target.execute(null);
//        Assertions.assertThat(result).isNull();
    }

    @Test
    public void executeShouldReturnValidCartResponse() {
        final CartResponse cartResponse = CartResponse
                .builder()
                .products(new HashSet<>())
                .total(35.50)
                .build();
        cartResponse.getProducts().add(Product
                .builder()
                .name("Trousers")
                .price(35.50)
                .build());

        final CartResponse result = target.execute(null);

        Assertions.assertThat(result).isEqualToComparingFieldByField(cartResponse);
    }
}