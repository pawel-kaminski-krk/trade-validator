package com.ffb.tradevalidator.api.rest.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.ffb.tradevalidator.api.model.Trade;
import com.ffb.tradevalidator.api.model.TradeRequest;

import java.io.IOException;

public class TradeRequestDeserializer extends StdDeserializer<TradeRequest>
{
    private final ObjectMapper mapper;

    public TradeRequestDeserializer(ObjectMapper mapper)
    {
        super((Class<?>) null);
        this.mapper = mapper;
    }

    @Override
    public TradeRequest deserialize(JsonParser p,
                                    DeserializationContext ctxt) throws IOException
    {
        JsonNode node = p.getCodec().readTree(p);
        String payload = node.textValue();
        Trade[] trades = mapper.readValue(payload, Trade[].class);

        return TradeRequest.defaultBuilder()
                .addTrades(trades)
                .build();
    }
}
