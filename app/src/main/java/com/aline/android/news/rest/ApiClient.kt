package com.aline.android.news.rest

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit

class ApiClient<T> {

        fun getClient(c: Class<T>): T {
        val builder = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .connectTimeout(240, TimeUnit.SECONDS)
                .readTimeout(240, TimeUnit.SECONDS)
                .writeTimeout(240, TimeUnit.SECONDS)
               // .proxy(Proxy(Proxy.Type.HTTP,  InetSocketAddress("192.168.0.12", 8080)))

        val retrofit = Retrofit.Builder()
                .client(builder.build())
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(c)
    }

}

class AuthInterceptor : Interceptor {

    val LOG : String = ApiClient<ApiService>().javaClass.simpleName

    override fun intercept(chain: Interceptor.Chain?): Response {
        val requestBuilder = chain!!.request().newBuilder()
        requestBuilder.addHeader("Authorization", "2e1ae1bc71c24821b16a6de409d409ae")
        val request = requestBuilder.build()
        val response = chain.proceed(request)
        if(response.code() == 401){
            Log.e(LOG, "Error API KEY")
        }
        return response
    }
}

fun getApiService(): ApiService {
    return ApiClient<ApiService>().getClient(ApiService::class.java)
}
