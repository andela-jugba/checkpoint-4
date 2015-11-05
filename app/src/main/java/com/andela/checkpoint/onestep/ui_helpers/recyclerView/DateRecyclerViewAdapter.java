package com.andela.checkpoint.onestep.ui_helpers.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.checkpoint.onestep.R;
import com.andela.checkpoint.onestep.models.Location;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andela-jugba on 11/3/15.
 */
public class DateRecyclerViewAdapter extends RecyclerView.Adapter<SingleDateViewHolder> {
    private HashMap<String, List<Location>> mListOfDates;
    private static Map<Integer, String> idMap;

    public DateRecyclerViewAdapter(HashMap<String, List<Location>> listHashMap) {
        mListOfDates = listHashMap;
        generateIdMap();
    }

    private void generateIdMap() {
        HashMap<Integer, String> tempMap = new HashMap<>();
        if(mListOfDates != null) {
            int temp = 0;
            for (Map.Entry<String, List<Location>> entry : mListOfDates.entrySet()) {
                tempMap.put(temp, entry.getKey());
                temp++;
            }
            idMap = tempMap;
        }
    }

    @Override
    public SingleDateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_location_by_date, null);
        return new SingleDateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SingleDateViewHolder holder, int position) {
        String date = idMap.get(position);
        holder.bindDate(date, mListOfDates.get(date));
        holder.updateUI();
    }

    @Override
    public int getItemCount() {
        if (mListOfDates == null) {
            return 0;
        } else {
            return mListOfDates.size();
        }
    }
}
