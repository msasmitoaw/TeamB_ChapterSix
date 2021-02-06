package com.suit.team.b.data.remote

import com.suit.team.b.data.model.*
import com.suit.team.b.data.model.BattleResponse
import com.suit.team.b.data.model.RegisterRequest
import com.suit.team.b.data.model.RegisterResponse
import com.suit.team.b.data.model.UsersResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @POST("api/v1/auth/register")
    fun register(@Body registerRequest: RegisterRequest): Single<RegisterResponse>

    @GET("api/v1/users")
    fun getUserData(@Header("Authorization") authorization: String?): Single<UsersResponse>

    @Multipart
    @PUT("api/v1/users")
    fun putUserData(
        @Header("Authorization") authorization: String,
        @Part("username") username: RequestBody,
        @Part("email") email: RequestBody
    ): Single<UsersResponse>

    @Multipart
    @PUT("api/v1/users")
    fun putUserPhoto(
        @Header("Authorization") authorization: String,
        @Part("username") username: RequestBody,
        @Part("email") email: RequestBody,
        @Part photo: MultipartBody.Part
    ): Single<UsersResponse>

    @POST("api/v1/auth/login")
    fun login(@Body loginRequest: LoginRequest): Single<LoginResponse>

    @GET("api/v1/auth/me")
    fun me(@Header("Authorization") authorization: String): Single<MeResponse>

    @GET("api/v1/battle")
    fun getBattle(@Header("Authorization") authorization: String?): Single<BattleResponse>
}
