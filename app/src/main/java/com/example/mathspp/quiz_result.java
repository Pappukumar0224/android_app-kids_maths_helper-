package com.example.mathspp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class quiz_result extends AppCompatActivity {
    private TextView resultText;
    private Button backToMenu;
    private UserDBHelper dbHelper;
    private String userPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        resultText = findViewById(R.id.resultText);
        backToMenu = findViewById(R.id.backToMenu);
        dbHelper = new UserDBHelper(this);

        // Use the same key "finalScore" as set in quiz_page.java
        int finalScore = getIntent().getIntExtra("finalScore", 0);
        userPin = getIntent().getStringExtra("USER_PIN"); // Get the PIN from Intent

        resultText.setText("Your Score: " + finalScore + "/10");

        saveScoreToDatabase(finalScore);

        backToMenu.setOnClickListener(v -> {
            Intent intent = new Intent(quiz_result.this, menu_page.class);
            startActivity(intent);
            finish();
        });
    }

    private void saveScoreToDatabase(int finalScore) {
        if (userPin != null) {
            int userId = dbHelper.getUserIdByPin(userPin); // Get user ID using PIN
            if (userId != -1) {
                boolean isSaved = dbHelper.saveScoreToDatabase(userId, finalScore);
                if (isSaved) {
                    Toast.makeText(this, "Score saved successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to save score!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "User ID not found!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User PIN is missing!", Toast.LENGTH_SHORT).show();
        }
    }
}
