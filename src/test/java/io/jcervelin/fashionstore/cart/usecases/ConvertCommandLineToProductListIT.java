package io.jcervelin.fashionstore.cart.usecases;

import io.jcervelin.fashionstore.cart.config.IntegratedTestingSupport;
import io.jcervelin.fashionstore.cart.domains.CartResponse;
import io.jcervelin.fashionstore.cart.domains.exceptions.ContentNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class ConvertCommandLineToProductListIT extends IntegratedTestingSupport {

    @Autowired
    private BuyGoodsByCommandLine target;

    @Test
    public void convertShouldReturnCartWith3ValidProducts() {
        final CartResponse cartResponse = target.convert(Arrays.asList("AnatwineBasket", "Jacket", "Trousers", "Tie"));
        Assertions.assertThat(cartResponse.getProducts().size()).isEqualTo(3);
        Assertions.assertThat(cartResponse.getTotal()).isEqualTo(91.35000000000001);
    }

    @Test
    public void convertShouldReturnError() {
        thrown.expect(ContentNotFoundException.class);
        thrown.expectMessage("Products: [Underwear, Socks] not found");
        target.convert(Arrays.asList("AnatwineBasket", "Underwear", "Socks"));
    }

    @Test
    public void convertShouldWhenAllProductsAreInvalidReturnError() {
        thrown.expect(ContentNotFoundException.class);
        thrown.expectMessage("Products: [] not found");
        target.convert(Arrays.asList("AnatwineBasket", "", ""));
    }

    @Test
    public void convertShouldWhenAllProductsOrWithEmptySpacesAreInvalidReturnError() {
        thrown.expect(ContentNotFoundException.class);
        thrown.expectMessage("Products: [] not found");
        target.convert(Arrays.asList("AnatwineBasket", "  ", ""));
    }

    @Test
    public void convertShouldReturn4ShirtsWithNoOffers() {
        final CartResponse cartResponse = target.convert(Arrays.asList("AnatwineBasket", "Shirt", "Shirt", "Shirt","Shirt"));
        Assertions.assertThat(cartResponse.getProducts().size()).isEqualTo(4);
        Assertions.assertThat(cartResponse.getTotal()).isEqualTo(50.0);
        Assertions.assertThat(cartResponse.getSubTotal()).isEqualTo(50.0);
        Assertions.assertThat(cartResponse.getOffers().stream().findFirst().get()).isEqualTo("(No offers available)");
    }

}