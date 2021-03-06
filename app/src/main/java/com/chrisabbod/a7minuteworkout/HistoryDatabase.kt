package com.chrisabbod.a7minuteworkout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version=1)
abstract class HistoryDatabase: RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    //Companion object utilizes Singleton pattern to only have one instance of the database
    companion object {
        //Keep a reference to any DB returned by getInstance. This helps avoid repeatedly initializing the DB which is expensive

        @Volatile   //The value of a volatile variable will never be cached
        private var INSTANCE: HistoryDatabase? = null

        //This function will be thread safe and callers should cache the results of multiple DB calls to avoid overhead
        //This is a Singleton that takes another Singleton as an argument
        fun getInstance(context: Context): HistoryDatabase {
            //Multiple threads can ask for the DB at the same time.
            // We are using this function because only one thread may enter a synchronized block at a time keeping us thread safe
            synchronized(this) {

                var instance = INSTANCE  //Copy current value of instance to a local variable so Kotlin can smartcast

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "history_database"
                    ).fallbackToDestructiveMigration()  //Wipes and rebuilds instead of migrating if no migration object exists
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}