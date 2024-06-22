package com.hci.TP3_HCI.remote.api

import com.hci.TP3_HCI.BuildConfig
import com.hci.TP3_HCI.remote.model.RemoteDevice
import com.google.gson.GsonBuilder
import com.hci.TP3_HCI.api.remote.api.DeviceTypeAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date

private val httpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .build()

private val gson = GsonBuilder()
    .registerTypeAdapter(Date::class.java, DateTypeAdapter())
    .registerTypeAdapter(RemoteDevice::class.java, DeviceTypeAdapter())
    .create()

//TODO baseURL deberia usar BuildConfig.API_BASE_URL pero no anda
private val retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.API_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(okHttpClient)
    .build()

object RetrofitClient {
    val deviceService : DeviceService by lazy {
        retrofit.create(DeviceService::class.java)
    }
}