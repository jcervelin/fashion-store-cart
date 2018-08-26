package io.jcervelin.fashionstore.cart.domains;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ActionType {
    ANATWINE_BASKET("AnatwineBasket"),
    OTHER("Other");

    private final String action;
}
