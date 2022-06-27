package com.example.horgszmobilalkalmazs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    int level;
    String username;
    ImageView gameImageView;
    Button firstAnswerButton;
    Button secondAnswerButton;
    Button thirdAnswerButton;
    Button fourthAnswerButton;
    Button nextLevelButton;
    Button endButton;
    Button againButton;
    TextView gameNumberTextView;
    TextView pointTextView;
    DatabaseFish databaseFish;
    DatabaseScore databaseScore;
    ArrayList<ClassFish> fishArray = new ArrayList<>();
    int questionIndex = 0;
    ArrayList<ClassFish> questionArray = new ArrayList<>();
    ArrayList<Button> buttonArrayList = new ArrayList<>();
    int points = 0;
    String date;
    final Handler handler = new Handler();
    final Runnable r = this::playGame;
    boolean isButtonClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        level = getIntent().getIntExtra("level", 1);
        username = getIntent().getStringExtra("username");
        if (username == null || username.equals("")) {
            username = getString(R.string.uj_jatekos);
        }

        TextView textView = findViewById(R.id.levelTextView);
        String levelText = "Játék - " + level + ". Szint";
        textView.setText(levelText);
        findViewById(R.id.exitButton).setOnClickListener(o -> exitGame());
        gameImageView = findViewById(R.id.gameImageView);
        pointTextView = findViewById(R.id.pointTextView);
        firstAnswerButton = findViewById(R.id.firstAnswerButton);
        secondAnswerButton = findViewById(R.id.secondAnswerButton);
        thirdAnswerButton = findViewById(R.id.thirdAnswerButton);
        fourthAnswerButton = findViewById(R.id.fourthAnswerButton);
        nextLevelButton = findViewById(R.id.nextLevelButton);
        endButton = findViewById(R.id.scoreButton);
        againButton = findViewById(R.id.againButton);
        buttonArrayList.add(firstAnswerButton);
        buttonArrayList.add(secondAnswerButton);
        buttonArrayList.add(thirdAnswerButton);
        buttonArrayList.add(fourthAnswerButton);
        for (Button b: buttonArrayList) {
            b.setOnClickListener(o -> onAnswerButtonClick(b, b.getText().toString()));
        }
        againButton.setOnClickListener(o -> playAgain(level));
        nextLevelButton.setOnClickListener(o -> playNextLevel());
        endButton.setOnClickListener(o -> showScores());
        gameNumberTextView = findViewById(R.id.gameNumberTextView);
        databaseFish = new DatabaseFish(this);
        databaseScore = new DatabaseScore(this);
        startGame(level);
    }

    private void startGame(int level){
        switch (level){
            case 1:
                fishArray = new ArrayList<>(databaseFish.getAllDataFromLocalStore().subList(0, 10));
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
                break;
            case 2:
                fishArray = new ArrayList<>(databaseFish.getAllDataFromLocalStore().subList(10, 20));
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
                break;
            case 3:
                fishArray = new ArrayList<>(databaseFish.getAllDataFromLocalStore().subList(20, 30));
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
                break;
            case 4:
                fishArray = new ArrayList<>(databaseFish.getAllDataFromLocalStore().subList(30, 40));
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
                break;
            case 5:
                fishArray = new ArrayList<>(databaseFish.getAllDataFromLocalStore().subList(40, 50));
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
                break;
            case 6:
                fishArray = new ArrayList<>(databaseFish.getAllDataFromLocalStore().subList(50, 60));
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
                break;
            case 7:
                fishArray = new ArrayList<>(databaseFish.getAllDataFromLocalStore().subList(60, 70));
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
                break;
            case 8:
                fishArray = new ArrayList<>(databaseFish.getAllDataFromLocalStore().subList(70, 82));
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
                break;
            case 9:
                fishArray = databaseFish.getAllDataFromLocalStore();
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
            default:
                fishArray = databaseFish.getAllDataFromLocalStore();
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray);
                playGame();
        }
    }

    private  void playGame(){
        isButtonClicked = false;
        againButton.setVisibility(View.GONE);
        nextLevelButton.setVisibility(View.GONE);
        endButton.setVisibility(View.GONE);
        pointTextView.setVisibility(View.GONE);
        for (Button b: buttonArrayList) {
            b.setBackgroundColor(getResources().getColor(R.color.orange));
            b.setClickable(true);
        }
        String gameNumberText = (questionIndex + 1) + ". Melyik hal van a képen?";
        gameNumberTextView.setText(gameNumberText);
        gameImageView.setImageResource(questionArray.get(questionIndex).getImageResourceId());
        ArrayList<ClassFish> fishArrayCopy = new ArrayList<>(fishArray);
        ArrayList<ClassFish> wrongAnswersArray = new ArrayList<>();
        while (wrongAnswersArray.size() != 3){
            int randomNumber = new Random().nextInt(fishArrayCopy.size());
            if (!fishArrayCopy.get(randomNumber).getName().equals(questionArray.get(questionIndex).getName())){
                wrongAnswersArray.add(fishArrayCopy.get(randomNumber));
                fishArrayCopy.remove(randomNumber);
            }
        }
        handleAnswerButtons(wrongAnswersArray);
    }

    private void handleAnswerButtons(ArrayList<ClassFish> wrongAnswersArray) {
        Collections.shuffle(buttonArrayList);
        buttonArrayList.get(0).setText(questionArray.get(questionIndex).getName());
        buttonArrayList.get(1).setText(wrongAnswersArray.get(0).getName());
        buttonArrayList.get(2).setText(wrongAnswersArray.get(1).getName());
        buttonArrayList.get(3).setText(wrongAnswersArray.get(2).getName());
    }

    private void onAnswerButtonClick(Button button, String buttonText){
        if (isButtonClicked){
            return;
        }
        isButtonClicked = true; // to prevent multiple button click at the same time
        for (Button b: buttonArrayList) {
            b.setClickable(false);
        }
        if (questionArray.get(questionIndex).getName().equals(buttonText)){
            points++;
            button.setBackgroundColor(getResources().getColor(R.color.green));
        } else {
            button.setBackgroundColor(getResources().getColor(R.color.red));
            for (Button b: buttonArrayList) {
                if (b.getText().toString().equals(questionArray.get(questionIndex).getName())){
                    b.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                }
            }
        }
        questionIndex++;
        if(level < 10){
            if (questionIndex < 10){
                handler.postDelayed(r, 500);
            } else {
                gameOver();
            }
        } else {
            if (questionIndex < 82){
                handler.postDelayed(r, 500);
            } else {
                gameOver();
            }
        }
    }

    private void playNextLevel() {
        playAgain(level+1);
    }

    private void playAgain(int level) {
        finish();
        Intent intent = new Intent(this,GameActivity.class);
        intent.putExtra("level", level);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void gameOver(){
        againButton.setVisibility(View.VISIBLE);
        endButton.setVisibility(View.VISIBLE);
        pointTextView.setVisibility(View.VISIBLE);
        String pointText;
        if (level < 10){
            pointText = "Elért pontszám: " + points + " / 10";
        } else {
            pointText = "Elért pontszám: " + points + " / 82";
        }

        pointTextView.setText(pointText);

        if (level < 10 && points == 10){
            nextLevelButton.setVisibility(View.VISIBLE);
        }

        saveScore();
    }

    private void saveScore() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd. HH:mm:ss", Locale.getDefault());
        date = sdf.format(new Date());
        databaseScore.addData(new ClassScore(date, points, level, username));
    }

    private void showScores() {
        finish();
        Intent intent = new Intent(this, ScoresActivity.class);
        intent.putExtra("date", date);
        intent.putExtra("level", level);
        startActivity(intent);
    }

    private void exitGame(){
        finish();
    }

}