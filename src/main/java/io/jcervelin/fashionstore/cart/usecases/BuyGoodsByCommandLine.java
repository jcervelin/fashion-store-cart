package io.jcervelin.fashionstore.cart.usecases;

import io.jcervelin.fashionstore.cart.domains.ActionType;
import io.jcervelin.fashionstore.cart.domains.CartResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BuyGoodsByCommandLine {

    @Resource(name="defaultBuyGoods")
    private final BuyGoods defaultBuyGoods;

    @Resource(name="campaignBuyGoods")
    private final BuyGoods campaignBuyGoods;

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
        ){
            final CartResponse cartResponse = campaignBuyGoods.execute(actions.subList(1, actions.size()));
            if (CollectionUtils.emptyIfNull(cartResponse.getOffers()).isEmpty()) {
                cartResponse.setOffers(Collections.singletonList("(No offers available)"));
            }
            return cartResponse;
        }

        return CartResponse.builder()
        .products(new ArrayList<>())
        .total(0.0)
        .build();
    }

}
