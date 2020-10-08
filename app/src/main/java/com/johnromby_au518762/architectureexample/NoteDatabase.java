package com.johnromby_au518762.architectureexample;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Note.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    // Singleton
    private static NoteDatabase instance;

    // Used to access our Dao
    public abstract NoteDao noteDao();

    // Get our singleton database
    // Note: Synchronized means only one thread can access this at a time, which prevents multiple instances of the database
    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
