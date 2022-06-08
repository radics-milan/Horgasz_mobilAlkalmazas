package com.example.horgszmobilalkalmazs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class JatekFragment extends Fragment {
    View view;
    DatabaseHal databaseHal;
    String username;
    DatabaseScore databaseScore;
    Button firstLevelButton;
    Button seconLevelButton;
    Button thirdLevelButton;
    Button fourthLevelButton;
    Button fifthLevelButton;
    Button sixthLevelButton;
    Button seventhLevelButton;
    Button eighthLevelButton;
    Button ninthLevelButton;
    Button tenthLevelButton;
    ArrayList<Button> buttonArrayList = new ArrayList<>();
    EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_jatek, container, false);

        databaseHal = new DatabaseHal(view.getContext());
        databaseScore = new DatabaseScore(view.getContext());

        firstLevelButton = view.findViewById(R.id.elsoSzintButton);
        seconLevelButton = view.findViewById(R.id.masodikSzintButton);
        thirdLevelButton = view.findViewById(R.id.harmadikSzintButton);
        fourthLevelButton = view.findViewById(R.id.negyedikSzintButton);
        fifthLevelButton = view.findViewById(R.id.otodikSzintButton);
        sixthLevelButton = view.findViewById(R.id.hatodikSzintButton);
        seventhLevelButton = view.findViewById(R.id.hetedikSzintButton);
        eighthLevelButton = view.findViewById(R.id.nyolcadikSzintButton);
        ninthLevelButton = view.findViewById(R.id.kilencedikSzintButton);
        tenthLevelButton = view.findViewById(R.id.tizedikSzintButton);

        buttonArrayList.add(firstLevelButton);
        buttonArrayList.add(seconLevelButton);
        buttonArrayList.add(thirdLevelButton);
        buttonArrayList.add(fourthLevelButton);
        buttonArrayList.add(fifthLevelButton);
        buttonArrayList.add(sixthLevelButton);
        buttonArrayList.add(seventhLevelButton);
        buttonArrayList.add(eighthLevelButton);
        buttonArrayList.add(ninthLevelButton);
        buttonArrayList.add(tenthLevelButton);

        view.findViewById(R.id.saveUsernameButton).setOnClickListener(o -> saveUsername());

        editText = view.findViewById(R.id.userNameEditText);

        handleOnClickButton();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (databaseScore.getLastPlayersUsername() == null){
            username = getString(R.string.uj_jatekos);
        } else {
            username = databaseScore.getLastPlayersUsername();
        }
        editText.setText(username);
        editText.clearFocus();
        handleLevelLock();
    }


    private void saveUsername() {
        username = editText.getText().toString();
        if (username.equals("")){
            username = getString(R.string.uj_jatekos);
            editText.setText(username);
        }
        editText.clearFocus();
        Toast.makeText(view.getContext(), "Név mentve: " + username, Toast.LENGTH_SHORT).show();
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void handleOnClickButton(){
        for (int i = 0; i < buttonArrayList.size(); i++) {
            int level = i+1;
            buttonArrayList.get(i).setOnClickListener(o -> onClickButton(level));
        }
    }

    private void onClickButton(int level){
        Intent intent = new Intent(view.getContext(), GameActivity.class);
        intent.putExtra("level", level);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void handleLevelLock(){
        for (int i = 0; i < buttonArrayList.size()-1; i++) {
            int level = i+1;
            if (databaseScore.isLevelCompleted(level)){
                buttonArrayList.get(level).setEnabled(true);
                buttonArrayList.get(level).setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_unlocked, 0,  R.drawable.ic_unlocked, 0);
            }
        }
    }
}