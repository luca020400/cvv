package com.luca020400.cvv.models

import java.util.*

data class LoginResponse(
        val ident: String,
        val firstName: String,
        val lastName: String,
        val token: String,
        val release: Date,
        val expire: Date
)
