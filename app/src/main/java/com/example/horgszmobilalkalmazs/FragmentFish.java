package com.example.horgszmobilalkalmazs;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class FragmentFish extends Fragment implements AdapterView.OnItemSelectedListener {
    private int gridNumber = 1;
    private final String[] filterNames = {
            "Minden faj",
            "Őshonos, fogható faj",
            "Őshonos, időszakos felmentéssel fogható faj",
            "Idegenhonos faj",
            "Idegenhonos, invazív faj",
            "Védett faj"
    };
    AdapterFish adapterFish;
    ArrayList<ClassFish> fishArray = new ArrayList<>();
    RecyclerView recyclerView;
    DatabaseFish databaseFish;
    String filter = null;
    View view;

    @Override
    public void onResume() {
        super.onResume();
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setQuery("", false);
        searchView.clearFocus();
        searchView.onActionViewCollapsed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fish, container, false);

        if (savedInstanceState != null){
            gridNumber = savedInstanceState.getInt("gridNumber");
        }

        databaseFish = new DatabaseFish(view.getContext());
        ImageView imageView = view.findViewById(R.id.eyeImageView);
        imageView.setOnClickListener(v -> onEyeImageViewClick());

        Spinner spinner = view.findViewById(R.id.fishFilterSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, filterNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);


        recyclerView = view.findViewById(R.id.fishRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), gridNumber));
        adapterFish = new AdapterFish(getActivity(), fishArray, gridNumber);
        recyclerView.setAdapter(adapterFish);

        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(view.getContext(), "Keresés: " + s, Toast.LENGTH_SHORT).show();
                adapterFish.getFilter().filter(s);
                filter = s;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapterFish.getFilter().filter(s);
                filter = s;
                return false;
            }
        });
        return view;
    }

    private void onEyeImageViewClick() {
        gridNumber++;
        if (gridNumber > 3) {
            gridNumber = 1;
        }
        adapterFish = new AdapterFish(getActivity(), fishArray, gridNumber);
        recyclerView.setAdapter(adapterFish);
        GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        assert layoutManager != null;
        layoutManager.setSpanCount(gridNumber);
        adapterFish.getFilter().filter(filter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("gridNumber", gridNumber);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getItemAtPosition(i).toString()){
            case "Minden faj":
                fishArray.clear();
                fishArray.addAll(databaseFish.getAllDataFromLocalStore());
                break;
            case "Őshonos, fogható faj":
                fishArray.clear();
                fishArray.addAll(databaseFish.getOshonosFoghatoFajok());
                break;
            case "Őshonos, időszakos felmentéssel fogható faj":
                fishArray.clear();
                fishArray.addAll(databaseFish.getOshonosIdoszakosFelmentesselFoghatoFajok());
                break;
            case "Idegenhonos faj":
                fishArray.clear();
                fishArray.addAll(databaseFish.getIdegenhonosFajok());
                break;
            case "Idegenhonos, invazív faj":
                fishArray.clear();
                fishArray.addAll(databaseFish.getIdegenhonosInvazivFajok());
                break;
            case "Védett faj":
                fishArray.clear();
                fishArray.addAll(databaseFish.getVedettFajok());
                break;
        }
        adapterFish.notifyDataSetChanged();
        Toast.makeText(getActivity(), adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}