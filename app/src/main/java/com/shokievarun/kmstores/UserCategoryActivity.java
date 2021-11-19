package com.shokievarun.kmstores;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shokievarun.kmstores.Model.Products;
import com.shokievarun.kmstores.ViewHolder.MyRecyclerViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

public class UserCategoryActivity extends AppCompatActivity {


    private String category="";

    private ArrayList<Products> productsList;
    private DatabaseReference ProductsRef;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private int ms=1000;

    private ScrollView myScrollView;


    private AutoCompleteTextView txtSearch;
 //   private DatabaseReference searchRef;
 //   private RecyclerView searchList;
    private String searchInput;
    private static String  type = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_category);

        myScrollView=findViewById(R.id.mainScroll);

        myScrollView.setVerticalScrollBarEnabled(false);
        myScrollView.setHorizontalScrollBarEnabled(false);

     //   searchRef= FirebaseDatabase.getInstance().getReference().child("Products");
        txtSearch=(AutoCompleteTextView)findViewById(R.id.search_product_name_category);

        ProductsRef= FirebaseDatabase.getInstance().getReference().child("Products");

        recyclerView=(RecyclerView) findViewById(R.id.recycler_category);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            type = getIntent().getExtras().get("Admin").toString();
        }


        TextView textViewCat1=findViewById(R.id.txtviewcategory1);
        TextView textViewCat2=findViewById(R.id.txtviewcategory2);
        TextView textViewCat3=findViewById(R.id.txtviewcategory3);
        TextView textViewCat4=findViewById(R.id.txtviewcategory4);
        TextView textViewCat5=findViewById(R.id.txtviewcategory5);
        TextView textViewCat6=findViewById(R.id.txtviewcategory6);
        TextView textViewCat7=findViewById(R.id.txtviewcategory7);
        TextView textViewCat8=findViewById(R.id.txtviewcategory8);
        TextView textViewCat9=findViewById(R.id.txtviewcategory9);
        TextView textViewCat10=findViewById(R.id.txtviewcategory10);
        TextView textViewCat11=findViewById(R.id.txtviewcategory11);
        TextView textViewCat12=findViewById(R.id.txtviewcategory12);
        TextView textViewCat13=findViewById(R.id.txtviewcategory13);
        TextView textViewCat14=findViewById(R.id.txtviewcategory14);


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
      myOrdersListener();



        ImageView backimage =(ImageView)findViewById(R.id.back_arrow_category);
        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserCategoryActivity.this,HomeActivity.class);
                finish();
            }
        });

        Drawable drawable = getResources().getDrawable(R.color.white);

        Drawable drawable1 = getResources().getDrawable(R.color.grey);


        textViewCat1.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;
            public void onClick(View view) {

                textViewCat1.setBackgroundDrawable(drawable);
                new CountDownTimer(ms,50)
                {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        textViewCat1.setBackgroundDrawable(drawable1);
                    }
                }.start();

                myOrdersListener();
            }
        });


        textViewCat2.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;
            public void onClick(View view) {
                textViewCat2.setBackgroundDrawable(drawable);
                new CountDownTimer(ms,50)
                {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        textViewCat2.setBackgroundDrawable(drawable1);
                    }
                }.start();

                category="Masala Items";
                filterProducts(category);

            }
        });


        textViewCat3.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;
            public void onClick(View view) {
                textViewCat3.setBackgroundDrawable(drawable);
                new CountDownTimer(ms,50)
                {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        textViewCat3.setBackgroundDrawable(drawable1);
                    }
                }.start();
                category="Atta";
                filterProducts(category);
            }
        });


        textViewCat4.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;
            public void onClick(View view) {
                textViewCat4.setBackgroundDrawable(drawable);
                new CountDownTimer(ms,50)
                {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        textViewCat4.setBackgroundDrawable(drawable1);
                    }
                }.start();

                category="Biscuits";
                filterProducts(category);
            }
        });


        textViewCat5.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;
            public void onClick(View view) {
                textViewCat5.setBackgroundDrawable(drawable);
                new CountDownTimer(ms,50)
                {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        textViewCat5.setBackgroundDrawable(drawable1);
                    }
                }.start();

                category="Books";
                filterProducts(category);

            }
        });


        textViewCat6.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;
            public void onClick(View view) {
                textViewCat6.setBackgroundDrawable(drawable);
                new CountDownTimer(ms,50)
                {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        textViewCat6.setBackgroundDrawable(drawable1);
                    }
                }.start();

                category="Cosmetics";
                filterProducts(category);
            }
        });


        textViewCat7.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;
            public void onClick(View view) {
                textViewCat7.setBackgroundDrawable(drawable);
                new CountDownTimer(ms,50)
                {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        textViewCat7.setBackgroundDrawable(drawable1);
                    }
                }.start();
                category="Cooldrinks";
                filterProducts(category);
            }
        });


        textViewCat8.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;
            public void onClick(View view) {
                textViewCat8.setBackgroundDrawable(drawable);
                new CountDownTimer(ms,50)
                {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        textViewCat8.setBackgroundDrawable(drawable1);
                    }
                }.start();

                category="Detergents";
                filterProducts(category);
            }
        });


        textViewCat9.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;
            public void onClick(View view) {
                textViewCat9.setBackgroundDrawable(drawable);
                new CountDownTimer(ms,50)
                {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        textViewCat9.setBackgroundDrawable(drawable1);
                    }
                }.start();

                category="Oils";
                filterProducts(category);
            }
        });


        textViewCat10.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;
            public void onClick(View view) {
                textViewCat10.setBackgroundDrawable(drawable);
                new CountDownTimer(ms,50)
                {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        textViewCat10.setBackgroundDrawable(drawable1);
                    }
                }.start();

                category="Pulses";
                filterProducts(category);
            }
        });


        textViewCat11.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;
            public void onClick(View view) {
                textViewCat11.setBackgroundDrawable(drawable);
                new CountDownTimer(ms,50)
                {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        textViewCat11.setBackgroundDrawable(drawable1);
                    }
                }.start();

                category="Rice";
                filterProducts(category);
            }
        });


        textViewCat12.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;
            public void onClick(View view) {
                textViewCat12.setBackgroundDrawable(drawable);
                new CountDownTimer(ms,50)
                {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        textViewCat12.setBackgroundDrawable(drawable1);
                    }
                }.start();
                category="Vegetables";
                filterProducts(category);
            }
        });


        textViewCat13.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;
            public void onClick(View view) {
                textViewCat13.setBackgroundDrawable(drawable);
                new CountDownTimer(ms,50)
                {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        textViewCat13.setBackgroundDrawable(drawable1);
                    }
                }.start();

                category="Extras";
                filterProducts(category);
            }
        });


        textViewCat14.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;
            public void onClick(View view) {
                textViewCat14.setBackgroundDrawable(drawable);
                new CountDownTimer(ms,50)
                {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        textViewCat14.setBackgroundDrawable(drawable1);
                    }
                }.start();

                category="daily deals";
                filterProducts(category);
            }
        });




    }



    public void myOrdersListener() {
        productsList = new ArrayList<>();
        Query queryReflist=ProductsRef.orderByChild("productStatus").equalTo("Approved");
        ProductsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Products product = snapshot.getValue(Products.class);
                    productsList.add(product);

                }

                Collections.shuffle(productsList);
                adapter = new MyRecyclerViewAdapter(UserCategoryActivity.this, productsList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }




    public void filterProducts(String category) {
        productsList = new ArrayList<>();
        Query queryRef=ProductsRef.orderByChild("Category").equalTo(category);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Products product = snapshot.getValue(Products.class);
                    productsList.add(product);

                }

                Collections.shuffle(productsList);
                adapter = new MyRecyclerViewAdapter(UserCategoryActivity.this, productsList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
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
        ProductsRef.addListenerForSingleValueEvent(eventListener);
    }


    private void getProducts(String selection) {


        Query queryReflist=ProductsRef.orderByChild("pname").startAt(selection);
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
                    UserCategoryActivity.CustomAdapterCategory adapter=new UserCategoryActivity.CustomAdapterCategory(prodList,UserCategoryActivity.this);
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        queryReflist.addListenerForSingleValueEvent(eventListener);

    }


    public static class CustomAdapterCategory extends RecyclerView.Adapter<UserCategoryActivity.CustomAdapterCategory.ViewHolder> {

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


        public CustomAdapterCategory(ArrayList<Products> myDataset,Context mContext) {
            this.mDataset=myDataset;
            this.mContext=mContext;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public UserCategoryActivity.CustomAdapterCategory.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view=layoutInflater.inflate(R.layout.product_items_layout,parent,false);
            return new UserCategoryActivity.CustomAdapterCategory.ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(UserCategoryActivity.CustomAdapterCategory.ViewHolder holder, final int position) {

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
