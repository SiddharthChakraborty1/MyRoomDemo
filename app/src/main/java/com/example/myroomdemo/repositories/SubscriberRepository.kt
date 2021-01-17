package com.example.myroomdemo.repositories

import com.example.myroomdemo.database.Subscriber
import com.example.myroomdemo.database.SubscriberDAO

class SubscriberRepository(private val subscriberDAO: SubscriberDAO) {

    val subscribers = subscriberDAO.getAllSubscribers()

    suspend fun insert(subscriber: Subscriber): Long{
        return subscriberDAO.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber): Int{
        return subscriberDAO.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber): Int{
        return subscriberDAO.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(): Int{
        return subscriberDAO.deleteAllSubscribers()
    }
}