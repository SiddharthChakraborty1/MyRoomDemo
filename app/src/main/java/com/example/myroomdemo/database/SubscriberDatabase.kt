package com.example.myroomdemo.database

import android.content.Context
import androidx.room.*
import androidx.room.Room
@Database(entities = [Subscriber::class], version = 1)
abstract class SubscriberDatabase: RoomDatabase() {

   abstract val subscriberDAO: SubscriberDAO
    companion object{
        @Volatile
        private var INSTANCE: SubscriberDatabase? = null
            fun getInstance(context: Context): SubscriberDatabase{
                synchronized(this){
                    var instance = INSTANCE
                    if(instance == null){
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            SubscriberDatabase::class.java,
                            "subscriber_data_database"
                        ).build()
                    }
                    return instance
                }

        }
    }

}
