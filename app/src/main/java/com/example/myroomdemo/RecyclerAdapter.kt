package com.example.myroomdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.myroomdemo.database.Subscriber
import com.example.myroomdemo.databinding.ActivityMainBinding.bind
import com.example.myroomdemo.databinding.ActivityMainBinding.inflate
import com.example.myroomdemo.databinding.RecyclerViewItemLayoutBinding

class RecyclerAdapter(private val subscribersList: List<Subscriber>, private val clickListener: (Subscriber) -> Unit): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){




    class ViewHolder(val binding: RecyclerViewItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit){
            binding.tvName.text = subscriber.name
            binding.tvEmail.text = subscriber.email
            binding.listItem.setOnClickListener{
                clickListener(subscriber)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RecyclerViewItemLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.recycler_view_item_layout, parent, false)

        return ViewHolder(binding)



    }

    override fun getItemCount(): Int = subscribersList.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(subscribersList[position],clickListener )

    }


}