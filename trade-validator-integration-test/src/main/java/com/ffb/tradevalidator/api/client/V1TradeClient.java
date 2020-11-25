/*
 * Software is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied.
 *
 * The Initial Developer of the Original Code is Paweł Kamiński.
 * All Rights Reserved.
 */
package com.ffb.tradevalidator.api.client;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface V1TradeClient
{
    @POST("v1/trades/")
    @Headers("Content-Type: application/json")
    Call<Void> createTrades(@Body String request);
}
