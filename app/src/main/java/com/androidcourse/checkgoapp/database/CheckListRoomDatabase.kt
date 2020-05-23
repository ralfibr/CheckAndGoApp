package com.androidcourse.checkgoapp.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.androidcourse.checkgoapp.model.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class CheckListRoomDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        private const val DATABASE_NAME = "CHECK_LIST_DATABASE"

        @Volatile
        private var checkListRoomDatabaseInstance: CheckListRoomDatabase? = null

        fun getDatabase(context: Context): CheckListRoomDatabase? {
            if (checkListRoomDatabaseInstance == null) {
                synchronized(CheckListRoomDatabase::class.java) {
                    if (checkListRoomDatabaseInstance == null) {
                        checkListRoomDatabaseInstance =
                            Room.databaseBuilder(context.applicationContext,CheckListRoomDatabase::class.java, DATABASE_NAME).build()
                    }
                }
            }
            return checkListRoomDatabaseInstance
        }
    }

}

