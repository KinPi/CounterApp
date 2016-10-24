package com.kin.counter.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kin.counter.R;
import com.kin.counter.activities.CounterListActivity;

public class ListAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return CounterListActivity.counterList.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemNameTextView;
        private TextView mItemCountTextView;
        private Button mPlusButton;
        private Button mMinusButton;

        public ListViewHolder(View itemView) {
            super(itemView);
            mItemNameTextView = (TextView) itemView.findViewById(R.id.ItemNameTextView);
            mItemCountTextView = (TextView) itemView.findViewById(R.id.ItemCountTextView);
            mPlusButton = (Button) itemView.findViewById(R.id.plusButton);
            mMinusButton = (Button) itemView.findViewById(R.id.minusButton);
        }

        public void bindView (final int position) {
            mItemNameTextView.setText(CounterListActivity.counterList.get(position).name);
            mItemCountTextView.setText(CounterListActivity.counterList.get(position).count + "");

            final int stepValue = CounterListActivity.counterList.get(position).step;
            if (stepValue != 1) {
                mPlusButton.setText("+" + stepValue);
                mMinusButton.setText("-" + stepValue);
            }

            mPlusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CounterListActivity.counterList.get(position).count += stepValue;
                    CounterListActivity.db.update(CounterListActivity.counterList.get(position));
                    mItemCountTextView.setText(CounterListActivity.counterList.get(position).count + "");
                }
            });
            mMinusButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    int newValue = CounterListActivity.counterList.get(position).count - stepValue;
                    if (newValue >= 0) {
                        CounterListActivity.counterList.get(position).count = newValue;
                        CounterListActivity.db.update(CounterListActivity.counterList.get(position));
                        mItemCountTextView.setText(CounterListActivity.counterList.get(position).count + "");
                    }
                }
            });
        }
    }
}
