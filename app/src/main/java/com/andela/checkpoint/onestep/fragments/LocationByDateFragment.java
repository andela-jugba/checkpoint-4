package com.andela.checkpoint.onestep.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.checkpoint.onestep.R;
import com.andela.checkpoint.onestep.models.LocationHelper;
import com.andela.checkpoint.onestep.ui_helpers.recyclerView.DateRecyclerViewAdapter;
import com.andela.checkpoint.onestep.ui_helpers.recyclerView.expandable_recyclerview.LocationExpandableAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationByDateFragment extends Fragment {
    private static final String TAG = LocationByDateFragment.class.getSimpleName();
    RecyclerView mRecyclerView;
    LocationExpandableAdapter mLocationExpandableAdapter;
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

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateUI();
        return view;

    }


    private void updateUI() {
        if (mLocationExpandableAdapter == null) {
            if (mLocationHelper.getParentedLocation() != null) {
                mLocationExpandableAdapter = new LocationExpandableAdapter(getActivity(), mLocationHelper.getParentedLocation());
                mLocationExpandableAdapter.setCustomParentAnimationViewId(R.id.textViewDate);
                mLocationExpandableAdapter.setParentAndIconExpandOnClick(true);
                mRecyclerView.setAdapter(mLocationExpandableAdapter);
            }

        } else {
            mLocationExpandableAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

}
