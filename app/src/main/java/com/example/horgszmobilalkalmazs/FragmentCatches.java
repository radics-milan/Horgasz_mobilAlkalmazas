package com.example.horgszmobilalkalmazs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentCatches extends Fragment implements AdapterView.OnItemSelectedListener  {
    View view;
    DatabaseCatch databaseCatch;
    ArrayList<ClassCatch> catchArray = new ArrayList<>();
    ArrayList<String> filterNames = new ArrayList<>();
    int gridNumber = 1;
    AdapterCatch adapterCatch;
    RecyclerView catchesRecyclerView;
    ImageView eyeImageView;
    ImageView addImageView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_catches, container, false);
        Spinner catchesSpinner = view.findViewById(R.id.catches_spinner);
        eyeImageView = view.findViewById(R.id.eyeImageView);
        eyeImageView.setOnClickListener(o -> onEyeImageViewClick());
        addImageView = view.findViewById(R.id.addImageView);
        addImageView.setOnClickListener(o -> goToAddCatchActivity());
        DatabaseFish databaseFish = new DatabaseFish(getContext());
        ArrayList<ClassFish> fishArrayList = databaseFish.getAllDataFromLocalStore();


        filterNames.add("Összes fogás");
        for (ClassFish fish: fishArrayList) {
            filterNames.add(fish.getNev());
        }

        databaseCatch = new DatabaseCatch(getContext());
//        databaseCatch.addCatch(new ClassCatch("2022.01.01.", "Angolna", "ASD", 20, 10, "Csalii", "Kecel"));
//        databaseCatch.addCatch(new ClassCatch("2022.01.02.", "Angolna", "ASD", 20, 10, "Csalii", "Kecel"));
//        databaseCatch.addCatch(new ClassCatch("2022.01.03.", "Angolna", "ASD", 20, 10, "Csalii", "Kecel"));
//        databaseCatch.addCatch(new ClassCatch("2022.01.04.", "Angolna", "ASD", 20, 10, "Csalii", "Kecel"));
//        databaseCatch.addCatch(new ClassCatch("2022.01.05.", "Angolna", "ASD", 20, 10, "Csalii", "Kecel"));
//        databaseCatch.addCatch(new ClassCatch("2022.01.06.", "Angolna", "ASD", 20, 10, "Csalii", "Kecel"));
//        databaseCatch.addCatch(new ClassCatch("2022.01.07.", "Angolna", "ASD", 20, 10, "Csalii", "Kecel"));
//        databaseCatch.addCatch(new ClassCatch("2022.01.08.", "Angolna", "ASD", 20, 10, "Csalii", "Kecel"));
//        databaseCatch.addCatch(new ClassCatch("2022.01.09.", "Angolna", "ASD", 20, 10, "Csalii", "Kecel"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, filterNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catchesSpinner.setAdapter(adapter);
        catchesSpinner.setSelection(0);
        catchesSpinner.setOnItemSelectedListener(this);

        catchesRecyclerView = view.findViewById(R.id.catches_recyclerView);
        catchesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), gridNumber));
        adapterCatch = new AdapterCatch(getActivity(), catchArray, gridNumber);
        catchesRecyclerView.setAdapter(adapterCatch);

        return view;
    }

    private void goToAddCatchActivity() {
        Intent intent = new Intent(getContext(), AddCatchActivity.class);
        startActivity(intent);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getItemAtPosition(i).toString().equals(filterNames.get(0))){
            catchArray.clear();
            catchArray.addAll(databaseCatch.getAllCatch());
            //Log.i(FragmentCatches.class.getName(), catchArray.get(0).getFishName());
        } else {
            catchArray.clear();
            catchArray.addAll(databaseCatch.getFishCatch(adapterView.getItemAtPosition(i).toString()));
        }

        if (catchArray.size() == 0){
            if ("Összes fogás".equals(adapterView.getItemAtPosition(i).toString())) {
                Toast.makeText(getContext(), "Még nincs rögzített fogás!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Még nincs " + adapterView.getItemAtPosition(i).toString() + " fogás!", Toast.LENGTH_SHORT).show();
            }

        }
        adapterCatch.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void onEyeImageViewClick() {
        gridNumber++;
        if (gridNumber > 3) {
            gridNumber = 1;
        }
        adapterCatch = new AdapterCatch(getActivity(), catchArray, gridNumber);
        catchesRecyclerView.setAdapter(adapterCatch);
        GridLayoutManager layoutManager = (GridLayoutManager) catchesRecyclerView.getLayoutManager();
        assert layoutManager != null;
        layoutManager.setSpanCount(gridNumber);
        Toast.makeText(getActivity(), "Nézet: " + gridNumber + " oszlopos", Toast.LENGTH_SHORT).show();
    }
}