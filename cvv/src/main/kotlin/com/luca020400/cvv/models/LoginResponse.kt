package com.luca020400.cvv.models

data class LoginResponse(
        val ident: String,
        val firstName: String,
        val lastName: String,
        val token: String,
        val release: String,
        val expire: String
)
