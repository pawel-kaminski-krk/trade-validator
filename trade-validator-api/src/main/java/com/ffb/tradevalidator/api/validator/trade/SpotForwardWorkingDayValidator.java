package com.ffb.tradevalidator.api.validator.trade;

import com.ffb.tradevalidator.api.model.Trade;
import com.ffb.tradevalidator.api.model.TradeType;
import com.ffb.tradevalidator.api.validator.BeanValidator;

import java.time.DayOfWeek;
import java.util.Set;

public class SpotForwardWorkingDayValidator extends BeanValidator<Trade>
{
    private static final Set<DayOfWeek> workingDays = Set.of(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
    );

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
        if (!workingDays.contains(given.getValueDate().getDayOfWeek())) {
            errorBuilder.append(given, "valueDate [%s] is required to fall on working day",
                 given.getValueDate());
        }
    }
}
