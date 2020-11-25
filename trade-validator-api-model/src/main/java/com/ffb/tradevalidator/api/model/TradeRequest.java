package com.ffb.tradevalidator.api.model;

import org.immutables.value.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value.Immutable
//@JsonDeserialize(builder = ImmutableTradeRequest.Builder.class)
public interface TradeRequest
{
    static ImmutableTradeRequest.Builder defaultBuilder()
    {
        return ImmutableTradeRequest.builder();
    }

    @NotNull
    @NotEmpty
    List<Trade> getTrades();
}
