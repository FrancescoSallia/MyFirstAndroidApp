package com.example.restaurantappprojektandroid.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantappprojektandroid.data.model.Category
import com.example.restaurantappprojektandroid.data.model.Meal

class Repository(private val api: MealdbApi) {


    private val _categorie = MutableLiveData<List<Category>>()
    val category: LiveData<List<Category>>
        get() = _categorie

    private val _meals = MutableLiveData<List<Meal>>()
    val meals : LiveData<List<Meal>>
        get() = _meals

    private val _mealDetail = MutableLiveData<List<Meal>>()
    val mealDetail: LiveData<List<Meal>>
        get() = _mealDetail

    private val _searchMeal = MutableLiveData<List<Meal>>()
    val searchMeal: LiveData<List<Meal>>
        get() = _searchMeal


    suspend fun getCategories(){
        try {
            val result = MealdbApi.retrofitService.getCategories()
            _categorie.postValue(result.categories)
        } catch (e: Exception) {
            Log.i("INFO","schau im Repository nach bei getCategories")
        }
    }

    suspend fun getMealsByCategory(categorieName: String) {
        try {
            val result = MealdbApi.retrofitService.getMealsByCategory(categorieName)
            _meals.postValue(result.meals)
        } catch (e: Exception) {
            Log.i("INFO", "schau im Repository nach bei getMealsByCategory : $e")
        }
    }

    suspend fun getMealById(mealId: String) {
        try {
            val result = MealdbApi.retrofitService.getMealById(mealId)
            _mealDetail.postValue(result.meals)
        }catch(e: Exception) {
                Log.i("INFO", "schau im Repository nach bei getMealById : $e")
            }
    }


    suspend fun searchMeal(search: String) {
        try {
            val result = MealdbApi.retrofitService.searchMeal(search)
            _searchMeal.postValue(result.meals)
        } catch (e: Exception) {
            Log.i("INFO", "schau im Repository nach bei getMealBySearch : $e")
        }
    }



}