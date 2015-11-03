package com.andela.checkpoint.onestep.ui_helpers.recyclerView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.andela.checkpoint.onestep.R;
import com.andela.checkpoint.onestep.models.Location;

import java.util.Date;
import java.util.List;

/**
 * Created by andela-jugba on 11/3/15.
 */
public class SingleDateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Date mDateOb;

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter adapter;

    private TextView mDate;
    private TextView mCount;
    private Context mContext;

    private List<Location> mlist;

    private View mRecycleViewHolder;

    public SingleDateViewHolder(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

        this.mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);

        this.mDate = (TextView) itemView.findViewById(R.id.textViewDate);
        this.mCount = (TextView) itemView.findViewById(R.id.textViewNumberOfLocations);
        this.mContext = itemView.getContext();
        this.mRecycleViewHolder = itemView.findViewById(R.id.recyclerViewHolder);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    public void bindDate(String date, List<Location> list) {
//        mDateOb = date;
        if (date != null) {
            mDate.setText(date.toString());
            mCount.setText(String.valueOf(list.size()));
            mlist = list;
        }

    }

    public void updateUI() {
        if (adapter == null) {
            adapter = new RecyclerViewAdapter(mContext, mlist);
            mRecyclerView.setAdapter(adapter);

        } else {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onClick(View view) {
        if (mRecycleViewHolder.getVisibility() == View.INVISIBLE) {
            mRecycleViewHolder.setVisibility(View.VISIBLE);
        } else {
            mRecycleViewHolder.setVisibility(View.INVISIBLE);
            mRecycleViewHolder.animate();
        }
    }
}
