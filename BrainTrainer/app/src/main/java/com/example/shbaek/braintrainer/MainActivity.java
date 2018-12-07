package com.example.shbaek.braintrainer;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView, qTextView, avgTextView, resultTextView;
    Button btn1, btn2, btn3, btn4, startButton, playAgainButton;
    int answer = 0;
    int wrongAnswer = 0;
    int numberOfQuestions = 0;
    int score = 0;
    boolean isActive = false;
    ConstraintLayout gameLayout;

    ArrayList<Integer> answers = new ArrayList<Integer>();

    public void makeProblem(){
        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        answer = a+b;
        qTextView.setText(Integer.toString(a)+"+"+Integer.toString(b));

        int locationOfCorrectAnswer = rand.nextInt(4);
        for(int i=0; i<4; i++){
            if(i==locationOfCorrectAnswer){
                answers.add(answer);
            } else {
                wrongAnswer = rand.nextInt(41);
                if(wrongAnswer == answer){
                    wrongAnswer = rand.nextInt(41);
                }
                answers.add(wrongAnswer);
            }
        }

        Log.i("answers.get(0) : ", Integer.toString(answers.get(0)));
        Log.i("answers.get(1) : ", Integer.toString(answers.get(1)));
        Log.i("answers.get(2) : ", Integer.toString(answers.get(2)));
        Log.i("answers.get(3) : ", Integer.toString(answers.get(3)));

        btn1.setText(Integer.toString(answers.get(0))); btn1.setTag(answers.get(0));
        btn2.setText(Integer.toString(answers.get(1))); btn2.setTag(answers.get(1));
        btn3.setText(Integer.toString(answers.get(2))); btn3.setTag(answers.get(2));
        btn4.setText(Integer.toString(answers.get(3))); btn4.setTag(answers.get(3));
    }

    public void start(View view){
        gameLayout.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        playAgain(findViewById(R.id.timerTextView));

    }

    public void playAgain(View view){

        numberOfQuestions = 0;
        score = 0;
        avgTextView.setText("0/0");
        timerTextView.setText("30s");
        playAgainButton.setVisibility(View.INVISIBLE);
        resultTextView.setText("");


        new CountDownTimer(5100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timeRemain = String.valueOf(millisUntilFinished/1000) + "s";
                timerTextView.setText(timeRemain);
            }

            @Override
            public void onFinish() {
                resultTextView.setText("종료!");
                isActive = false;
                playAgainButton.setVisibility(View.VISIBLE);

            }
        }.start();

        makeProblem();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        avgTextView = findViewById(R.id.avgTextView);
        qTextView = findViewById(R.id.qTextView);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        startButton = findViewById(R.id.startButton);
        resultTextView = findViewById(R.id.resultTextView);
        playAgainButton = findViewById(R.id.playAgainButton);
        gameLayout = findViewById(R.id.gameLayout);

        gameLayout.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.VISIBLE);

    }

    public void checkedAnswer(View view){

        Log.i("answer : ", String.valueOf(answer));
        numberOfQuestions++;

        resultTextView.setVisibility(View.VISIBLE);

        if(view.getTag().equals(answer)) {
            resultTextView.setText("정답!");
            score++;
            Log.i("answer : ", view.getTag().toString());
        } else {
            resultTextView.setText("오답!");
            Log.i("answer : ", view.getTag().toString());
        }

        avgTextView.setText(score+"/"+numberOfQuestions);
        answers.clear();
        makeProblem();
    }
}
