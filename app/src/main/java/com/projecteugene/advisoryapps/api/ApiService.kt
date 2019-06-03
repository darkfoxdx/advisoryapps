package com.projecteugene.advisoryapps.api

import com.projecteugene.advisoryapps.model.*
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


/**
 * Created by Eugene Low
 */
interface ApiService {

    @POST("login")
    @FormUrlEncoded
    fun login(@Field("email") email: String?,
              @Field("password") password: String?): Observable<LoginResult>

    @GET("listing")
    fun listing(@Query("id") id: String?,
                @Query("token") token: String?): Observable<ListingResult>

    @POST("listing/update")
    @FormUrlEncoded
    fun listingUpdate(@Field("id") id: String?,
                      @Field("token") token: String?,
                      @Field("listing_id") listing_id: String?,
                      @Field("listing_name") listing_name: String?,
                      @Field("distance") distance: String?): Observable<ListingUpdateResult>



    companion object Client {
        private const val BASE_URL = "http://interview.advisoryapps.com/index.php/"
        fun getInstance(): Retrofit {
            return retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }
}