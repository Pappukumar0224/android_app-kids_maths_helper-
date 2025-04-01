package com.example.mathspp;;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class area_peri extends AppCompatActivity {
    private EditText input1, input2;
    private Button calculateBtn;
    private TextView resultText, formulaText;
    private ImageView shapeImage;
    private Spinner shapeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_peri);

        shapeSpinner = findViewById(R.id.shape_spinner);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        calculateBtn = findViewById(R.id.calculate_btn);
        resultText = findViewById(R.id.result_text);
        formulaText = findViewById(R.id.formula_text);
        shapeImage = findViewById(R.id.shape_image);

        String[] shapes = {"Square", "Rectangle", "Circle", "Triangle"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, shapes);
        shapeSpinner.setAdapter(adapter);

        shapeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateUI(shapes[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        calculateBtn.setOnClickListener(v -> calculate());
    }

    private void updateUI(String shape) {
        switch (shape) {
            case "Square":
                shapeImage.setImageResource(R.drawable.square_icon);
                input1.setHint("Enter Side Length");
                input2.setVisibility(View.GONE);
                formulaText.setText("Area = Side × Side\nPerimeter = 4 × Side");
                break;
            case "Rectangle":
                shapeImage.setImageResource(R.drawable.rectangle_icon);
                input1.setHint("Enter Length");
                input2.setHint("Enter Width");
                input2.setVisibility(View.VISIBLE);
                formulaText.setText("Area = Length × Width\nPerimeter = 2 × (Length + Width)");
                break;
            case "Circle":
                shapeImage.setImageResource(R.drawable.circle_icon);
                input1.setHint("Enter Radius");
                input2.setVisibility(View.GONE);
                formulaText.setText("Area = π × Radius²\nPerimeter (Circumference) = 2 × π × Radius");
                break;
            case "Triangle":
                shapeImage.setImageResource(R.drawable.triangle_icon);
                input1.setHint("Enter Base");
                input2.setHint("Enter Height");
                input2.setVisibility(View.VISIBLE);
                formulaText.setText("Area = (Base × Height) / 2\nPerimeter depends on all sides");
                break;
        }
    }

    private void calculate() {
        String shape = shapeSpinner.getSelectedItem().toString();
        double value1, value2, area = 0, perimeter = 0;

        try {
            value1 = Double.parseDouble(input1.getText().toString());
            value2 = input2.getVisibility() == View.VISIBLE ? Double.parseDouble(input2.getText().toString()) : 0;

            switch (shape) {
                case "Square":
                    area = value1 * value1;
                    perimeter = 4 * value1;
                    break;
                case "Rectangle":
                    area = value1 * value2;
                    perimeter = 2 * (value1 + value2);
                    break;
                case "Circle":
                    area = Math.PI * value1 * value1;
                    perimeter = 2 * Math.PI * value1;
                    break;
                case "Triangle":
                    area = (value1 * value2) / 2;
                    perimeter = 0; // Perimeter needs all three sides
                    break;
            }

            String areaFormatted = String.format("%.2f", area);
            String perimeterFormatted = perimeter != 0 ? String.format("%.2f", perimeter) : "Depends on sides";

            resultText.setText("Area: " + areaFormatted + "\nPerimeter: " + perimeterFormatted);
        } catch (NumberFormatException e) {
            resultText.setText("Please enter valid values!");
        }
    }

}
