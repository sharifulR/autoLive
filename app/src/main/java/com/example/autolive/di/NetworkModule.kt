package com.example.autolive.di

import com.example.autolive.api.AuthInterceptor
import com.example.autolive.api.LiveApi
import com.example.autolive.api.UserApi
import com.example.autolive.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun providersRetrofitBuilder():Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(authInterceptor: AuthInterceptor) : OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun providersUserApi(retrofitBuilder: Retrofit.Builder):UserApi{
        return retrofitBuilder.build().create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun providersLiveApi(retrofitBuilder: Retrofit.Builder,okHttpClient: OkHttpClient) :LiveApi{
        return retrofitBuilder
            .client(okHttpClient)
            .build().create(LiveApi::class.java)
    }
}