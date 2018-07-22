package com.roaimsapp.findit.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.roaimsapp.findit.data.model.Segment;

import java.util.List;

/**
 * Created by Roaim on 23-Jul-18.
 */

public class SegmentSpinnerAdapter implements SpinnerAdapter {

    private Context mContext;
    List<Segment> mList;

    public SegmentSpinnerAdapter(Context context, List<Segment> list) {
        mContext = context;
        this.mList = list;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Segment getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        Segment item = getItem(position);
        if (item != null && convertView instanceof TextView) {
            ((TextView)convertView).setText(item.getName());
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext)
                    .inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        }
        Segment item = getItem(i);
        if (item != null && view instanceof TextView) {
            ((TextView)view).setText(item.getName());
        }
        return view;
    }
}
