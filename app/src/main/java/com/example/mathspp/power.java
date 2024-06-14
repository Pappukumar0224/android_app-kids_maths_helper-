package com.example.mathspp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class power extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power);
        TextView result = findViewById(R.id.textView);
        int number = getIntent().getIntExtra("number",0);

        long square = (long) number * number;
        result.setText("Square of "+number + "is : "+square);

    }

}