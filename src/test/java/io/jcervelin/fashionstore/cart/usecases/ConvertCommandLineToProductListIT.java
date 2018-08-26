package io.jcervelin.fashionstore.cart.usecases;

import io.jcervelin.fashionstore.cart.config.IntegratedTestingSupport;
import io.jcervelin.fashionstore.cart.domains.CartResponse;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class ConvertCommandLineToProductListIT extends IntegratedTestingSupport {

    @Autowired
    private ConvertCommandLineToProductList target;

    @Test
    public void convertShouldReturnCartWith3ValidProducts() {
        final CartResponse cartResponse = target.convert(Arrays.asList("AnatwineBasket", "Jacket", "Trousers", "Tie"));
        Assertions.assertThat(cartResponse.getProducts().size()).isEqualTo(3);
        Assertions.assertThat(cartResponse.getTotal()).isEqualTo(94.9);
    }

}