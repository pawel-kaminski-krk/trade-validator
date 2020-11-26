package com.ffb.tradevalidator.api.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ffb.tradevalidator.api.model.CurrencyPair;
import com.ffb.tradevalidator.api.model.TradeRequest;
import com.ffb.tradevalidator.api.rest.serializer.CurrencyPairDeserializer;
import com.ffb.tradevalidator.api.rest.serializer.TradeRequestDeserializer;
import com.ffb.tradevalidator.api.validator.TradesValidator;
import com.ffb.tradevalidator.api.validator.Validator;
import com.ffb.tradevalidator.api.validator.trade.CustomerValidator;
import com.ffb.tradevalidator.api.validator.trade.OptionDateValidator;
import com.ffb.tradevalidator.api.validator.trade.SpotForwardDateValidator;
import com.ffb.tradevalidator.api.validator.trade.SpotForwardWorkingDayValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class MvcConfiguration
{
    @Bean
    public ObjectMapper objectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
                .configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true)
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                .configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule deserializers = new SimpleModule();
        deserializers.addDeserializer(CurrencyPair.class, new CurrencyPairDeserializer());
        deserializers.addDeserializer(TradeRequest.class, new TradeRequestDeserializer(objectMapper));
        objectMapper.registerModule(deserializers);

        return objectMapper;
    }

    @Bean
    Validator modelValidator()
    {
        return new Validator(
                new TradesValidator(
                    new CustomerValidator(Set.of("YODA1", "YODA2")),
                    new OptionDateValidator(),
                    new SpotForwardDateValidator(),
                    new SpotForwardWorkingDayValidator()
                ));
    }
}
