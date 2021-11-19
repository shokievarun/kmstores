package com.shokievarun.kmstores;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.shokievarun.kmstores.Model.Cart;
import com.shokievarun.kmstores.Model.Products;
import com.shokievarun.kmstores.Prevalent.Prevalent;
import com.shokievarun.kmstores.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyOrderProductsActivity extends AppCompatActivity {


    private RecyclerView productsList;
     private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference myOrdersRef;
    private TextView productTotalPrice;
    private int totalAmount = 0, totalPrices = 0;
    private  ArrayList<Products> tA;
    private int totalPrice;
    private String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_products);

        userID = getIntent().getStringExtra("uid");

        productsList = findViewById(R.id.products_listmy);
        productsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productsList.setLayoutManager(layoutManager);
        productTotalPrice = (TextView) findViewById(R.id.new_orders_txtViewmy);


        myOrdersRef = FirebaseDatabase.getInstance().getReference().child("My Orders")
                .child(Prevalent.currentOnlineUser.getPhone()).child(userID);



        ImageView backimage =(ImageView)findViewById(R.id.back_arrow_my_orders_products);


        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyOrderProductsActivity.this,HomeActivity.class);
                finish();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        totalAmountMyOrder();



        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(myOrdersRef, Cart.class)
                        .build();


        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {


                holder.txtProductQuantity.setText("* " + model.getQuantity());
                holder.txtProductRate.setText(model.getPrice());
                holder.txtProductName.setText(model.getPname());
                int oneProductSum = ((Integer.valueOf(model.getPrice())) * Integer.valueOf(model.getQuantity()));
                holder.txtProductSumPrice.setText(oneProductSum + " Rs/-");


                int oneTypeProductPrice = ((Integer.valueOf(model.getPrice())) * Integer.valueOf(model.getQuantity()));
                totalAmount = totalAmount + oneTypeProductPrice;

           //    productTotalPrice.setText("Total Amount:  " + totalAmount + " Rs/-");

            }


            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        productsList.setAdapter(adapter);
        adapter.startListening();

    }

    private void totalAmountMyOrder(){



        ValueEventListener eventListener= new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                tA=new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Products pro=new Products(ds.child("price").getValue(String.class),
                            ds.child("quantity").getValue(String.class));

                    tA.add(pro);
                }
                totalPrice=0;
                for(int i=0;i<tA.size();i++){
                    totalPrice = totalPrice+ ((Integer.valueOf(tA.get(i).getPrice()))*Integer.valueOf(tA.get(i).getQuantity()));
                }
                productTotalPrice.setText("Total Amount:  "+ totalPrice +" Rs/-");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myOrdersRef.addListenerForSingleValueEvent(eventListener);
    }




}