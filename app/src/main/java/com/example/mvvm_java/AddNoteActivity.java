package com.example.mvvm_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE= "com.example.mvvm_java.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION= "com.example.mvvm_java.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY= "com.example.mvvm_java.EXTRA_PRIORITY";
    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle= findViewById(R.id.editText_title);
        editTextDescription= findViewById(R.id.editText_description);
        numberPickerPriority=findViewById(R.id.number_picker);
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);
     getSupportActionBar().setHomeAsUpIndicator(R.drawable.cancel_24);
        setTitle("Add Note");
    }

    //
    private void saveNote(){
        String title= editTextTitle.getText().toString();
        String description= editTextDescription.getText().toString();
        int priority= numberPickerPriority.getValue();
        if (title.trim().isEmpty()||description.trim().isEmpty()){
            Toast.makeText(this, "Please insert title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data= new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);
        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                // set method
                saveNote();
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}