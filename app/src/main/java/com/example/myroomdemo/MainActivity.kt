package com.example.myroomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myroomdemo.database.Subscriber
import com.example.myroomdemo.database.SubscriberDatabase
import com.example.myroomdemo.repositories.SubscriberRepository
import com.example.myroomdemo.viewmodels.SubscriberViewModel
import com.example.myroomdemo.viewmodels.SubscriberViewModelFactory
import com.example.myroomdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        binding.subscriberViewModel = subscriberViewModel
        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let{
                Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
            }
        })


        initRecyclerView()


    }

    private fun showData(){
        subscriberViewModel.subscribers.observe(this, Observer {
            Log.i("MYTAG", it.toString())
            binding.subscriberRecyclerView.adapter = RecyclerAdapter(it, {selectedItem: Subscriber -> listItemClicked(selectedItem)})

        })


    }

    private fun initRecyclerView(){
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        showData()
    }

    private fun listItemClicked(subscriber: Subscriber){
       // Toast.makeText(this,"The selected subscriber is ${subscriber.name}", Toast.LENGTH_SHORT).show()
        subscriberViewModel.initUpdateOrDelete(subscriber)
        subscriberViewModel.isUpdateOrDelete = true
        subscriberViewModel.saveOrUpdateButtonText.value = "Update"
        subscriberViewModel.clearAllOrDeleteButtonText.value = "Delete"
    }
}