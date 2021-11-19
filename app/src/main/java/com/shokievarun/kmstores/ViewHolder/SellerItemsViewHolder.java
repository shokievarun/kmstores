package com.shokievarun.kmstores.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shokievarun.kmstores.Interface.ItemClickListener;
import com.shokievarun.kmstores.R;

public class SellerItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName,txtProductDescription,txtProductPrice,txtProductStatus;
    public ImageView imageView;
    public ItemClickListener listener;


    public SellerItemsViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView=(ImageView) itemView.findViewById(R.id.product_seller_image);
        txtProductName=(TextView) itemView.findViewById(R.id.product_seller_name);
        txtProductPrice=(TextView) itemView.findViewById(R.id.product_seller_price);
        txtProductDescription=(TextView) itemView.findViewById(R.id.product_seller_description);
        txtProductStatus=(TextView) itemView.findViewById(R.id.product_status);


    }

    public void setItemClickListener(ItemClickListener listener)
    {
        this.listener=listener;
    }


    @Override
    public void onClick(View view) {

        listener.onClick(view, getAdapterPosition(), false);
    }
}
