package com.suit.team.b.data.remote

import com.suit.team.b.data.model.RegisterRequest
import com.suit.team.b.data.model.RegisterResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/v1/auth/register")
    fun register(@Body registerRequest: RegisterRequest): Single<RegisterResponse>
}
