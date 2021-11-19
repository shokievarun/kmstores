package com.shokievarun.kmstores.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shokievarun.kmstores.HomeActivity;
import com.shokievarun.kmstores.Model.Products;
import com.shokievarun.kmstores.ProductDetailsActivity;
import com.shokievarun.kmstores.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private final List<Products> mData;
    private LayoutInflater mInflater;
 //   private ItemClickListener mClickListener;
    public TextView txtProductName,txtProductDescription,txtProductPrice;
    public ImageView imageView;
    public ItemClickListener mClickListener ;
    private Products model;
    private String type="";




    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, List<Products> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }


    // inflates the row layout from xml when needed
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout,parent,false);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

        Products pro=mData.get(position);
        holder.txtProductName.setText(pro.getPname());
        holder.txtProductPrice.setText("Price : "+pro.getPrice()+" Rs/-");
        holder.txtProductDescription.setText(pro.getDescription());
        Picasso.get().load(pro.getImage()).into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ProductDetailsActivity.class);
                    intent.putExtra("pid", pro.getPid());
                    v.getContext().startActivity(intent);


            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView) itemView.findViewById(R.id.product_image);
            txtProductName=(TextView) itemView.findViewById(R.id.product_name);
            txtProductPrice=(TextView) itemView.findViewById(R.id.product_price);
            txtProductDescription=(TextView) itemView.findViewById(R.id.product_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Products getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(HomeActivity itemClickListener) {
        this.mClickListener = (ItemClickListener) itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}