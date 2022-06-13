package com.example.horgszmobilalkalmazs;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentScores extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scores, container, false);
        view.findViewById(R.id.firstLevelButton).setOnClickListener(o-> onClickButton(1));
        view.findViewById(R.id.secondLevelButton).setOnClickListener(o-> onClickButton(2));
        view.findViewById(R.id.thirdLevelButton).setOnClickListener(o-> onClickButton(3));
        view.findViewById(R.id.fourthLevelButton).setOnClickListener(o-> onClickButton(4));
        view.findViewById(R.id.fifthLevelButton).setOnClickListener(o-> onClickButton(5));
        view.findViewById(R.id.sixthLevelButton).setOnClickListener(o-> onClickButton(6));
        view.findViewById(R.id.seventhButton).setOnClickListener(o-> onClickButton(7));
        view.findViewById(R.id.eighthLevelButton).setOnClickListener(o-> onClickButton(8));
        view.findViewById(R.id.ninthLevelButton).setOnClickListener(o-> onClickButton(9));
        view.findViewById(R.id.tenthLevelButton).setOnClickListener(o-> onClickButton(10));

        return view;
    }

    private void onClickButton(int level) {
        Intent intent = new Intent(getContext(), ScoresActivity.class);
        intent.putExtra("level", level);
        startActivity(intent);
    }
}