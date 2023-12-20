package com.ardine.fruturity.data.repositories

import com.ardine.fruturity.data.api.ApiService
import com.ardine.fruturity.data.response.FruitResponse

class Repository private constructor(
    private val apiService: ApiService,
){
    private  val historyFruits = mutableListOf<FruitResponse>()
    private  val bookmarkFruits = mutableListOf<FruitResponse>()

//    init{
//        if (fruits.isEmpty()){
//            FruitsDataSource.dummyFruits.forEach{
//                fruits.add(FruitResponse(dummyFruits)
//            }
//        }
//    }

//    fun getAllFruits(): Flow<List<FruitHistory>> {
//        return flowOf(dummy)
//    }

    suspend fun getBookmarkFruits(): List<FruitResponse> {
        try {
            val response = apiService.getBookmarkedFruits()
            bookmarkFruits.addAll(response)
            return response
        } catch (e: Exception){
            e.printStackTrace()
            throw e
        }
    }

    suspend fun getAllFruits(): List<FruitResponse> {
        try {
            val response = apiService.getFruits()
            historyFruits.addAll(response)
            return response
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    suspend fun getFruitById(id: String): FruitResponse {
        return apiService.getFruitById(id)
    }

//    suspend fun getFruitById(fruitId: String): FruitResponse {
//        try {
//            val response = apiService.getFruitById(fruitId)
//            fruits.addAll(response)
//            return response
//        } catch (e: Exception) {
//            // Handle the exception (e.g., logging, error reporting)
//            e.printStackTrace()
//            throw e
//        }
//    }

//    suspend fun getAllMarkedFruits(): List<FruitResponse> {
//        try {
//            val response = apiService.getFruits()
//            fruits.addAll(response)
//            return response
//        } catch (e: Exception) {
//            // Handle the exception (e.g., logging, error reporting)
//            e.printStackTrace()
//            throw e
//        }
//    }

//    fun updateBookmarkStatus(fruitId: Long) {
//        val index = fruits.indexOfFirst { it.fruits.id == fruitId }
//        if (index >= 0) {
//            val fruitMarked = fruits[index]
//            fruits[index] = fruitMarked.copy(fruits = fruitMarked.fruits.copy(isBookmark = !fruitMarked.fruits.isBookmark))
//        }
//    }

//    fun searchFruits(query: String){
//        return getAllFruits()
//            .map { result ->
//                result.filter {
//                    it.fruits.category.contains(query, ignoreCase = true)
//                }
//            }
//        try {
//            val apiFruits = apiService.getFruits()
//            val fruitHistoryList = apiFruits.map { FruitHistory(it, false) }
//            fruits.addAll(fruitHistoryList)
//        } catch (e: Exception) {
//            // Handle the exception (e.g., logging, error reporting)
//            e.printStackTrace()
//        }
//    }

//    BOOKMARK
//    fun updateFruit(fruitId: Long): Flow<Boolean> {
//        val index = fruits.indexOfFirst { it.fruits.id == fruitId }
//        val result = if (index >= 0) {
//            val fruitMarked = fruits[index]
//            fruits[index] = fruitMarked.copy(fruits = fruitMarked.fruits)
//            true
//        } else {
//            false
//        }
//        return flowOf(result)
//    }
//
//    fun getAddedMarkedFruits(): Flow<List<FruitHistory>>{
//        return getAllFruits()
//            .map{ marked ->
//                marked.filter { mark ->
//                    mark.isBookmark == true
//                }
//            }
//    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(
            apiService: ApiService,
        ): Repository =
            instance ?: synchronized(this) {
                Repository(apiService).apply {
                    instance = this
                }
            }
    }
}