package io.jcervelin.fashionstore.cart.usecases;

import io.jcervelin.fashionstore.cart.domains.ActionType;
import io.jcervelin.fashionstore.cart.domains.CartResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BuyGoodsByCommandLine {

    @Qualifier(value="defaultBuyGoods")
    private final BuyGoods buyGoods;

    /**
     * Aways the first parameter of the arguments is an action, or a department
     * int this example is always [AnatwineBasket]. To add another action, consider
     * add it io.jcervelin.fashionstore.cart.domains.ActionType enum
     * @param actions
     * @return
     */
    public CartResponse convert(List<String> actions) {
        if (CollectionUtils.emptyIfNull(actions).size() > 1
            && ActionType.getActionTypes()
                .stream()
                .anyMatch(actionType -> actionType.getAction().equalsIgnoreCase(actions.get(0)))
        )
            return buyGoods.execute(actions.subList(1,actions.size()));

        return CartResponse.builder()
        .products(new ArrayList<>())
        .total(0.0)
        .build();
    }

}
