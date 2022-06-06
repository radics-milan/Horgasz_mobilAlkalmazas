package com.example.horgszmobilalkalmazs;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;



public class JatekFragment extends Fragment {
    View view;
    DatabaseHal databaseHal;
    String username;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_jatek, container, false);

        view.findViewById(R.id.elsoSzintButton).setOnClickListener(o -> onClickButton(1));
        view.findViewById(R.id.masodikSzintButton).setOnClickListener(o -> onClickButton(2));
        view.findViewById(R.id.harmadikSzintButton).setOnClickListener(o -> onClickButton(3));
        view.findViewById(R.id.negyedikSzintButton).setOnClickListener(o -> onClickButton(4));
        view.findViewById(R.id.otodikSzintButton).setOnClickListener(o -> onClickButton(5));
        view.findViewById(R.id.hatodikSzintButton).setOnClickListener(o -> onClickButton(6));
        view.findViewById(R.id.hetedikSzintButton).setOnClickListener(o -> onClickButton(7));
        view.findViewById(R.id.nyolcadikSzintButton).setOnClickListener(o -> onClickButton(8));
        view.findViewById(R.id.kilencedikSzintButton).setOnClickListener(o -> onClickButton(9));
        view.findViewById(R.id.tizedikSzintButton).setOnClickListener(o -> onClickButton(10));
        view.findViewById(R.id.saveUsernameButton).setOnClickListener(o -> saveUsername());
        username = getString(R.string.uj_jatekos);
        databaseHal = new DatabaseHal(view.getContext());
        return view;
    }

    private void saveUsername() {
        EditText editText = view.findViewById(R.id.userNameEditText);
        username = editText.getText().toString();
        if (username.equals("")){
            username = getString(R.string.uj_jatekos);
            editText.setText(username);
        }

        Toast.makeText(view.getContext(), "Név mentve: " + username, Toast.LENGTH_SHORT).show();
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onClickButton(int level){
        Intent intent = new Intent(view.getContext(), GameActivity.class);
        intent.putExtra("level", level);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}