package com.example.mathspp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class signup_page extends AppCompatActivity {

    EditText nameInput, ageInput, pinInput;
    Button signupButton;
    UserDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        nameInput = findViewById(R.id.editText_name);
        ageInput = findViewById(R.id.editText_age);
        pinInput = findViewById(R.id.editText_pin);
        signupButton = findViewById(R.id.signup_submit_button);

        dbHelper = new UserDBHelper(this);

        signupButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String ageStr = ageInput.getText().toString().trim();
            String pin = pinInput.getText().toString().trim();

            if (!name.isEmpty() && !ageStr.isEmpty() && pin.length() == 4) {
                int age = Integer.parseInt(ageStr);
                boolean success = dbHelper.insertUser(name, age, pin);

                if (success) {
                    Toast.makeText(signup_page.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(signup_page.this, login_page.class));
                    finish();
                } else {
                    Toast.makeText(signup_page.this, "Sign Up Failed!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(signup_page.this, "Please fill all fields correctly", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
