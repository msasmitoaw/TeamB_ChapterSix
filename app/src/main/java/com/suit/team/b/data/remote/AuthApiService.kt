package com.suit.team.b.data.remote

import com.suit.team.b.data.model.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/v1/auth/register")
    fun register(@Body registerRequest: RegisterRequest): Single<RegisterResponse>

    @POST("api/v1/auth/login")
    fun login(@Body loginRequest: LoginRequest): Single<LoginResponse>

    @GET("api/v1/auth/me")
    fun me(@Header("Authorization") authorization: String): Single<MeResponse>
}
