package com.ffb.tradevalidator.api.model;

import org.immutables.value.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value.Immutable
public interface TradeRequest
{
    static ImmutableTradeRequest.Builder defaultBuilder()
    {
        return ImmutableTradeRequest.builder();
    }

    @NotNull
    @NotEmpty
    @Valid
    List<Trade> getTrades();
}
