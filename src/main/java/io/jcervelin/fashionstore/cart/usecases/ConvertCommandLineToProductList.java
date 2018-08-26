package io.jcervelin.fashionstore.cart.usecases;

import io.jcervelin.fashionstore.cart.domains.CartResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ConvertCommandLineToProductList {

    private final BuyGoods buyGoods;

    public CartResponse convert(List<String> actions) {
        if (CollectionUtils.emptyIfNull(actions).size() > 1) {
            return buyGoods.execute(actions);
        }
        return CartResponse.builder()
                .total(0.0)
                .products(new ArrayList<>())
                .build();
    }

}
