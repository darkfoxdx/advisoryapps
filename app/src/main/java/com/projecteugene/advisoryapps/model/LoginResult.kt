package com.projecteugene.advisoryapps.model

/**
 * Created by Eugene Low
 */
data class LoginResult(override val status: ApiStatus,
                       val id: String?,
                       val token: String?): ApiResult()