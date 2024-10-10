package info.ahmadi.fontwriter.api

import info.ahmadi.fontwriter.model.AssetsApiResponse
import info.ahmadi.fontwriter.model.CheckCodeApiRequest
import info.ahmadi.fontwriter.model.FontApiResponse
import info.ahmadi.fontwriter.model.LoginApiRequest
import info.ahmadi.fontwriter.model.VideoApiResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @POST("/api/login")
    fun loginApi(@Body body: LoginApiRequest): Call<Any>

    @POST("/api/check_code")
    fun checkCode(@Body body: CheckCodeApiRequest): Call<Any>

    @GET("/api/fonts")
    fun getFonts(): Call<FontApiResponse>

    @GET("/api/assets")
    fun getAssets(): Call<AssetsApiResponse>

    @GET("/api/toturials")
    fun getVideo(): Call<VideoApiResponseData>


}
