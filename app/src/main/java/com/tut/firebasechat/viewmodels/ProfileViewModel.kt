package com.tut.firebasechat.viewmodels

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.tut.firebasechat.R
import com.tut.firebasechat.models.FirebaseResponse
import com.tut.firebasechat.models.ResponseWrapper
import com.tut.firebasechat.models.User
import com.tut.firebasechat.repositories.AuthRepository
import com.tut.firebasechat.repositories.ProfileRepository

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ProfileRepository
    private var user: User? = null
    private val app = getApplication<Application>()

    fun putProfileDetails(name: String): LiveData<FirebaseResponse> {
        val response: MediatorLiveData<FirebaseResponse> = MediatorLiveData()
        AuthRepository.getFirebaseUser().apply{ if (this != null) user =
            User(name = name,phone =  this.phoneNumber!!) }
        response.addSource(repository.putProfileDetails(user)){ repositoryResponse ->
            if (repositoryResponse == FirebaseResponse.SUCCESS) {
                putProfileCompleted()
            }
            response.value = repositoryResponse
        }
        return response
    }

    fun isProfileExists(): LiveData<ResponseWrapper<Boolean>> {
        val response: MediatorLiveData<ResponseWrapper<Boolean>> = MediatorLiveData()
        response.addSource(repository.isProfileExists()) { responseWrapper ->
            if (responseWrapper.response == FirebaseResponse.SUCCESS &&
                responseWrapper.data == true) putProfileCompleted()
            response.value = responseWrapper
        }
        return response
    }

    fun isProfileCompleted(): Boolean =
            getStore().getBoolean(app.getString(R.string.KEY_PROFILE_UPDATED), false)

    private fun putProfileCompleted() =
            getStore().edit().apply {
                this.putBoolean(app.getString(R.string.KEY_PROFILE_UPDATED), true).apply()
            }

    private fun getStore(): SharedPreferences =
            app.getSharedPreferences(app.getString(R.string.STORE), 0)
}