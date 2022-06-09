package com.example.horgszmobilalkalmazs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class FragmentScores extends Fragment {
    View view;
    ArrayList<Button> buttonArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_scores, container, false);

        buttonArrayList.add(view.findViewById(R.id.elsoSzintButton));
        buttonArrayList.add(view.findViewById(R.id.masodikSzintButton));
        buttonArrayList.add(view.findViewById(R.id.harmadikSzintButton));
        buttonArrayList.add(view.findViewById(R.id.negyedikSzintButton));
        buttonArrayList.add(view.findViewById(R.id.otodikSzintButton));
        buttonArrayList.add(view.findViewById(R.id.hatodikSzintButton));
        buttonArrayList.add(view.findViewById(R.id.hetedikSzintButton));
        buttonArrayList.add(view.findViewById(R.id.nyolcadikSzintButton));
        buttonArrayList.add(view.findViewById(R.id.kilencedikSzintButton));
        buttonArrayList.add(view.findViewById(R.id.tizedikSzintButton));

        handleButtons();
        return view;
    }

    private void handleButtons(){
        for (int i = 0; i < buttonArrayList.size(); i++) {
            int level = i+1;
            buttonArrayList.get(i).setOnClickListener(o-> onClickButton(level));
        }
    }

    private void onClickButton(int level) {
        Intent intent = new Intent(view.getContext(), ScoresActivity.class);
        intent.putExtra("level", level);
        startActivity(intent);
    }
}