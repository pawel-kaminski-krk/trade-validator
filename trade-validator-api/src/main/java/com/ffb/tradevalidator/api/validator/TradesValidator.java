package com.ffb.tradevalidator.api.validator;

import com.ffb.tradevalidator.api.model.Trade;
import com.ffb.tradevalidator.api.model.TradeRequest;

import java.util.List;

import static java.util.Arrays.asList;

public class TradesValidator extends BeanValidator<TradeRequest>
{
    private final List<BeanValidator<Trade>> validators;

    public TradesValidator(BeanValidator<Trade>... validators)
    {
        this.validators = asList(validators);
    }

    @Override
    public boolean canValidate(Object input)
    {
        return (input instanceof TradeRequest);
    }

    @Override
    protected void validate(TradeRequest given, ErrorMessageBuilder errorBuilder)
    {
        List<Trade> trades = given.getTrades();
        for (Trade trade : trades) {
            for (BeanValidator<Trade> validator : validators) {
                if (validator.canValidate(trade)) {
                    validator.validate(trade, errorBuilder);
                }
            }
        }
    }
}
