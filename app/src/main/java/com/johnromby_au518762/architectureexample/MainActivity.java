package com.johnromby_au518762.architectureexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btnAddNote = findViewById(R.id.btn_add_note);
        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, Constants.ADD_NOTE_REQ);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true); // Only set this if you know the RecyclerViews size does not change (increases performance)

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        //noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class); // ViewModelProviders is deprecated.
        // This is the new way of doing it I believe
        noteViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.ADD_NOTE_REQ && resultCode == RESULT_OK) {
            String title = data.getStringExtra(Constants.EXTRA_TITLE);
            String description = data.getStringExtra(Constants.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(Constants.EXTRA_PRIORITY, 1);

            Note note = new Note(title, description, priority);
            noteViewModel.insert(note);

            Toast.makeText(this, R.string.toastMsgNoteSaved, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.toastMsgNoteNotSaved, Toast.LENGTH_SHORT).show();
        }
    }
}