package com.andela.checkpoint.onestep.ui_helpers.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.checkpoint.onestep.R;
import com.andela.checkpoint.onestep.models.Location;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andela-jugba on 11/03/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RowViewHolder> {
    Context context;
    List<Location> itemsList;

    public RecyclerViewAdapter(Context context, List<Location> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        } else {
            return itemsList.size();
        }
    }

    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.single_row, null);
        RowViewHolder viewHolder = new RowViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RowViewHolder rowViewHolder, int position) {
        Location items = itemsList.get(position);
        rowViewHolder.textView.setText(String.valueOf(items.getName()));
        DateFormat dateFormat = new SimpleDateFormat("EEEE dd,MMM,yyyy");
        rowViewHolder.textViewCurrency.setText(dateFormat.format(items.getDate()));
    }

}
