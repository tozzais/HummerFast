package com.xianlv.business.kotlin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    val placeList  = ArrayList<Place>()

    val placeResponse = Transformations.switchMap(searchLiveData){
        Repository.serach(it)
    }

    fun search(query:String){
        searchLiveData.value = query
    }

}