package com.example.myroomdemo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myroomdemo.repositories.SubscriberRepository

class SubscriberViewModelFactory(private val subscriberRepository: SubscriberRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SubscriberViewModel::class.java)){
            return SubscriberViewModel(subscriberRepository) as T
        }
        throw IllegalArgumentException("Wrong ViewModelClass")
    }
}