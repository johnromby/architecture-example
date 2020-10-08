package com.johnromby_au518762.architectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {
    private TextView editTextTitle;
    private TextView editTextDescription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle(getString(R.string.addNoteTitle));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, R.string.toastMsgTitleDescription, Toast.LENGTH_SHORT).show();
            return;
        }

        // For simplicity I have not created a new separate ViewModel class for this, but it would be the correct approach.
        // Reason for why it is a good idea to create a separate VM class is because Add Note does not need to retrieve notes from the database, it just needs to save a new note.
        // So again, for simplicity, we'll keep one VM for all operations and also we won't use that VM here, but instead send it back to the main activity.
        // The simple solution here is therefore Intents.
        Intent data = new Intent();
        data.putExtra(Constants.EXTRA_TITLE, title);
        data.putExtra(Constants.EXTRA_DESCRIPTION, description);
        data.putExtra(Constants.EXTRA_PRIORITY, priority);

        setResult(RESULT_OK, data);
        finish();
    }
}