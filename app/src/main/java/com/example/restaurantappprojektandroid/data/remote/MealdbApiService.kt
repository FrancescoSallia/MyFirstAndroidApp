package com.example.restaurantappprojektandroid.data.remote

import com.example.restaurantappprojektandroid.data.model.CategorieResponse
import com.example.restaurantappprojektandroid.data.model.MealResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

private val logger = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
private val client = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface MealdbApiService {
    @GET("categories.php")
    suspend fun getCategories(): CategorieResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") categorieName: String): MealResponse

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") mealId: String): MealResponse

    @GET("search.php")
    suspend fun searchMeal(@Query("s") mealName: String): MealResponse
}

object MealdbApi {
    val retrofitService: MealdbApiService by lazy { retrofit.create(MealdbApiService::class.java) }
}