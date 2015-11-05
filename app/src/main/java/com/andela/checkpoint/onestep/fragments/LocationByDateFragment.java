package com.andela.checkpoint.onestep.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.checkpoint.onestep.R;
import com.andela.checkpoint.onestep.models.Location;
import com.andela.checkpoint.onestep.models.LocationHelper;
import com.andela.checkpoint.onestep.ui_helpers.recyclerView.DateRecyclerViewAdapter;
import com.andela.checkpoint.onestep.ui_helpers.recyclerView.RecyclerViewAdapter;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationByDateFragment extends Fragment {
    private static final String TAG = LocationByDateFragment.class.getSimpleName();
    RecyclerView recyclerView;
    DateRecyclerViewAdapter adapter;
    LocationHelper mLocationHelper;

    public LocationByDateFragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_by_date, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        updateUI();
        return view;
    }


    private void updateUI() {
        if (adapter == null) {
            adapter = new DateRecyclerViewAdapter(mLocationHelper.getLocationByDate());
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
