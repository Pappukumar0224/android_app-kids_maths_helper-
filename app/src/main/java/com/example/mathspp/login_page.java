package com.example.mathspp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Button submit = findViewById(R.id.login_submit_button);
        EditText pinInput = findViewById(R.id.editText_pin);
        TextView enterpinmsg = findViewById(R.id.textView_pin);
        EditText signupmsg = findViewById(R.id.signup_Text_msg);
        Button signup = findViewById(R.id.signup_button);

        UserDBHelper dbHelper = new UserDBHelper(this);

        submit.setOnClickListener(v -> {
            String enteredPin = pinInput.getText().toString().trim();
            if (enteredPin.length() == 4 && dbHelper.validatePin(enteredPin)) {
                Toast.makeText(login_page.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(login_page.this, menu_page.class));
                finish();
            } else {
                Toast.makeText(login_page.this, "Incorrect PIN. Try Again!", Toast.LENGTH_SHORT).show();
            }
        });

        signup.setOnClickListener(v -> startActivity(new Intent(login_page.this, signup_page.class)));

    }
}