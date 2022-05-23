package com.example.horgszmobilalkalmazs;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FooldalFragment extends Fragment {
    View view;
    Button quitButton;
    String LOG_TAG = FooldalFragment.class.getName();
    TextView todayDateTextView;
    LinearLayout todayTilalmiIdoszakFishList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fooldal, container, false);

        quitButton = view.findViewById(R.id.quitButton);
        quitButton.setOnClickListener(c -> requireActivity().finishAffinity());

        todayDateTextView = view.findViewById(R.id.maiDatum);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.", Locale.getDefault());
        todayDateTextView.setText(sdf.format(new Date()));

        todayTilalmiIdoszakFishList = view.findViewById(R.id.tilalmiIdoszakosHalakLista);

        DatabaseHal databaseHal = new DatabaseHal(getActivity());
        databaseHal.fillLocalStore();

        ArrayList<ClassHal> fish = databaseHal.getAllDataFromLocalStore();;
        for (ClassHal hal: fish) {
            if(hal.isTilalmiIdoszakToday()){
                TextView fishListItem = new TextView(getActivity());
                fishListItem.setText(hal.getNev());
                fishListItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                fishListItem.setTextColor(getResources().getColor(R.color.black));
                fishListItem.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                fishListItem.setOnClickListener(view -> {
                    onClickFish(hal.getNev());
                });

                todayTilalmiIdoszakFishList.addView(fishListItem);
            }
        }
        return view;
    }

    private void onClickFish(String nev) {
        Toast.makeText(getActivity(), nev, Toast.LENGTH_SHORT).show();
    }

}