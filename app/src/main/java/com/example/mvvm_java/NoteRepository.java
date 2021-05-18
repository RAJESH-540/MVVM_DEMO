package com.example.mvvm_java;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

   public NoteRepository (Application application){
       NoteDataBase dataBase= NoteDataBase.getInstance(application);
        noteDao = dataBase.noteDao();
        allNotes=noteDao.getAllNotes();

   }

        public void insert(Note note){
        new InsertNoteAsyncTask(noteDao).execute(note);

        }

        public void update(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);


        }
        public  void delete(Note note){
       new DeleteNoteAsyncTask(noteDao).execute(note);

        }

        public  void deleteAllNotes(){
       new DeleteAllNoteAsyncTask(noteDao).execute();


        }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    //insert async task
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void>{

       private  NoteDao noteDao;

        public InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    //delete Async task
    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        private  NoteDao noteDao;

        public DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    //update async task
    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        private  NoteDao noteDao;

        public UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    //delete all notes
    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void>{

        private  NoteDao noteDao;

        public DeleteAllNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... notes) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

}
