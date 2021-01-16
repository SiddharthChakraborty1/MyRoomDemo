package com.example.myroomdemo.viewmodels

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.example.myroomdemo.Event
import com.example.myroomdemo.database.Subscriber
import com.example.myroomdemo.repositories.SubscriberRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class SubscriberViewModel(private val subscriberRepository: SubscriberRepository): ViewModel(),
    Observable {

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
    get() = statusMessage

    val subscribers = subscriberRepository.subscribers

    var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete : Subscriber

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    fun clearAllOrDelete(){
        if(isUpdateOrDelete)
        {
            delete(subscriberToUpdateOrDelete)
            inputEmail.value = null
            inputName.value = null
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            isUpdateOrDelete = false

        }
        else
        {
            deleteAll()
        }
    }

    fun saveOrUpdate() {
        if(isUpdateOrDelete){
            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!
            update(subscriberToUpdateOrDelete)
            inputName.value = null
            inputEmail.value = null
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            isUpdateOrDelete = false
        }
        else{
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(Subscriber(0,name,email))
        inputEmail.value = null
        inputName.value = null
        }

    }


    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun insert(subscriber: Subscriber): Job = viewModelScope.launch {
        subscriberRepository.insert(subscriber)
        statusMessage.value = Event("Subscriber inserted successfully!")
    }

    fun update(subscriber: Subscriber): Job = viewModelScope.launch {
        subscriberRepository.update(subscriber)
        statusMessage.value = Event("Subscriber updated successfully!")
    }

    fun delete(subscriber: Subscriber): Job = viewModelScope.launch {
        subscriberRepository.delete(subscriber)
        statusMessage.value = Event("Subscriber deleted successfully!")

    }

    fun deleteAll(): Job = viewModelScope.launch {
        subscriberRepository.deleteAll()
        statusMessage.value = Event("All subscriber deleted successfully!")
    }

    fun initUpdateOrDelete(subscriber: Subscriber){
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }



}




