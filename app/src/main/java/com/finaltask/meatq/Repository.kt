package com.finaltask.meatq

import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

class Repository {

    suspend fun uploadImage(file: File): Response{
        return try{
            ApiService.apiService?.predict(
                image = MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    file.asRequestBody()
                )
            )!!
        }catch (e: IOException){
            e.printStackTrace()
            val response = Response()
            response.predictedLabel = listOf("Failed")
            response
        }catch (e: HttpException){
            e.printStackTrace()
            val response = Response()
            response.predictedLabel = listOf("Failed")
            response
        }catch (e: Exception){
            e.printStackTrace()
            val response = Response()
            response.predictedLabel = listOf("Failed")
            response
        }
    }
}