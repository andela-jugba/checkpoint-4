package com.andela.checkpoint.onestep.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.checkpoint.onestep.R;
import com.andela.checkpoint.onestep.models.Location;
import com.andela.checkpoint.onestep.models.LocationHelper;
import com.andela.checkpoint.onestep.ui_helpers.recyclerView.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Currency;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationByLocationFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    LocationHelper mLocationHelper;

    public LocationByLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationHelper = LocationHelper.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_location_by_location, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        updateUI();
        return v;
    }

    private void updateUI() {
        if (adapter == null) {
            adapter = new RecyclerViewAdapter(getContext(), mLocationHelper.getLocations());
            recyclerView.setAdapter(adapter);

        } else {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


}
