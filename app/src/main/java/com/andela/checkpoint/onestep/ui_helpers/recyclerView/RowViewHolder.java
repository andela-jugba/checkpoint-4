package com.andela.checkpoint.onestep.ui_helpers.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.andela.checkpoint.onestep.R;


/**
 * Created by andela on 10/18/15.
 */

public class RowViewHolder extends RecyclerView.ViewHolder {

    TextView textViewValue;
    TextView textView;

    public RowViewHolder(View view) {
        super(view);
        this.textView = (TextView) view.findViewById(R.id.title);
        this.textViewValue = (TextView) view.findViewById(R.id.value);
    }
}

