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
    DatabaseHal databaseHal;
    DatabaseScore databaseScore;
    ArrayList<ClassHal> fishArray = new ArrayList<>();
    int questionIndex = 0;
    ArrayList<ClassHal> questionArray = new ArrayList<>();
    ArrayList<Button> buttonArrayList = new ArrayList<>();
    int points = 0;
    String date;
    final Handler handler = new Handler();
    final Runnable r = this::playGame;

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
        databaseHal = new DatabaseHal(this);
        databaseScore = new DatabaseScore(this);
        startGame(level);
    }

    private void startGame(int level){
        switch (level){
            case 1:
                fishArray = new ArrayList<>(databaseHal.getAllDataFromLocalStore().subList(0, 10));
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
                break;
            case 2:
                fishArray = new ArrayList<>(databaseHal.getAllDataFromLocalStore().subList(10, 20));
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
                break;
            case 3:
                fishArray = new ArrayList<>(databaseHal.getAllDataFromLocalStore().subList(20, 30));
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
                break;
            case 4:
                fishArray = new ArrayList<>(databaseHal.getAllDataFromLocalStore().subList(30, 40));
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
                break;
            case 5:
                fishArray = new ArrayList<>(databaseHal.getAllDataFromLocalStore().subList(40, 50));
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
                break;
            case 6:
                fishArray = new ArrayList<>(databaseHal.getAllDataFromLocalStore().subList(50, 60));
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
                break;
            case 7:
                fishArray = new ArrayList<>(databaseHal.getAllDataFromLocalStore().subList(60, 70));
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
                break;
            case 8:
                fishArray = new ArrayList<>(databaseHal.getAllDataFromLocalStore().subList(70, 82));
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
                break;
            case 9:
                fishArray = databaseHal.getAllDataFromLocalStore();
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray.subList(0, 10));
                playGame();
            default:
                fishArray = databaseHal.getAllDataFromLocalStore();
                Collections.shuffle(fishArray);
                questionArray = new ArrayList<>(fishArray);
                playGame();
        }
    }

    private  void playGame(){
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
        ArrayList<ClassHal> fishArrayCopy = new ArrayList<>(fishArray);
        ArrayList<ClassHal> wrongAnswersArray = new ArrayList<>();
        while (wrongAnswersArray.size() != 3){
            int randomNumber = new Random().nextInt(fishArrayCopy.size());
            if (!fishArrayCopy.get(randomNumber).getNev().equals(questionArray.get(questionIndex).getNev())){
                wrongAnswersArray.add(fishArrayCopy.get(randomNumber));
                fishArrayCopy.remove(randomNumber);
            }
        }
        handleAnswerButtons(wrongAnswersArray);
    }

    private void handleAnswerButtons(ArrayList<ClassHal> wrongAnswersArray) {
        Collections.shuffle(buttonArrayList);
        buttonArrayList.get(0).setText(questionArray.get(questionIndex).getNev());
        buttonArrayList.get(1).setText(wrongAnswersArray.get(0).getNev());
        buttonArrayList.get(2).setText(wrongAnswersArray.get(1).getNev());
        buttonArrayList.get(3).setText(wrongAnswersArray.get(2).getNev());
    }

    private void onAnswerButtonClick(Button button, String buttonText){
        for (Button b: buttonArrayList) {
            b.setClickable(false);
        }
        if (questionArray.get(questionIndex).getNev().equals(buttonText)){
            points++;
            button.setBackgroundColor(getResources().getColor(R.color.green));
        } else {
            button.setBackgroundColor(getResources().getColor(R.color.red));
            for (Button b: buttonArrayList) {
                if (b.getText().toString().equals(questionArray.get(questionIndex).getNev())){
                    b.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                }
            }
        }
        questionIndex++;
        if(level < 10){
            if (questionIndex < 10){
                handler.postDelayed(r, 800);
            } else {
                gameOver();
            }
        } else {
            if (questionIndex < 82){
                handler.postDelayed(r, 100);
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
        //TODO show scores
    }

    private void exitGame(){
        finish();
    }


//    public void jatekVege(){
//
//        datum = sdf.format(new Date()); //játék időpontja (string)
//        jatekEredmeny = new JatekEredmeny(datum, pontSzam, szint, jatekosNev);
//        addData(jatekEredmeny);
//        ujraGomb.setVisibility(View.VISIBLE);
//        befejezes.setVisibility(View.VISIBLE);
//        if( pontSzam == 10 && szint != 7 && szint != 99) {
//            nextLevel.setVisibility(View.VISIBLE);
//            Animation a = new AlphaAnimation(0.0f, 1.0f);
//            a.setDuration(1500);
//            nextLevel.startAnimation(a);
//        }
//
//        ujraGomb.setBackgroundColor(Color.parseColor("#FF018786"));
//        befejezes.setBackgroundColor(Color.parseColor("#FF018786"));
//
//        if(szint != 99){
//            pontszamJelzo.setText("Elért pontszám: " + pontSzam + " / 10");
//            pontszamJelzo.setVisibility(View.VISIBLE);
//            switch (pontSzam){
//                case 10 : {
//                    teljesitmeny.setText("ÜGYES VAGY!");
//                    teljesitmeny.setTextColor(Color.GREEN);
//                    teljesitmeny.setVisibility(View.VISIBLE);
//                    break;
//                }
//                case 0 : {
//                    teljesitmeny.setText("EZT MÉG GYAKOROLNI KELL!");
//                    teljesitmeny.setTextColor(Color.RED);
//                    teljesitmeny.setVisibility(View.VISIBLE);
//                    break;
//                }
//                default: { break; }//semmit sem irunk ki
//            }
//
//        } else { //99 lvl
//            pontszamJelzo.setText("Elért pontszám: " + pontSzam + " / 67");
//            pontszamJelzo.setVisibility(View.VISIBLE);
//            switch (pontSzam){
//                case 67 : {
//                    teljesitmeny.setText("GRATULÁLOK! NAGYON JÓL ISMERED A HALAKAT!");
//                    teljesitmeny.setTextColor(Color.GREEN);
//                    teljesitmeny.setVisibility(View.VISIBLE);
//                    break;
//                }
//                case 66 : {
//                    teljesitmeny.setText("SZÉP MUNKA! MAJDNEM TÖKÉLETES!");
//                    teljesitmeny.setTextColor(Color.RED);
//                    teljesitmeny.setVisibility(View.VISIBLE);
//                    break;
//                }
//                default: {
//                    teljesitmeny.setText("MÉG EGY KICSIT GYAKOROLJ!");
//                    teljesitmeny.setTextColor(Color.RED);
//                    teljesitmeny.setVisibility(View.VISIBLE);
//                    break;
//                }
//            }
//        }
//    }
//
//
//    public void addData(JatekEredmeny jatekEredmeny){
//        boolean adatBeszuras = mDatabase.addData(jatekEredmeny);
//        if(adatBeszuras){
//            //sikerült a beszúrás
//        } else{
//            //nem sikerült a beszúrás HIBA :(
//        }
//    }
//
//    public void goToEredmenyek(View view) {
//        finish();
//        Intent intent = new Intent(this, EredmenyekActivity.class);
//        intent.putExtra("datum", jatekEredmeny.getDatum());
//        intent.putExtra("szint", jatekEredmeny.getSzint());
//        startActivity(intent);
//    }
//
//
//    public void goNextLevel(View view) {
//        finish();
//        Intent intent = new Intent(this, JatekActivity.class);
//        int ujSzint = szint + 1;
//        intent.putExtra("szint", ujSzint);
//        intent.putExtra("jatekosNev", jatekosNev);
//        startActivity(intent);
//    }
//}

}