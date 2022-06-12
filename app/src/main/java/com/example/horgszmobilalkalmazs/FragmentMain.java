package com.example.horgszmobilalkalmazs;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FragmentMain extends Fragment {
    View view;
    TextView todayDateTextView;
    LinearLayout todayTilalmiIdoszakFishList;
    TextView todayTilalmiIdoszakosHalakTextView;
    DatabaseFish databaseFish;

    boolean showTilalmiIdoszakosHalakList = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);


        todayDateTextView = view.findViewById(R.id.maiDatum);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.", Locale.getDefault());
        todayDateTextView.setText(sdf.format(new Date()));

        todayTilalmiIdoszakFishList = view.findViewById(R.id.tilalmiIdoszakosHalakLista);
        todayTilalmiIdoszakosHalakTextView = view.findViewById(R.id.maiTilalmiIdoszakosHalakText);
        todayTilalmiIdoszakosHalakTextView.setOnClickListener(o -> changeTilalmiIdoszakosHalakVisibility());

        databaseFish = new DatabaseFish(getActivity());
        databaseFish.fillLocalStore();
        showTilalmiIdoszakosHalak();

        return view;
    }

    private void changeTilalmiIdoszakosHalakVisibility(){
        showTilalmiIdoszakosHalakList = !showTilalmiIdoszakosHalakList;
        showTilalmiIdoszakosHalak();
    }

    private void showTilalmiIdoszakosHalak() {

        if (showTilalmiIdoszakosHalakList){
            ArrayList<ClassFish> fish = databaseFish.getAllDataFromLocalStore();
            for (ClassFish hal: fish) {
                if(hal.isTilalmiIdoszakToday()){
                    TextView fishListItem = new TextView(getActivity());
                    fishListItem.setText(hal.getNev());
                    fishListItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    fishListItem.setTextColor(getResources().getColor(R.color.black));
                    fishListItem.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    fishListItem.setOnClickListener(view -> onClickFish(hal.getNev()));
                    todayTilalmiIdoszakFishList.addView(fishListItem);
                    todayTilalmiIdoszakosHalakTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up, 0);
                }
            }
        } else {
            todayTilalmiIdoszakFishList.removeAllViews();
            todayTilalmiIdoszakosHalakTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down, 0);
        }
    }

    private void onClickFish(String nev) {
        Intent intent = new Intent(getActivity(), FishDetailsActivity.class);
        intent.putExtra("fishName", nev);
        startActivity(intent);
    }

}