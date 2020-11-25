package com.ffb.tradevalidator.api.model;

import org.immutables.value.Value;

import javax.validation.constraints.NotNull;

@Value.Immutable
public interface CurrencyPair
{
    static ImmutableCurrencyPair.Builder defaultBuilder()
    {
        return ImmutableCurrencyPair.builder();
    }

    @NotNull
    String getLeftCurrency();

    @NotNull
    String getRightCurrency();
}
