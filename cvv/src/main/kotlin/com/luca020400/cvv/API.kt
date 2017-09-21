package com.luca020400.cvv

import com.luca020400.cvv.models.LoginRequest
import com.luca020400.cvv.models.LoginResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface API {
    @POST("rest/v1/auth/login")
    fun doLogin(@Body loginRequest: LoginRequest): Observable<LoginResponse>
}
