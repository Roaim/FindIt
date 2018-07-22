package com.roaimsapp.findit.adapter.viewholder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Roaim on 22-Jul-18.
 */

public class BaseViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public B binding;

    public BaseViewHolder(B binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void unBind() {
        if (binding != null) {
            binding.unbind();
        }
    }
}
