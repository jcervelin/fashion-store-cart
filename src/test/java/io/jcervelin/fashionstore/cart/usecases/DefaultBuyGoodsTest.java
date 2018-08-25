package io.jcervelin.fashionstore.cart.usecases;

import io.jcervelin.fashionstore.cart.config.UnitTestingSupport;
import io.jcervelin.fashionstore.cart.io.jcervelin.fashionstore.cart.domains.CartResponse;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.*;

public class DefaultBuyGoodsTest extends UnitTestingSupport {

    @InjectMocks
    private DefaultBuyGoods target;

    @Test
    public void executeShouldReturnNull() {
        final CartResponse result = target.execute(null);
        Assertions.assertThat(result).isNull();
    }
}