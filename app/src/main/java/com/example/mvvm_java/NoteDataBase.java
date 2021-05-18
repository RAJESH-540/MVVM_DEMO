package com.example.mvvm_java;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Note.class,version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    private static NoteDataBase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDataBase getInstance(Context context){
        if (instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),NoteDataBase.class
            ,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();

        }
        return instance;
    }
     private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
         @Override
         public void onCreate(@NonNull SupportSQLiteDatabase db) {
             super.onCreate(db);
              new PopulatedDbAsyncTask(instance).execute();
         }
     };


    private  static class PopulatedDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private  NoteDao noteDao;

         private PopulatedDbAsyncTask (NoteDataBase db){

             noteDao= db.noteDao();
         }

        @Override
        protected Void doInBackground(Void... voids) {
             noteDao.insert(new Note("title1", "descripition",1));
             noteDao.insert(new Note("title2", "descripition",2));
             noteDao.insert(new Note("title3", "descripition",3));
            return null;
        }
    }


}
