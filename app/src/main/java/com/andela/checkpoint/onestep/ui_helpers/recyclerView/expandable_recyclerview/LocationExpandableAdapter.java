package com.andela.checkpoint.onestep.ui_helpers.recyclerView.expandable_recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.checkpoint.onestep.R;
import com.andela.checkpoint.onestep.models.Location;
import com.andela.checkpoint.onestep.models.LocationParent;
import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by andela-jugba on 11/6/15.
 */
public class LocationExpandableAdapter extends ExpandableRecyclerAdapter<LocationParentViewHolder, LocationChildViewHolder> {
    private LayoutInflater mInflater;
    private List<Object> mList;

    public LocationExpandableAdapter(Context context, List parentItemList) {
        super(context, parentItemList);
        this.mList = parentItemList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public LocationParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.single_location_by_date, viewGroup, false);
        return new LocationParentViewHolder(view);
    }

    @Override
    public LocationChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.location_list_child_view, viewGroup, false);
        return new LocationChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(LocationParentViewHolder locationParentViewHolder, int i, Object parentObject) {
        LocationParent location = (LocationParent) parentObject;
        DateFormat dateFormat = new SimpleDateFormat("EEEE dd,MMM,yyyy");
        locationParentViewHolder.mDate.setText(dateFormat.format(location.getDate()));
        locationParentViewHolder.mCount.setText(String.valueOf(location.getCount()));
    }

    @Override
    public void onBindChildViewHolder(LocationChildViewHolder locationChildViewHolder, int i, Object parentObject) {
        Location location = (Location) parentObject;
        locationChildViewHolder.mPlaceName.setText(location.getName());
        locationChildViewHolder.mTime.setText(String.valueOf(location.getTimesVisited()));
        locationChildViewHolder.bindUuid(location.getID());
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        } else return super.getItemCount();
    }
}
