package com.ffb.tradevalidator.api.rest;

import com.ffb.tradevalidator.api.model.TradeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = "/v1/trades")
public class CustomerResource
{
    private static final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    @RequestMapping(method = POST, path = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createTrades(@Valid @RequestBody TradeRequest request)
    {
        log.trace("Validated the payload");
    }
}
