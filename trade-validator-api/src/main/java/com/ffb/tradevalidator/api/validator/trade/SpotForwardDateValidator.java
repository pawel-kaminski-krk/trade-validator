package com.ffb.tradevalidator.api.validator.trade;

import com.ffb.tradevalidator.api.model.Trade;
import com.ffb.tradevalidator.api.model.TradeType;
import com.ffb.tradevalidator.api.validator.BeanValidator;

public class SpotForwardDateValidator extends BeanValidator<Trade>
{
    @Override
    public boolean canValidate(Object input)
    {
        if (input instanceof Trade) {
            Trade trade = (Trade) input;
            TradeType type = trade.getType();
            return type == TradeType.Spot || type == TradeType.Forward;
        }
        return false;
    }

    @Override
    protected void validate(Trade given, ErrorMessageBuilder errorBuilder)
    {
        if (given.getValueDate() == null) {
            errorBuilder.append(given, "valueDate cannot be null on %s", given.getType());
        }
        if (given.getTradeDate() == null) {
            errorBuilder.append(given, "tradeDate cannot be null on %s", given.getType());
        }
        if (given.getValueDate().isBefore(given.getTradeDate())) {
            errorBuilder.append(given, "valueDate cannot be before tradeDate but it is - %s < %s",
                 given.getValueDate(), given.getTradeDate());
        }
    }
}
