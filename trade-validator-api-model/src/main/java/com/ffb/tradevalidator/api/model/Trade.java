package com.ffb.tradevalidator.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

@Value.Immutable
@JsonDeserialize(builder = ImmutableTrade.Builder.class)
public interface Trade
{
    static ImmutableTrade.Builder defaultBuilder()
    {
        return ImmutableTrade.builder();
    }

    static ImmutableTrade.Builder from(Trade other)
    {
        requireNonNull(other, "Cloning from null Trade is illegal");
        return ImmutableTrade.builder().from(other);
    }

    @NotNull
    public String getCustomer();

    @NotNull
    TradeType getType();

    @Nullable
    TradeOptionStyle getTradeOptionStyle();

    @Nullable
    LocalDate getTradeDate();

    @Nullable
    LocalDate getValueDate();

    @Nullable
    LocalDate getExcerciseStartDate();

    @Nullable
    LocalDate getExpiryDate();

    @Nullable
    LocalDate getPremiumDate();

    @Nullable
    LocalDate getDeliveryDate();

    @NotNull
    @JsonProperty("ccyPair")
    CurrencyPair getCurrencyPair();
}
