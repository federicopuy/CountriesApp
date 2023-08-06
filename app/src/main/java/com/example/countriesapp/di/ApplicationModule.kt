package com.example.countriesapp.di

import android.content.Context
import androidx.room.Room
import com.example.countriesapp.data.CountriesRepository
import com.example.countriesapp.data.network.BASE_URL
import com.example.countriesapp.data.network.NetworkApiDataSource
import com.example.countriesapp.data.network.RetrofitCountriesApi
import com.example.countriesapp.data.persistence.CountryDao
import com.example.countriesapp.data.persistence.CountryDatabase
import com.example.countriesapp.data.persistence.RoomDBDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideRetrofitService(json: Json, okHttpClient: OkHttpClient): RetrofitCountriesApi {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
            .create(RetrofitCountriesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder().addNetworkInterceptor(httpLoggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json { ignoreUnknownKeys = true }
    }


    @Provides
    @Singleton
    fun provideNetworkApiDataSource(
        retrofitCountriesApi: RetrofitCountriesApi,
    ): NetworkApiDataSource {
        return NetworkApiDataSource(retrofitCountriesApi)
    }

    @Provides
    @Singleton
    fun provideRoomDbDataSource(
        countryDao: CountryDao,
    ): RoomDBDataSource {
        return RoomDBDataSource(countryDao)
    }

    @Provides
    @Singleton
    fun providesCountryDao(countryDatabase: CountryDatabase): CountryDao {
        return countryDatabase.countryDao()
    }

    @Provides
    @Singleton
    fun provideCountryDatabase(@ApplicationContext appContext: Context): CountryDatabase {
        return Room.databaseBuilder(
            appContext,
            CountryDatabase::class.java,
            "country_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCountriesRepository(
        networkApiDataSource: NetworkApiDataSource,
        roomDBDataSource: RoomDBDataSource
    ): CountriesRepository {
        return CountriesRepository(networkApiDataSource, roomDBDataSource)
    }

}