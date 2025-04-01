package com.example.mathspp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class quiz_page extends AppCompatActivity {
    private TextView questionText, scoreText, feedbackMessage;
    private RadioGroup optionsGroup;
    private RadioButton option1, option2, option3, option4;
    private Button nextButton;
    private ImageView feedbackImage;

    private int num1, num2, correctAnswer, currentQuestionIndex = 0, score = 0;
    private Random random = new Random();
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);

        questionText = findViewById(R.id.questionText);
        scoreText = findViewById(R.id.scoreText);
        optionsGroup = findViewById(R.id.optionsGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        nextButton = findViewById(R.id.nextButton);
        feedbackImage = findViewById(R.id.feedbackImage);
        feedbackMessage = findViewById(R.id.feedbackMessage);

        // Initially hide feedback views
        feedbackImage.setVisibility(View.INVISIBLE);
        feedbackMessage.setVisibility(View.INVISIBLE);

        loadNewQuestion();

        nextButton.setOnClickListener(v -> checkAnswer());
    }

    private void loadNewQuestion() {
        if (currentQuestionIndex < 10) { // 10 questions per quiz
            num1 = random.nextInt(10) + 1;  // Numbers between 1 and 10
            num2 = random.nextInt(10) + 1;
            correctAnswer = num1 * num2;
            questionText.setText("What is " + num1 + " × " + num2 + "?");

            // Generate wrong answers
            int wrongAnswer1 = correctAnswer + random.nextInt(5) + 1;
            int wrongAnswer2 = correctAnswer - random.nextInt(5) - 1;
            int wrongAnswer3 = correctAnswer + random.nextInt(10) - 5;

            int correctPosition = random.nextInt(4); // Random position for correct answer

            RadioButton[] options = {option1, option2, option3, option4};
            for (int i = 0; i < 4; i++) {
                if (i == correctPosition) {
                    options[i].setText(String.valueOf(correctAnswer));
                } else {
                    int[] wrongAnswers = {wrongAnswer1, wrongAnswer2, wrongAnswer3};
                    options[i].setText(String.valueOf(wrongAnswers[i % 3]));
                }
            }
        } else {
            // Quiz finished: pass score to result page
            Intent intent = new Intent(quiz_page.this, quiz_result.class);
            intent.putExtra("finalScore", score);
            startActivity(intent);
            finish();
        }
    }

    private void checkAnswer() {
        int selectedId = optionsGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedOption = findViewById(selectedId);
            int selectedAnswer = Integer.parseInt(selectedOption.getText().toString());

            if (selectedAnswer == correctAnswer) {
                score++;
                showFeedback(true);
            } else {
                showFeedback(false);
            }
            scoreText.setText("Score: " + score);

            currentQuestionIndex++;
            optionsGroup.clearCheck();
            // Wait a moment to let kids see the feedback before moving on
            handler.postDelayed(() -> {
                feedbackImage.setVisibility(View.INVISIBLE);
                feedbackMessage.setVisibility(View.INVISIBLE);
                loadNewQuestion();
            }, 1500);
        } else {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
        }
    }

    private void showFeedback(boolean isCorrect) {
        if (isCorrect) {
            feedbackImage.setImageResource(R.drawable.tick); // tick image resource
            feedbackMessage.setText("Great Job!");
        } else {
            feedbackImage.setImageResource(R.drawable.cross); // cross image resource
            feedbackMessage.setText("Oops! Try again.");
        }
        feedbackImage.setVisibility(View.VISIBLE);
        feedbackMessage.setVisibility(View.VISIBLE);
    }


}

