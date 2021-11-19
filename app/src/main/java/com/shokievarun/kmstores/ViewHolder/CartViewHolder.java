package com.shokievarun.kmstores.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shokievarun.kmstores.Interface.ItemClickListener;
import com.shokievarun.kmstores.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

     public TextView txtProductName, txtProductSumPrice, txtProductQuantity,txtProductRate;
     private ItemClickListener itemClickListener;


    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName=itemView.findViewById(R.id.cart_product_name);
        txtProductSumPrice=itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity=itemView.findViewById(R.id.cart_product_quantity);
        txtProductRate=itemView.findViewById(R.id.cart_product_rate);


    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v, getAdapterPosition(),false);

    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }

}




