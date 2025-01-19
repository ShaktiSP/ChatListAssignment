package com.example.firebaseassignment.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.firebaseassignment.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    private lateinit var retrofit: Retrofit
    private lateinit var REST_CLIENT: ApiInterface

    fun getApi(): ApiInterface {
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        REST_CLIENT = retrofit.create(ApiInterface::class.java)
        return REST_CLIENT
    }

    companion object{
        @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
        fun getOkHttpClient(): OkHttpClient {

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val builder = OkHttpClient.Builder()
            builder.connectTimeout(5, TimeUnit.MINUTES)
            builder.readTimeout(3, TimeUnit.MINUTES)
            builder.writeTimeout(5, TimeUnit.MINUTES)
            builder.addNetworkInterceptor(httpLoggingInterceptor)
            builder.protocols(listOf(Protocol.HTTP_1_1))

            builder.addInterceptor { chain ->
                val request = chain.request()
                val header = request.newBuilder()?.header("Authorization",
                    "Bearer ${Constants.token}")
                val build = header!!.build()
                chain.proceed(build)
            }
            return builder.build()
        }
    }
}