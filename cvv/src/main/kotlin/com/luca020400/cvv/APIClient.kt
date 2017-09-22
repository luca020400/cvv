package com.luca020400.cvv

import android.content.Context
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    companion object {
        fun create(context: Context): API {
            val zorro = Interceptor { chain ->
                val original = chain.request()

                val request = original.newBuilder()
                        .header("User-Agent", "zorro/1.0")
                        .header("Z-Dev-Apikey", "+zorro+")
                        .method(original.method(), original.body())
                        .build()

                chain.proceed(request)
            }

            val token = Interceptor { chain ->
                val original = chain.request()

                val token = if (TokenStorage().IsTokenValid(context)) {
                    TokenStorage().GetToken(context)
                } else {
                    ""
                }

                val request = original.newBuilder()
                        .header("Z-Auth-Token", token)
                        .method(original.method(), original.body())
                        .build()

                chain.proceed(request)
            }

            val client = OkHttpClient.Builder()
                    .addInterceptor(zorro)
                    .addInterceptor(token)
                    .build()

            val gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
                    .create()

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl("https://web.spaggiari.eu/")
                    .client(client)
                    .build()

            return retrofit.create(API::class.java)
        }
    }
}
