package com.andela.checkpoint.onestep.ui_helpers.recyclerView.expandable_recyclerview;

import android.view.View;
import android.widget.TextView;

import com.andela.checkpoint.onestep.R;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

/**
 * Created by andela-jugba on 11/6/15.
 */
public class LocationParentViewHolder extends ParentViewHolder {
    TextView mDate;
    TextView mCount;
    public LocationParentViewHolder(View itemView) {
        super(itemView);
        mDate = (TextView) itemView.findViewById(R.id.textViewDate);
        mCount = (TextView) itemView.findViewById(R.id.textViewNumberOfLocations);
    }
}
