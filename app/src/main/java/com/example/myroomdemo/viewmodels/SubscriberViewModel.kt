package com.example.myroomdemo.viewmodels

import android.util.Patterns
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

        if(inputName.value == null){
            statusMessage.value = Event("Pleas enter subscriber's name!")
        }
        if(inputEmail.value == null){
            statusMessage.value = Event("Please enter subscriber's email!")
        }else if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()){
            statusMessage.value = Event("Subscriber's email is not in the right format!")
        }else {


            if (isUpdateOrDelete) {
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.email = inputEmail.value!!
                update(subscriberToUpdateOrDelete)
                inputName.value = null
                inputEmail.value = null
                saveOrUpdateButtonText.value = "Save"
                clearAllOrDeleteButtonText.value = "Clear All"
                isUpdateOrDelete = false
            } else {
                val name = inputName.value!!
                val email = inputEmail.value!!
                insert(Subscriber(0, name, email))
                inputEmail.value = null
                inputName.value = null
            }
        }

    }


    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun insert(subscriber: Subscriber): Job = viewModelScope.launch {
       val newRowId: Long =  subscriberRepository.insert(subscriber)
        if(newRowId > -1) {
            statusMessage.value = Event("Subscriber inserted successfully! the row id is $newRowId")
        }else{
            statusMessage.value = Event("An error occurred, please try again!")

        }
    }

    fun update(subscriber: Subscriber): Job = viewModelScope.launch {
       val noOfRows = subscriberRepository.update(subscriber)
        if(noOfRows > 0) {
            statusMessage.value = Event("Subscriber updated successfully! no of rows updated are $noOfRows")
            inputEmail.value = null
            inputName.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
        }else {
            statusMessage.value = Event("An error occurred, Please try again!")
        }
    }

    fun delete(subscriber: Subscriber): Job = viewModelScope.launch {
        val noOfRowsDeleted = subscriberRepository.delete(subscriber)
        if(noOfRowsDeleted > 0) {
            statusMessage.value = Event("Subscriber deleted successfully! no of rows deleted: $noOfRowsDeleted")
        }else{
            statusMessage.value = Event("Something went wrong, please try again")
        }

    }

    fun deleteAll(): Job = viewModelScope.launch {
       val noOfRowsDeleted = subscriberRepository.deleteAll()
        if(noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted subscribers deleted successfully!")
        }else{
            statusMessage.value = Event("Something went wrong, please try again")

        }
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




