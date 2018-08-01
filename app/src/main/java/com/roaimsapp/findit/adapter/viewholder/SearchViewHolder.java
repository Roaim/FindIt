package com.roaimsapp.findit.adapter.viewholder;

import com.roaimsapp.findit.data.model.Number;
import com.roaimsapp.findit.databinding.ItemSearchBinding;

/**
 * Created by Roaim on 22-Jul-18.
 */

public class SearchViewHolder extends BaseViewHolder<ItemSearchBinding> {

    public SearchViewHolder(ItemSearchBinding binding) {
        super(binding);
    }

    public void bind(Number number, int length) {
//        binding.setNumber(number);
        binding.setPrev(number.getPrev());
        switch (length) {
            case 1:
                binding.setSearch(number.getNumber());
                binding.setNext(number.getNext1());
                break;
            case 2:
                binding.setSearch(number.getNumber() + " " + number.getNext1());
                binding.setNext(number.getNext2());
                break;
            case 3:
                binding.setSearch(number.getNumber() + " " + number.getNext1() + " " + number.getNext2());
                binding.setNext(number.getNext3());
        }
        binding.executePendingBindings();
    }

}
