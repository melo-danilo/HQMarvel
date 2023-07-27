package com.draccoapp.myapplication.api.credentials

import com.draccoapp.myapplication.utils.Constants
import com.draccoapp.myapplication.utils.Env

class ApiCredentials {
    companion object {
        const val publicKey = Env.publicKey
        const val privateKey = Env.privateKey
        const val baseUrl = Constants.BASE_URL
    }

}