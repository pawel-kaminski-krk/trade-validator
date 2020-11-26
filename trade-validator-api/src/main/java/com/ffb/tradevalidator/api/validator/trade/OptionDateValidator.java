package com.ffb.tradevalidator.api.validator.trade;

import com.ffb.tradevalidator.api.model.Trade;
import com.ffb.tradevalidator.api.model.TradeOptionStyle;
import com.ffb.tradevalidator.api.model.TradeType;
import com.ffb.tradevalidator.api.validator.BeanValidator;

public class OptionDateValidator extends BeanValidator<Trade>
{
    @Override
    public boolean canValidate(Object input)
    {
        if (input instanceof Trade) {
            Trade trade = (Trade) input;
            TradeType type = trade.getType();
            return type == TradeType.VanillaOption;
        }
        return false;
    }

    @Override
    protected void validate(Trade given, ErrorMessageBuilder errorBuilder)
    {
        if (given.getTradeOptionStyle() == TradeOptionStyle.AMERICAN) {
            if (given.getExcerciseStartDate() == null) {
                errorBuilder.append(given, "excerciseStartDate cannot be null on %s", given.getType());
            }
            if (given.getTradeDate() == null) {
                errorBuilder.append(given, "tradeDate cannot be null on %s", given.getType());
            }
            if (given.getExcerciseStartDate() != null && given.getTradeDate() != null) {
                if (!given.getExcerciseStartDate().isAfter(given.getTradeDate())) {
                    errorBuilder.append(given, "excerciseStartDate must be after tradeDate but is not - %s <= %s",
                                  given.getExcerciseStartDate(), given.getTradeDate());
                }
            }

            if (given.getExpiryDate() == null) {
                errorBuilder.append(given, "expiryDate cannot be null on %s", given.getType());
            }
            if (given.getExcerciseStartDate() != null && given.getExpiryDate() != null) {
                if (!given.getExcerciseStartDate().isBefore(given.getExpiryDate())) {
                    errorBuilder.append(given, "excerciseStartDate must be before expiryDate but is not - %s >= %s",
                                        given.getExcerciseStartDate(), given.getExpiryDate());
                }
            }
        }

        if (given.getPremiumDate() == null) {
            errorBuilder.append(given, "premiumDate cannot be null on %s", given.getType());
        }
        if (given.getExpiryDate() == null) {
            errorBuilder.append(given, "expiryDate cannot be null on %s", given.getType());
        }
        if (given.getDeliveryDate() == null) {
            errorBuilder.append(given, "deliveryDate cannot be null on %s", given.getType());
        }
        if (given.getPremiumDate() != null && given.getDeliveryDate() != null) {
            if (!given.getPremiumDate().isBefore(given.getDeliveryDate())) {
                errorBuilder.append(given, "premiumDate must be before deliveryDate but is not - %s >= %s",
                                    given.getPremiumDate(), given.getDeliveryDate());
            }
        }
        if (given.getExpiryDate() != null && given.getDeliveryDate() != null) {
            if (!given.getExpiryDate().isBefore(given.getDeliveryDate())) {
                errorBuilder.append(given, "expiryDate must be before deliveryDate but is not - %s >= %s",
                                    given.getExpiryDate(), given.getDeliveryDate());
            }
        }
    }
}
