package com.aline.android.news.rest

import com.aline.android.news.models.Locale
import io.reactivex.Observable
import org.intellij.lang.annotations.Language
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v2/top-headlines")
    fun getTopNewsLocale(@Query("country") country : String)
            : Observable<Locale>

    @GET("/v2/top-headlines")
    fun getTopNewsOptions(@Query("country") country : String,
                          @Query("category") category : String)
            : Observable<Locale>

    @GET("/v2/everything")
    fun getSearchNews(@Query ("q") searchTextUser : String,
                      @Query("language") language: String,
                      @Query ("sortBy") publishedAt : String)
            : Observable<Locale>


}