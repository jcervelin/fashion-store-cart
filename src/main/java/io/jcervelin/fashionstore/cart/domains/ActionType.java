package io.jcervelin.fashionstore.cart.domains;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum ActionType {
    ANATWINE_BASKET("AnatwineBasket"),
    OTHER("Other");

    private final String action;

    public static List<ActionType> getActionTypes() {
        return Arrays.asList(ActionType.values());
    }
}
