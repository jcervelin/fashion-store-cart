package io.jcervelin.fashionstore.cart.config;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UnitTestingSupport {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

}