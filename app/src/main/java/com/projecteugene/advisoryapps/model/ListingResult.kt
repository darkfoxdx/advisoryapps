package com.projecteugene.advisoryapps.model

/**
 * Created by Eugene Low
 */
data class ListingResult(override val status: ApiStatus,
                         val listing: List<Item>?): ApiResult()