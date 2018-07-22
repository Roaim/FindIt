package com.roaimsapp.findit.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.roaimsapp.findit.R;
import com.roaimsapp.findit.adapter.viewholder.NumberViewHolder;
import com.roaimsapp.findit.data.model.Number;
import com.roaimsapp.findit.databinding.ItemNumberBinding;

import java.util.List;

/**
 * Created by Roaim on 21-Jul-18.
 */

public class NumberAdapter extends RecyclerView.Adapter<NumberViewHolder> {
    private List<Number> mList;
    private NumberClickListener mListener;

    public NumberAdapter(List<Number> list, NumberClickListener listener) {
        mList = list;
        this.mListener = listener;
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemNumberBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_number, parent, false);
        return new NumberViewHolder(binding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        Number number = getNumber(position);
        holder.bind(number);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public Number getNumber(int position) {
        return mList.get(position);
    }

    public int getIndexOf(Number number) {
        return mList.indexOf(number);
    }

    public void update(Number number, int index) {
        mList.set(index, number);
    }

    public void update(int startIndex, Number... numbers) {
        for (int i = 0; i < numbers.length; i++) {
            int index = startIndex - 1 - i;
            mList.set(index, numbers[i]);
        }
    }

    public void update(Number... numbers) {
        for (int i = 0; i < numbers.length; i++) {
            int index = mList.size() - 1 - i;
            mList.set(index, numbers[i]);
        }
    }

    public void addAllNumbers(List<Number> list) {
        if (!mList.isEmpty()) {
            mList.removeAll(mList);
        }
        mList.addAll(list);
    }

    public void addNumber(Number number) {
        mList.add(number);
    }

    public void removeNumber(Number number) {
        mList.remove(number);
    }

    @Override
    public void onViewDetachedFromWindow(NumberViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unBind();
    }
}
