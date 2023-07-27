package com.draccoapp.myapplication.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.draccoapp.myapplication.api.credentials.ApiCredentials
import com.draccoapp.myapplication.api.models.Comic
import com.draccoapp.myapplication.api.models.DataState
import com.draccoapp.myapplication.api.request.ApiRequest
import com.draccoapp.myapplication.api.services.comic.ComicService
import com.draccoapp.myapplication.events.Event
import com.draccoapp.myapplication.utils.ApiHelpers
import kotlinx.coroutines.launch

class HQViewModel: ViewModel() {

    val hqDetailsLiveData: LiveData<Comic>
        get() = _hqDetailsLiveData

    private val _hqDetailsLiveData = MutableLiveData<Comic>()

    val hqListLiveData: LiveData<List<Comic>?>
        get() = _hqListLiveData

    private val _hqListLiveData = MutableLiveData<List<Comic>?>()

    val appState: LiveData<DataState>
        get() = _appState

    private val _appState = MutableLiveData<DataState>()

    val navigationToDetailsLiveData
        get() = _navigationToDetailsLiveData

    private val _navigationToDetailsLiveData = MutableLiveData<Event<Unit>>()

    private val comicService = ApiRequest().getService(ComicService::class.java)

    init {
        _appState.postValue(DataState.Loading)
        getHQData()
    }

    fun onHQSelected(position: Int){
        val hqDetails = _hqListLiveData.value?.get(position)
        hqDetails?.let {
            _hqDetailsLiveData.postValue(it)
            _navigationToDetailsLiveData.postValue(Event(Unit))
        }

    }

    private fun getHQData(){
        val timestamp = ApiHelpers.getCurrentTimeStamp()
        val input = "$timestamp${ApiCredentials.privateKey}${ApiCredentials.publicKey}"
        val hash = ApiHelpers.generateMD5Hash(input)

        viewModelScope.launch {
            val response = comicService
                .getComicList(
                    timestamp,
                    ApiCredentials.publicKey,
                    hash,
                    null,
                    null,
                    null,
                    null,
                    100
                )

            if(response.isSuccessful){
                val filteredComicsList = response.body()?.data?.results?.filter { comic ->
                    comic.thumbnail?.path?.contains("image_not_available", ignoreCase = true) != true
                }
                _hqListLiveData.postValue(filteredComicsList)
                response.body()?.data?.results?.get(8)?.let { Log.e("Results", it.images.toString()) }
                _appState.postValue(DataState.Success)
            }else{
                _appState.postValue(DataState.Error)
            }

        }
    }

}