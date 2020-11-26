/*
 * Software is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied.
 *
 * The Initial Developer of the Original Code is Paweł Kamiński.
 * All Rights Reserved.
 */
package com.ffb.tradevalidator.api.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffb.tradevalidator.TradeApp;
import com.ffb.tradevalidator.api.client.V1TradeClient;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(
        classes = {TradeApp.class},
        webEnvironment = DEFINED_PORT,
        properties = {"server.port=8090"})
@AutoConfigureWebMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class TradeRestApiIT
{
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${server.port:}")
    private int applicationPort;

    @Test
    void shouldValidateInputTrades() throws IOException
    {
        V1TradeClient apiClient = createApiClient(V1TradeClient.class);

        //when
        Response<Void> actual = apiClient
                .createTrades(readInputFrom("/good-input.json"))
                .execute();

        assertThat(actual.isSuccessful()).isTrue();
        assertThat(actual.code()).isEqualTo(202);
    }

    @Test
    void shouldReturn400ForEmptyArray() throws IOException
    {
        V1TradeClient apiClient = createApiClient(V1TradeClient.class);

        // when
        Response<Void> actual = apiClient
                .createTrades("[]")
                .execute();

        assertThat(actual.code()).isEqualTo(400);
        assertThat(actual.errorBody().string()).contains("tradeRequest.trades must not be empty");
    }

    @Test
    void shouldReturn400ForInvalidTrades() throws IOException
    {
        V1TradeClient apiClient = createApiClient(V1TradeClient.class);
        String badInput = readInputFrom("/bad-input.json");
        // when
        Response<Void> actual = apiClient
                .createTrades(badInput)
                .execute();

        assertThat(actual.code()).isEqualTo(400);
        assertThat(actual.errorBody().string()).contains("there are validation errors. See \\nTradeRequest{trades=[Trade{customer=YODA1, type=Spot, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=2020-08-15, excerciseStartDate=null, expiryDate=null, premiumDate=null, deliveryDate=null, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}, Trade{customer=YODA1, type=Spot, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=2020-08-22, excerciseStartDate=null, expiryDate=null, premiumDate=null, deliveryDate=null, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}, Trade{customer=YODA2, type=Forward, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=2020-08-22, excerciseStartDate=null, expiryDate=null, premiumDate=null, deliveryDate=null, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}, Trade{customer=YODA2, type=Forward, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=2020-08-21, excerciseStartDate=null, expiryDate=null, premiumDate=null, deliveryDate=null, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}, Trade{customer=YODA2, type=Forward, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=2020-08-08, excerciseStartDate=null, expiryDate=null, premiumDate=null, deliveryDate=null, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}, Trade{customer=PLUT02, type=Forward, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=2020-08-08, excerciseStartDate=null, expiryDate=null, premiumDate=null, deliveryDate=null, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}, Trade{customer=PLUTO3, type=Forward, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=2020-08-22, excerciseStartDate=null, expiryDate=null, premiumDate=null, deliveryDate=null, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}, Trade{customer=YODA1, type=VanillaOption, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=null, excerciseStartDate=null, expiryDate=2020-08-19, premiumDate=2020-08-12, deliveryDate=2020-08-22, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}, Trade{customer=YODA2, type=VanillaOption, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=null, excerciseStartDate=null, expiryDate=2020-08-21, premiumDate=2020-08-12, deliveryDate=2020-08-22, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}, Trade{customer=YODA1, type=VanillaOption, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=null, excerciseStartDate=null, expiryDate=2020-08-25, premiumDate=2020-08-12, deliveryDate=2020-08-22, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}, Trade{customer=YODA1, type=VanillaOption, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=null, excerciseStartDate=2020-08-12, expiryDate=2020-08-19, premiumDate=2020-08-12, deliveryDate=2020-08-22, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}, Trade{customer=YODA2, type=VanillaOption, tradeOptionStyle=null, tradeDate=null, valueDate=null, excerciseStartDate=2020-08-12, expiryDate=2020-08-21, premiumDate=2020-08-12, deliveryDate=2020-08-22, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}, Trade{customer=YODA1, type=VanillaOption, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=null, excerciseStartDate=2020-08-12, expiryDate=2020-08-25, premiumDate=2020-08-12, deliveryDate=2020-08-22, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}, Trade{customer=YODA1, type=VanillaOption, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=null, excerciseStartDate=2020-08-10, expiryDate=2020-08-19, premiumDate=2020-08-12, deliveryDate=2020-08-22, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}, Trade{customer=PLUTO3, type=VanillaOption, tradeOptionStyle=null, tradeDate=null, valueDate=null, excerciseStartDate=2020-08-10, expiryDate=2020-08-19, premiumDate=2020-08-12, deliveryDate=2020-08-22, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}]}\\n- valueDate [2020-08-15] is required to fall on working day see Trade{customer=YODA1, type=Spot, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=2020-08-15, excerciseStartDate=null, expiryDate=null, premiumDate=null, deliveryDate=null, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}\\n- valueDate [2020-08-22] is required to fall on working day see Trade{customer=YODA1, type=Spot, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=2020-08-22, excerciseStartDate=null, expiryDate=null, premiumDate=null, deliveryDate=null, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}\\n- valueDate [2020-08-22] is required to fall on working day see Trade{customer=YODA2, type=Forward, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=2020-08-22, excerciseStartDate=null, expiryDate=null, premiumDate=null, deliveryDate=null, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}\\n- valueDate cannot be before tradeDate but it is - 2020-08-08 < 2020-08-11 see Trade{customer=YODA2, type=Forward, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=2020-08-08, excerciseStartDate=null, expiryDate=null, premiumDate=null, deliveryDate=null, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}\\n- valueDate [2020-08-08] is required to fall on working day see Trade{customer=YODA2, type=Forward, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=2020-08-08, excerciseStartDate=null, expiryDate=null, premiumDate=null, deliveryDate=null, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}\\n- customer PLUT02 is unsupported see Trade{customer=PLUT02, type=Forward, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=2020-08-08, excerciseStartDate=null, expiryDate=null, premiumDate=null, deliveryDate=null, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}\\n- valueDate cannot be before tradeDate but it is - 2020-08-08 < 2020-08-11 see Trade{customer=PLUT02, type=Forward, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=2020-08-08, excerciseStartDate=null, expiryDate=null, premiumDate=null, deliveryDate=null, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}\\n- valueDate [2020-08-08] is required to fall on working day see Trade{customer=PLUT02, type=Forward, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=2020-08-08, excerciseStartDate=null, expiryDate=null, premiumDate=null, deliveryDate=null, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}\\n- customer PLUTO3 is unsupported see Trade{customer=PLUTO3, type=Forward, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=2020-08-22, excerciseStartDate=null, expiryDate=null, premiumDate=null, deliveryDate=null, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}\\n- valueDate [2020-08-22] is required to fall on working day see Trade{customer=PLUTO3, type=Forward, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=2020-08-22, excerciseStartDate=null, expiryDate=null, premiumDate=null, deliveryDate=null, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}\\n- expiryDate must be before deliveryDate but is not - 2020-08-25 >= 2020-08-22 see Trade{customer=YODA1, type=VanillaOption, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=null, excerciseStartDate=null, expiryDate=2020-08-25, premiumDate=2020-08-12, deliveryDate=2020-08-22, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}\\n- expiryDate must be before deliveryDate but is not - 2020-08-25 >= 2020-08-22 see Trade{customer=YODA1, type=VanillaOption, tradeOptionStyle=null, tradeDate=2020-08-11, valueDate=null, excerciseStartDate=2020-08-12, expiryDate=2020-08-25, premiumDate=2020-08-12, deliveryDate=2020-08-22, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}\\n- customer PLUTO3 is unsupported see Trade{customer=PLUTO3, type=VanillaOption, tradeOptionStyle=null, tradeDate=null, valueDate=null, excerciseStartDate=2020-08-10, expiryDate=2020-08-19, premiumDate=2020-08-12, deliveryDate=2020-08-22, currencyPair=CurrencyPair{leftCurrency=EUR, rightCurrency=USD}}");
    }

    private String readInputFrom(String inputPath)
    {
        try {
            return IOUtils.toString(getClass().getResourceAsStream(inputPath), UTF_8.name());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T createApiClient(Class<T> clientType)
    {
        String baseUrl = getBaseUrl(applicationPort);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
        return retrofit.create(clientType);
    }

    private String getBaseUrl(int applicationPort)
    {
        return "http://localhost:" + applicationPort;
    }
}
    
