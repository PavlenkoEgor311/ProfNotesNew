package com.example.profnotes.core.di

import android.app.Application
import android.content.Context
import com.example.profnotes.BuildConfig
import com.example.profnotes.data.network.Api.NotesApi
import com.example.profnotes.data.network.Api.RegisterApi
import com.example.profnotes.data.network.interceptor.ErrorStatusInterceptor
import com.example.profnotes.data.network.interceptor.NetworkMonitor
import com.example.profnotes.data.network.interceptor.NetworkStatusInterceptor
import com.example.profnotes.data.network.interceptor.PrettyLogger
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class CoroutineScopeIO

    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    @Provides
    fun provideLogger() =
        HttpLoggingInterceptor(PrettyLogger()).setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        logger: HttpLoggingInterceptor,
        networkStatusInterceptor: NetworkStatusInterceptor
    ) =
        OkHttpClient.Builder()
            .readTimeout(80, TimeUnit.SECONDS)
            .writeTimeout(80, TimeUnit.SECONDS)
            .connectTimeout(80, TimeUnit.SECONDS)
            .addInterceptor(networkStatusInterceptor)
            .addInterceptor(ErrorStatusInterceptor(provideMoshi()))
            .addInterceptor(logger.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient, moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.MY_NOTE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun provideNotesApi(retrofit: Retrofit): NotesApi =
        retrofit.create(NotesApi::class.java)

    @Provides
    fun provideRegisterApi(retrofit: Retrofit): RegisterApi =
        retrofit.create(RegisterApi::class.java)

    @Provides
    fun provideNetworkStatusInterceptor(context: Context) =
        NetworkStatusInterceptor(NetworkMonitor(context))

    @Provides
    fun provideApplicationContext(application: Application): Context = application
}