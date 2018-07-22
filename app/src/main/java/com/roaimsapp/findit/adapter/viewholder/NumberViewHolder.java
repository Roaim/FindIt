package com.roaimsapp.findit.adapter.viewholder;

import com.roaimsapp.findit.adapter.NumberClickListener;
import com.roaimsapp.findit.data.model.Number;
import com.roaimsapp.findit.databinding.ItemNumberBinding;

/**
 * Created by Roaim on 22-Jul-18.
 */

public class NumberViewHolder extends BaseViewHolder<ItemNumberBinding> {

    public NumberViewHolder(ItemNumberBinding binding, NumberClickListener mListener) {
        super(binding);
        binding.setListener(mListener);
    }

    public void bind(Number number) {
        number.setSerial(getAdapterPosition());
        binding.setNumber(number);
        binding.executePendingBindings();
    }

}
