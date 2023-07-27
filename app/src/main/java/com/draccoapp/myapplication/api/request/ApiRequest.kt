package com.draccoapp.myapplication.api.request

import com.draccoapp.myapplication.api.retrofit.RetrofitConfiguration

class ApiRequest {

    fun <S> getService(service: Class<S>): S {
        return RetrofitConfiguration.createService(service)
    }

}