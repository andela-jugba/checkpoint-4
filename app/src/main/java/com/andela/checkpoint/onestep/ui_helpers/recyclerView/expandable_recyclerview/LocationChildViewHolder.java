package com.andela.checkpoint.onestep.ui_helpers.recyclerView.expandable_recyclerview;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.checkpoint.onestep.R;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import java.util.UUID;

/**
 * Created by andela-jugba on 11/6/15.
 */
public class LocationChildViewHolder extends ChildViewHolder implements View.OnClickListener {
    protected TextView mPlaceName;
    protected TextView mTime;
    protected UUID mUuid;

    public LocationChildViewHolder(View itemView) {
        super(itemView);
        mPlaceName = (TextView) itemView.findViewById(R.id.textViewLocationName);
        mTime = (TextView) itemView.findViewById(R.id.textViewTimeSpent);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        showToast("This works! " + mUuid, view.getContext());
    }

    protected void showToast(String text, Context context) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public void bindUuid(UUID uuid) {
        this.mUuid = uuid;
    }

}
