package com.ajit.developerbeetask;

import static com.ajit.developerbeetask.Constants.PREFS_NAME;
import static com.ajit.developerbeetask.Constants.PREF_SEARCH_WORD;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {


    private EditText searchEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        searchEditText = findViewById(R.id.searchEditText);
        Button saveButton = findViewById(R.id.saveButton);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedSearchWord = prefs.getString(PREF_SEARCH_WORD, "hi");
        searchEditText.setText(savedSearchWord);

        saveButton.setOnClickListener(v -> {
            String searchWord = searchEditText.getText().toString().trim();
            if (!searchWord.isEmpty()) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(PREF_SEARCH_WORD, searchWord);
                editor.apply();
                Toast.makeText(SettingsActivity.this, "Search word saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SettingsActivity.this, "Please enter a search word", Toast.LENGTH_SHORT).show();
            }
        });
    }
}