package com.ffb.tradevalidator.api.validator.trade;

import com.ffb.tradevalidator.api.model.Trade;
import com.ffb.tradevalidator.api.validator.BeanValidator;

import java.util.Set;

public class CustomerValidator extends BeanValidator<Trade>
{
    private final Set<String> supportedCustomers;

    public CustomerValidator(Set<String> supportedCustomers)
    {
        this.supportedCustomers = supportedCustomers;
    }

    @Override
    public boolean canValidate(Object input)
    {
        return (input instanceof Trade);
    }

    @Override
    protected void validate(Trade given, ErrorMessageBuilder errorBuilder)
    {
        if (!supportedCustomers.contains(given.getCustomer())) {
            errorBuilder.append(given, "customer %s is unsupported", given.getCustomer());
        }
    }
}
