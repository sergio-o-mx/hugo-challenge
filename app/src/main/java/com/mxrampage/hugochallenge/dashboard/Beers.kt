package com.mxrampage.hugochallenge.dashboard

import com.squareup.moshi.Json

@Suppress("SpellCheckingInspection")
data class Beers(
    val id: Long,
    val name: String,
    val tagline: String,
    @Json(name = "first_brewed") val firstBrewed: String,
    val description: String,
    @Json(name = "image_url") val imageURL: String?,
    val abv: Float,
    val ibu: Float?,
    @Json(name = "target_fg") val targetFG: Float,
    @Json(name = "target_og") val targetOG: Float,
    val ebc: Float?,
    val srm: Float?,
    val ph: Float?,
    @Json(name = "attenuation_level") val attenuationLevel: Float,
    val volume: Amount,
    @Json(name = "boil_volume") val boilVolume: Amount,
    val method: Method,
    val ingredients: Ingredients,
    @Json(name = "food_pairing") val foodPairing: List<String>,
    @Json(name = "brewers_tips") val brewersTips: String,
    @Json(name = "contributed_by") val contributedBy: String
) {
    data class Amount(
        val value: Float,
        val unit: String
    )
    data class Method(
        @Json(name =  "mash_temp") val mashTemp: List<Mash>,
        val fermentation: Fermentation,
        val twist: String?
    ) {
        data class Mash(
            val temp: Amount,
            val duration: Int?
        )
        data class Fermentation(
            val temp: Amount
        )
    }
    data class Ingredients(
        val malt: List<Malt>,
        val hops: List<Hops>,
        val yeast: String
    ) {
        data class Malt(
            val name: String,
            val amount: Amount
        )
        data class Hops(
            val name: String,
            val amount: Amount,
            val add: String,
            val attribute: String
        )
    }
}
