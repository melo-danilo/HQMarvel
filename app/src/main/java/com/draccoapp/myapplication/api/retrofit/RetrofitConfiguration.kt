package com.draccoapp.myapplication.api.retrofit

import android.util.Log
import com.draccoapp.myapplication.api.credentials.ApiCredentials
import com.draccoapp.myapplication.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitConfiguration {

    companion object {

        private lateinit var retrofit: Retrofit
        private val baseUrl = ApiCredentials.baseUrl
        private const val CONNECTION_TIMEOUT = 20 * 1000
//        private lateinit var preferences : Preferences

        private fun getRetrofitInstance(): Retrofit {


            val client =
                OkHttpClient.Builder().addInterceptor { chain ->
                    var newRequest = chain.request().newBuilder()
                        .header("ContentResponse-Type", "application/json")
//                        .header(
//                            "Authorization",
//                            "Bearer ${
//                                preferences.getToken()
//                            }"
//                        )
                        .build()
                    chain.proceed(newRequest)
                }.connectTimeout(
                    CONNECTION_TIMEOUT.toLong(),
                    TimeUnit.MINUTES
                ).readTimeout(1, TimeUnit.MINUTES).build()

            if (!::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
            }

            return retrofit
        }


        fun <S> createService(serviceClass: Class<S>): S {
            return getRetrofitInstance().create(serviceClass)
        }


    }

}