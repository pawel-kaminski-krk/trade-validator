package com.ffb.tradevalidator.api.validator.trade;

import com.ffb.tradevalidator.api.model.CurrencyPair;
import com.ffb.tradevalidator.api.model.Trade;
import com.ffb.tradevalidator.api.model.TradeType;
import com.ffb.tradevalidator.api.validator.BeanValidator.ErrorMessageBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

class CustomerValidatorTest
{
    private final CustomerValidator validator = new CustomerValidator(Set.of("A", "B"));
    private final Trade goodTrade = Trade.defaultBuilder()
            .customer("A")
            .type(TradeType.Forward)
            .currencyPair(CurrencyPair.defaultBuilder()
                                  .leftCurrency("cCC")
                                  .rightCurrency("cDD")
                                  .build())
            .build();

    private final ErrorMessageBuilder builder = Mockito.mock(ErrorMessageBuilder.class);

    @Test
    void shouldAcceptInputWithSupportedCustomers()
    {

        // when
        validator.validate(goodTrade, builder);

        // then
        verifyZeroInteractions(builder);
    }

    @Test
    void shouldComplainAboutInputWithUnsupportedCustomers()
    {
        Trade badTrade = Trade.from(goodTrade)
                .customer("Z")
                .build();
        // when
        validator.validate(badTrade, builder);

        // then
        verify(builder).append("customer %s is unsupported", "Z");
    }
}