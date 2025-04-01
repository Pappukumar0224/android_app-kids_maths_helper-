package com.example.mathspp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class menu_page extends AppCompatActivity {

    TextView welcomeText;
    ImageButton powerButton, tableButton, calculatorButton, areaPerimeterButton, takeTestButton;
    UserDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

        welcomeText = findViewById(R.id.welcome_text);
        powerButton = findViewById(R.id.n_power);
        tableButton = findViewById(R.id.table);
        calculatorButton = findViewById(R.id.calculater);
        areaPerimeterButton = findViewById(R.id.area_perimeter);
        takeTestButton = findViewById(R.id.take_test);

        // Initialize database helper
        dbHelper = new UserDBHelper(this);

        // Fetch the latest logged-in user
        String userName = getLatestUserName();
        welcomeText.setText("Welcome " + userName);

        // Button Click Listeners
        powerButton.setOnClickListener(v -> startActivity(new Intent(menu_page.this, power.class)));
        tableButton.setOnClickListener(v -> startActivity(new Intent(menu_page.this, table.class)));
        areaPerimeterButton.setOnClickListener(v -> startActivity(new Intent(menu_page.this, area_peri.class)));

        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalculator();
            }
        });

        takeTestButton.setOnClickListener(v -> startActivity(new Intent(menu_page.this, quiz_page.class))); // Future Quiz Page
    }

    // Method to get the latest signed-up user's name from SQLite
    private String getLatestUserName() {
        Cursor cursor = dbHelper.getLastUser();
        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getString(0); // Get the name column value
        }
        return "User"; // Default if no user is found
    }

    private void openCalculator() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_CALCULATOR);

        PackageManager packageManager = getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent);
        } else {
            // List of known calculator package names
            String[] calculatorPackages = {
                    "com.android.calculator2",
                    "com.sec.android.app.popupcalculator",
                    "com.google.android.calculator"
            };

            boolean calculatorFound = false;
            for (String packageName : calculatorPackages) {
                intent = packageManager.getLaunchIntentForPackage(packageName);
                if (intent != null) {
                    startActivity(intent);
                    calculatorFound = true;
                    break;
                }
            }

            if (!calculatorFound) {
                Toast.makeText(this, "No calculator app found", Toast.LENGTH_LONG).show();
            }
        }
    }
}
