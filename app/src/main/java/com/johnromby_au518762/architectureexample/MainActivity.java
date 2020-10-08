package com.johnromby_au518762.architectureexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class); // ViewModelProviders is deprecated.

        // This is the new way of doing it I believe
        noteViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // Update RecyclerView...

                // TODO Temporary Toast message, can be removed.
                Toast.makeText(MainActivity.this, "Debug: onChanged", Toast.LENGTH_SHORT).show();
            }
        });
    }
}