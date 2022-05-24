package com.example.horgszmobilalkalmazs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

public class HalakFragment extends Fragment {
    View view;
    SearchView searchView;
    String LOG_TAG = HalakFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_halak, container, false);

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getActivity(), "Keresés: " +s , Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ImageView imageView = view.findViewById(R.id.eyeImageView);
        imageView.setOnClickListener(v -> Toast.makeText(getActivity(), "View changed", Toast.LENGTH_SHORT).show());
        return view;
    }

}