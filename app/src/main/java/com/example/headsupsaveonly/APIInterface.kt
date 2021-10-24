package com.example.headsupsaveonly

import retrofit2.http.*
import retrofit2.Call

interface APIInterface {
    @GET("/celebrities/")
    fun getCelebrity(): Call<ArrayList<CelebrityDetails.Celebrity>>

    @POST("/celebrities/")
    fun addCelebrity(@Body userData: CelebrityDetails.Celebrity): Call<CelebrityDetails.Celebrity>

    @PUT("/celebrities/{id}")
    fun updateCelebrity(@Path("id") id : Int, @Body userData: CelebrityDetails.Celebrity): Call<CelebrityDetails.Celebrity>

    @DELETE("/celebrities/{id}")
    fun deleteCelebrity(@Path("id") id : Int ): Call<Void>
}