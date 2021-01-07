package com.mxrampage.hugochallenge.detail

import android.text.SpannedString
import android.util.Log
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mxrampage.hugochallenge.dashboard.Beers
import com.mxrampage.hugochallenge.database.Favorites
import kotlinx.coroutines.launch

class DetailsViewModel @ViewModelInject constructor(
    private val repository: DetailsRepository
) : ViewModel() {
    private val _beerDetails = MutableLiveData<List<Beers>>()
    val beerDetails: LiveData<List<Beers>>
        get() = _beerDetails

    fun getBeerDetailsFromNetwork(beerId: Long) {
        viewModelScope.launch {
            try {
                val responseFromNetwork = repository.getBeersDetailsFromNetwork(beerId)
                _beerDetails.postValue(responseFromNetwork.body())
            } catch (exception: Exception) {
                exception.message?.let { Log.e("DetailsViewModel", it) }
            }
        }
    }

    fun createInformationList(beers: Beers): SpannedString {
        val malts = buildSpannedString {
            bold { append("     Malts: ") }
            appendLine()
            for (ingredient in beers.ingredients.malt) {
                append("        ${ingredient.name}: ${ingredient.amount.value} ${ingredient.amount.unit}")
                appendLine()
            }
        }
        val hops = buildSpannedString {
            bold { append("     Hops: ") }
            appendLine()
            for (ingredient in beers.ingredients.hops) {
                append("        ${ingredient.name}: ${ingredient.amount.value} ${ingredient.amount.unit}")
                appendLine()
            }
        }
        return buildSpannedString {
            bold { append(beers.name) }
            appendLine()
            bold { append(beers.tagline) }
            appendLine()
            bold { append("First brewed: ") }
            append(beers.firstBrewed)
            appendLine()
            appendLine(beers.description)
            bold { append("ABV: ") }
            append(beers.abv.toString())
            append(" - ")
            bold { append("IBU: ") }
            append(beers.ibu.toString())
            appendLine()
            bold { append("Ingredients: ") }
            appendLine()
            append(malts)
            append(hops)
            bold { append("     Yeast: ") }
            append(beers.ingredients.yeast)
            appendLine()
            bold { append("Tips: ") }
            append(beers.brewersTips)
        }
    }

    fun markBeerAsFavorite(beer: Beers) {
        val favorite = createFavoriteFromBeer(beer)
        viewModelScope.launch {
            repository.saveFavoriteInLocalStorage(favorite)
        }
    }

    fun removeBeerFromFavorites(beer: Beers) {
        val favorite = createFavoriteFromBeer(beer)
        viewModelScope.launch {
            repository.deleteFavoriteFromLocalStorage(favorite)
        }
    }

    private fun createFavoriteFromBeer(beer: Beers): Favorites {
        var beerPh = 0f
        if (beer.ph != null) {
            beerPh = beer.ph
        }
        var beerImage = ""
        if (beer.imageURL != null) {
            beerImage = beer.imageURL
        }
        return Favorites(
            beer.id,
            beer.name,
            beerImage,
            beerPh
        )
    }
}
