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
    private LinearLayout todayCloseSeasonalFishLinearLayout;
    private TextView todayCloseSeasonalFishTextView;
    private DatabaseFish databaseFish;
    private boolean showCloseSeasonalFish = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        TextView todayDateTextView = view.findViewById(R.id.today_s_date_textView);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.", Locale.getDefault());
        todayDateTextView.setText(sdf.format(new Date()));
        todayCloseSeasonalFishLinearLayout = view.findViewById(R.id.today_s_close_seasonal_fish_linearLayout);
        todayCloseSeasonalFishTextView = view.findViewById(R.id.today_s_close_seasonal_fish_textView);
        todayCloseSeasonalFishTextView.setOnClickListener(o -> changeVisibility());
        databaseFish = new DatabaseFish(getActivity());
        databaseFish.fillLocalStore();
        show();

        return view;
    }

    private void changeVisibility(){
        showCloseSeasonalFish = !showCloseSeasonalFish;
        show();
    }

    private void show() {

        if (showCloseSeasonalFish){
            ArrayList<ClassFish> fish = databaseFish.getAllDataFromLocalStore();
            for (ClassFish hal: fish) {
                if(hal.isCloseSeasonToday()){
                    TextView fishListItem = new TextView(getActivity());
                    fishListItem.setText(hal.getName());
                    fishListItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    fishListItem.setTextColor(getResources().getColor(R.color.black));
                    fishListItem.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    fishListItem.setOnClickListener(view -> onClickFish(hal.getName()));
                    todayCloseSeasonalFishLinearLayout.addView(fishListItem);
                    todayCloseSeasonalFishTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up, 0);
                }
            }
        } else {
            todayCloseSeasonalFishLinearLayout.removeAllViews();
            todayCloseSeasonalFishTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down, 0);
        }
    }

    private void onClickFish(String nev) {
        Intent intent = new Intent(getActivity(), FishDetailsActivity.class);
        intent.putExtra("fishName", nev);
        startActivity(intent);
    }

}