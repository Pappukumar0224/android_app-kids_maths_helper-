package com.example.mathspp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class table extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        TextView result = findViewById(R.id.textView3);
        int num = getIntent().getIntExtra("number",0);
        String display= genrate_table(num);
        result.setText(display);
    }

    public String genrate_table(int num){

        String str = "\n";
        for(int i=1;i<=10;i++){

            str = str + num + "*"+ i +"="+num*i+"\n";
           System.out.println(str);
        }
        return str;
    }
}