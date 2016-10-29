package com.kin.counter.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kin.counter.R;
import com.kin.counter.activities.AllCounterListActivity;
import com.kin.counter.activities.CounterItemActivity;
import com.kin.counter.activities.CounterListActivity;
import com.kin.counter.userDao.Counter;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter {
    private List<Counter> mCounterList;
    private Context mContext;

    public ListAdapter (Context context, List<Counter> counterList) {
        super();
        mContext = context;
        mCounterList = counterList;
    }

    public void setCounterList (List<Counter> counterList) {
        mCounterList = counterList;
    }

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
        return mCounterList.size();
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
            mItemNameTextView.setText(mCounterList.get(position).name);
            mItemNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CounterItemActivity.class);
                    intent.putExtra("Counter", mCounterList.get(position));
                    ((AppCompatActivity) mContext).startActivityForResult(intent, CounterListActivity.COUNTER_ITEM_REQUEST);
                }
            });

            mItemCountTextView.setText(mCounterList.get(position).count + "");

            final int incrementValue = mCounterList.get(position).increment;
            if (incrementValue == 1) {
                mPlusButton.setText("+");
                mMinusButton.setText("-");
            }
            else {
                mPlusButton.setText("+" + incrementValue);
                mMinusButton.setText("-" + incrementValue);
            }

            mPlusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCounterList.get(position).count += incrementValue;
                    AllCounterListActivity.db.update(mCounterList.get(position));
                    mItemCountTextView.setText(mCounterList.get(position).count + "");
                }
            });
            mMinusButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    int newValue = mCounterList.get(position).count - incrementValue;
                    if (newValue >= 0) {
                        mCounterList.get(position).count = newValue;
                        AllCounterListActivity.db.update(mCounterList.get(position));
                        mItemCountTextView.setText(mCounterList.get(position).count + "");
                    }
                }
            });
        }
    }
}
