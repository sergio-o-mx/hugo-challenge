package com.mxrampage.hugochallenge.dashboard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mxrampage.hugochallenge.database.Favorites
import kotlinx.coroutines.launch

class DashboardViewModel @ViewModelInject constructor(
    private val repository: DashboardRepository
) : ViewModel() {
    private val _beersResponse = MutableLiveData<List<Beers>>()
    val beersResponse: LiveData<List<Beers>>
        get() = _beersResponse
    private val _favoritesResponse = MutableLiveData<List<Favorites>>()
    val favoritesResponse: LiveData<List<Favorites>>
        get() = _favoritesResponse
    private val _errorResponse = MutableLiveData<Exception>()
    val errorResponse: LiveData<Exception>
        get() = _errorResponse

    fun getBeersFromNetwork() {
        viewModelScope.launch {
            try {
                val responseFromNetwork = repository.getBeersFromNetwork()
                if (responseFromNetwork.isSuccessful) {
                    responseFromNetwork.body()?.let {
                        _beersResponse.postValue(it)
                    }
                }
            } catch (exception: Exception) {
                _errorResponse.postValue(exception)
            }
        }
    }

    fun searchBeersOnNetwork(beerName: String) {
        viewModelScope.launch {
            try {
                val responseFromNetwork = repository.searchBeersOnNetwork(beerName)
                if (responseFromNetwork.isSuccessful) {
                    responseFromNetwork.body()?.let {
                        _beersResponse.postValue(it)
                    }
                }
            } catch (exception: Exception) {
                _errorResponse.postValue(exception)
            }
        }
    }

    fun getFavoritesFromLocalStorage() {
        viewModelScope.launch {
            try {
                val responseFromLocal = repository.getFavoritesFromLocalStorage()
                _favoritesResponse.postValue(responseFromLocal)
            } catch (exception: Exception) {
                _errorResponse.postValue(exception)
            }
        }
    }
}
