package com.andela.checkpoint.onestep.fragments;

import android.os.Bundle;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.checkpoint.onestep.R;
import com.andela.checkpoint.onestep.ui_helpers.recyclerView.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class LocationListFragment extends Fragment {
    private final String LOGS_BY_DAY = "Logs By Day";
    private final String LOGS_BY_LOCATION = "Logs By Location";
    private ViewPager mViewPager;
    private TabLayout mTabLayout;


    public LocationListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_list, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(mViewPager);

        mTabLayout = (TabLayout) view.findViewById(R.id.slidingTab);
        mTabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new LocationByDateFragment(), LOGS_BY_DAY);
        adapter.addFragment(new LocationByLocationFragment(), LOGS_BY_LOCATION);
        viewPager.setAdapter(adapter);
    }

}
