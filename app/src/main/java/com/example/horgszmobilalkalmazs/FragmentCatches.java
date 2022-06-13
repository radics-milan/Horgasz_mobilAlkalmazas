package com.example.horgszmobilalkalmazs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentCatches extends Fragment implements AdapterView.OnItemSelectedListener  {
    View view;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_catches, container, false);
        Spinner catchesSpinner = view.findViewById(R.id.catches_spinner);
        RecyclerView catchesRecyclerView = view.findViewById(R.id.catches_recyclerView);

        DatabaseFish databaseFish = new DatabaseFish(getContext());
        ArrayList<ClassFish> fishArrayList = databaseFish.getAllDataFromLocalStore();
        ArrayList<String> filterNames = new ArrayList<>();
        filterNames.add("Összes fogás");
        for (ClassFish fish: fishArrayList) {
            filterNames.add(fish.getNev());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, filterNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catchesSpinner.setAdapter(adapter);
        catchesSpinner.setSelection(0);
        catchesSpinner.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(), adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}