package com.ffb.tradevalidator.api.rest;

import com.ffb.tradevalidator.api.model.TradeRequest;
import com.ffb.tradevalidator.api.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = "/v1/trades")
public class TradeResource
{
    private static final Logger log = LoggerFactory.getLogger(TradeResource.class);

    private final Validator validator;

    @Autowired
    public TradeResource(Validator validator)
    {
        this.validator = validator;
    }

    @RequestMapping(method = POST, path = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createTrades(@Valid @RequestBody TradeRequest request)
    {
        validator.validate(request);
        log.trace("Validated the payload");
    }
}
