package com.example.mathspp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class second_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page2);

        EditText text = findViewById(R.id.entered_text);
        Button power = findViewById(R.id.n_power);
        Button table = findViewById(R.id.table);
        Button calculator = findViewById(R.id.calculater);

        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = text.getText().toString().trim();
                if (!num.isEmpty()) {
                    int number = Integer.parseInt(num);
                    if (number > 0) {
                        Intent power = new Intent(second_page.this, power.class);
                        power.putExtra("number", number);
                        startActivity(power);
                    } else {
                        text.setHint("please enter positive number");
                    }
                } else {
                    Toast.makeText(second_page.this, "Please enter the number", Toast.LENGTH_LONG).show();
                    text.setHint("Please Enter a number");
                }
            }
        });

        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = text.getText().toString().trim();
                if (!num.isEmpty()) {
                    int number = Integer.parseInt(num);
                    if (number > 0) {
                        Intent table = new Intent(second_page.this, table.class);
                        table.putExtra("number", number);
                        startActivity(table);
                    } else {
                        text.setHint("Please Enter positive number ");
                        Toast.makeText(second_page.this, "Please enter correct input", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(second_page.this, "Please enter correct input", Toast.LENGTH_LONG).show();
                    text.setHint("Please Enter a number");
                }
            }
        });

        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalculator();
            }
        });
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
