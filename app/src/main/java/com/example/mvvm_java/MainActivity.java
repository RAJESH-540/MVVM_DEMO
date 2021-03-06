package com.example.mvvm_java;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_RESULT= 1;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton= findViewById(R.id.floating_btn);
        floatingActionButton.setOnClickListener(v -> {
            Intent intent= new Intent(MainActivity.this,AddNoteActivity.class);
            startActivityForResult(intent,ADD_NOTE_RESULT);
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();

        final NoteAdapter adapter= new NoteAdapter();
        recyclerView.setAdapter(adapter);
         noteViewModel= ViewModelProviders.of(this).get(NoteViewModel.class);
         noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
             @Override
             public void onChanged(List<Note> notes) {
                 adapter.SetNotes(notes);
             }
         });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ADD_NOTE_RESULT && resultCode==RESULT_OK){
            String title=data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description =data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int priority =data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY,1);
             Note note= new Note(title, description,priority);
             noteViewModel.insert(note);
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }
}