package com.radlance.fooddelivery.domain.core

interface AuthResult {
    class Success(val token: String = "") : AuthResult
    object Error : AuthResult
}