package com.nytimes.rest

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @Path – variable substitution for the API endpoint. For example movie id will be swapped for{id} in the URL endpoint.
 * @Query – specifies the query key name with the value of the annotated parameter.
 * @Body – payload for the POST call
 * @Header – specifies the header with the value of the annotated parameter
 */

interface ApiInterface {


    /**
     * Available Jobs - JobSeeker
     *
     * @param type
     * @param limit
     * @param page
     * @param authorization
     * @return
     *
     * http://api.nytimes.com/svc/mostpopular/v2/mostviewed/all-sections/7.json
     */
    @GET("svc/mostpopular/v2/mostviewed/all-sections/{type}.json")
    fun getList(@Path("type") type: Int, @Query("api-key") key: String):
            Call<JsonElement>


}
