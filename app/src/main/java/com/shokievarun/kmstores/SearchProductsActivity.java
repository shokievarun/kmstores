package com.shokievarun.kmstores;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shokievarun.kmstores.Model.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchProductsActivity extends AppCompatActivity {



    private AutoCompleteTextView txtSearch;
    private DatabaseReference searchRef;
    private RecyclerView searchList;
    private String searchInput;
    private static String  type = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);


        searchRef= FirebaseDatabase.getInstance().getReference().child("Products");
        txtSearch=(AutoCompleteTextView)findViewById(R.id.search_product_name);
        searchList = (RecyclerView)findViewById(R.id.search_list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        searchList.setLayoutManager(layoutManager);

        ImageView backimage =(ImageView)findViewById(R.id.back_arrow_asp);


        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     Intent intent=new Intent(CartActivity.this,HomeActivity.class);
                finish();
            }
        });


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            type = getIntent().getExtras().get("Admin").toString();
        }


        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchInput = txtSearch.getText().toString().toLowerCase();
                    getProducts(searchInput);
                    return true;
                }
                return false;
            }
        });

      populateSearch();

    }



    private void populateSearch(){


        ValueEventListener eventListener= new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> names=new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String n=snapshot.child("pname").getValue(String.class);
                    names.add(n);

                }
                ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.custom_autocomplete,names);


                txtSearch.setAdapter(adapter);
                txtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selection=parent.getItemAtPosition(position).toString();
                        getProducts(selection);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        searchRef.addListenerForSingleValueEvent(eventListener);
    }


    private void getProducts(String selection) {


        Query queryReflist=searchRef.orderByChild("pname").startAt(selection);
        ValueEventListener eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    ArrayList<Products> prodList=new ArrayList<>();
                    for(DataSnapshot ds:snapshot.getChildren())
                    {
                        //chech if error
                        Products pro=new Products(ds.child("pname").getValue(String.class),
                                ds.child("price").getValue(String.class),
                                ds.child("image").getValue(String.class),
                                ds.child("description").getValue(String.class),
                                ds.child("pid").getValue(String.class));
                        prodList.add(pro);

                    }
                    CustomAdapter adapter=new CustomAdapter(prodList,SearchProductsActivity.this);
                    searchList.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        queryReflist.addListenerForSingleValueEvent(eventListener);

    }


    public static class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private ArrayList<Products> mDataset;
        private Context mContext;


        public static class ViewHolder extends RecyclerView.ViewHolder {

            TextView  txtProductNames,txtProductPrices,txtProductDescriptions;
             ImageView imageViews;

            public ViewHolder(View itemView) {
                super(itemView);

                imageViews=(ImageView) itemView.findViewById(R.id.product_image);
                txtProductNames=(TextView) itemView.findViewById(R.id.product_name);
                txtProductPrices=(TextView) itemView.findViewById(R.id.product_price);
                txtProductDescriptions=(TextView) itemView.findViewById(R.id.product_description);

            }
        }


        public CustomAdapter(ArrayList<Products> myDataset,Context mContext) {
            this.mDataset=myDataset;
            this.mContext=mContext;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view=layoutInflater.inflate(R.layout.product_items_layout,parent,false);
            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            Products pro=mDataset.get(position);
         //   String pid=pro.getPid();
            holder.txtProductNames.setText(pro.getPname());
            holder.txtProductPrices.setText("Price : Rs "+pro.getPrice());
            holder.txtProductDescriptions.setText(pro.getDescription());
            Picasso.get().load(pro.getImage()).into(holder.imageViews);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(type.equals("Admin"))
                    {
                        Intent intent = new Intent(mContext, AdminMaintainProductsActivity.class);
                        intent.putExtra("pid",pro.getPid());
                        mContext.startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(mContext, ProductDetailsActivity.class);
                        intent.putExtra("pid", pro.getPid());
                        mContext.startActivity(intent);
                    }


                }
            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    }
