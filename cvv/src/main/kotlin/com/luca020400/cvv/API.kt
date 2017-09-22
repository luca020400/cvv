package com.luca020400.cvv

import com.luca020400.cvv.models.Lessons
import com.luca020400.cvv.models.LoginRequest
import com.luca020400.cvv.models.LoginResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface API {
    @POST("rest/v1/auth/login")
    fun doLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("rest/v1/students/{studentId}/lessons/today")
    fun getLessonToday(@Path("studentId") studentId: String): Observable<Lessons>
}
