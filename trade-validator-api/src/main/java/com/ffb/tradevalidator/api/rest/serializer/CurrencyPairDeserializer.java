package com.ffb.tradevalidator.api.rest.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.ffb.tradevalidator.api.model.CurrencyPair;

import java.io.IOException;

public class CurrencyPairDeserializer extends StdDeserializer<CurrencyPair>
{
    public CurrencyPairDeserializer()
    {
        this(null);
    }

    protected CurrencyPairDeserializer(Class<?> vc)
    {
        super(vc);
    }

    @Override
    public CurrencyPair deserialize(JsonParser p,
                                    DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        JsonNode node = p.getCodec().readTree(p);
        String payload = node.textValue();
        return CurrencyPair.defaultBuilder()
                .leftCurrency(payload.substring(0, 3))
                .rightCurrency(payload.substring(3))
                .build();
    }
}
