package com.roaimsapp.findit.adapter;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.roaimsapp.findit.data.model.Number;

public interface NumberClickListener {
    void onNumberClick(Number number);
    void onButtonClick(ImageView imageView, TextView textView, EditText editText, Number number);
}
