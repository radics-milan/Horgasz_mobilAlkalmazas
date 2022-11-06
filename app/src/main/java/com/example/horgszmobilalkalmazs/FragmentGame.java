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


public class FragmentGame extends Fragment {
    View view;
    DatabaseFish databaseFish;
    String username;
    DatabaseScore databaseScore;
    ArrayList<Button> buttonArrayList = new ArrayList<>();
    EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_game, container, false);

        databaseFish = new DatabaseFish(view.getContext());
        databaseScore = new DatabaseScore(view.getContext());

        buttonArrayList.add(view.findViewById(R.id.firstLevelButton));
        buttonArrayList.add(view.findViewById(R.id.secondLevelButton));
        buttonArrayList.add(view.findViewById(R.id.thirdLevelButton));
        buttonArrayList.add(view.findViewById(R.id.fourthLevelButton));
        buttonArrayList.add(view.findViewById(R.id.fifthLevelButton));
        buttonArrayList.add(view.findViewById(R.id.sixthLevelButton));
        buttonArrayList.add(view.findViewById(R.id.seventhButton));
        buttonArrayList.add(view.findViewById(R.id.eighthLevelButton));
        buttonArrayList.add(view.findViewById(R.id.ninthLevelButton));
        buttonArrayList.add(view.findViewById(R.id.tenthLevelButton));

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
        for (int i = 1; i < buttonArrayList.size(); i++) {
            if (databaseScore.isLevelCompleted(i)){
                buttonArrayList.get(i).setEnabled(true);
                buttonArrayList.get(i).setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_unlocked, 0,  R.drawable.ic_unlocked, 0);
            }
        }
    }
}