package com.shokievarun.kmstores;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shokievarun.kmstores.Model.AdminOrders;
import com.shokievarun.kmstores.Model.Cart;
import com.shokievarun.kmstores.ViewHolder.CartViewHolder;

import java.util.ArrayList;

public class AdminOrdersUserProductsActivity extends AppCompatActivity {



        private RecyclerView productsListAdmin;
        private DatabaseReference AdminOrderUserDetailsRefup;
        private RecyclerView.LayoutManager layoutManager;
        private DatabaseReference AdminOrderProductsRef;
        private TextView productTotalPriceAdmin;
        private int totalAmount = 0, totalPrices = 0;
        private ArrayList<Cart> tA;
        private int totalPrice;
        private String userID = "";

    private TextView usernameavtxtvup,userphoneavtxtvup,useraddressavtxtvup,totalAmountavtxtvup,dateavtxtvup,extraItemsavtxtvup,transavtxtvup;

    private String usernameavup,userphoneavup,useraddressavup,totalAmountavup,dateavup,timeavup,extraItemsavup,transvup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders_user_products);

            userID = getIntent().getStringExtra("uid");

        usernameavtxtvup=(TextView)findViewById(R.id.order_user_nameavup);
        userphoneavtxtvup=(TextView)findViewById(R.id.order_user_phone_numberavup);
        useraddressavtxtvup=(TextView)findViewById(R.id.order_user_address_cityavup);
        totalAmountavtxtvup=(TextView)findViewById(R.id.order_total_avup);
        dateavtxtvup=(TextView)findViewById(R.id.order_date_timeavup);
        extraItemsavtxtvup=(TextView)findViewById(R.id.order_extraavup);
        transavtxtvup=(TextView)findViewById(R.id.order_transvup);



        AdminOrderUserDetailsRefup = FirebaseDatabase.getInstance().getReference().child("Orders Backup")
                .child(userID).child("User details");

        getUserDataup();

            productsListAdmin = findViewById(R.id.admin_orders_itemlist);
            productsListAdmin.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            productsListAdmin.setLayoutManager(layoutManager);
            productTotalPriceAdmin = (TextView) findViewById(R.id.admin_item_total);


            AdminOrderProductsRef = FirebaseDatabase.getInstance().getReference().child("Orders Backup")
                    .child(userID).child("Items list");



            ImageView backimage =(ImageView)findViewById(R.id.back_arrow_admin_order_products);


            backimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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
                            .setQuery(AdminOrderProductsRef, Cart.class)
                            .build();


            FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {


                    holder.txtProductQuantity.setText("*  "+model.getQuantity());
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

            productsListAdmin.setAdapter(adapter);
            adapter.startListening();

        }

        private void totalAmountMyOrder(){



            ValueEventListener eventListener= new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    tA=new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                       /* Products pro=new Products(ds.child("price").getValue(String.class),
                                ds.child("quantity").getValue(String.class));*/

                        Cart cart=new Cart(ds.child("pname").getValue(String.class),
                                ds.child("price").getValue(String.class),
                                ds.child("quantity").getValue(String.class));


                        tA.add(cart);
                    }
                    totalPrice=0;
                    for(int i=0;i<tA.size();i++){
                        totalPrice = totalPrice+ ((Integer.valueOf(tA.get(i).getPrice()))*Integer.valueOf(tA.get(i).getQuantity()));

                            String name=tA.get(i).getPname();
                            String price=tA.get(i).getPrice();
                            String quantity=tA.get(i).getQuantity();

                            Log.i("name",name);
                            Log.i("name",price);
                            Log.i("name",quantity);


                    }
                    productTotalPriceAdmin.setText("Total Amount:  "+ totalPrice +" Rs/-");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            AdminOrderProductsRef.addListenerForSingleValueEvent(eventListener);


    }


    private void getUserDataup() {




        AdminOrderUserDetailsRefup.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //here you will get the data
                        usernameavup = dataSnapshot.getValue(AdminOrders.class).getUsername();
                        userphoneavup = dataSnapshot.getValue(AdminOrders.class).getUserphone();
                        useraddressavup = dataSnapshot.getValue(AdminOrders.class).getUseraddress();
                        totalAmountavup = dataSnapshot.getValue(AdminOrders.class).getTotalAmount();
                        dateavup = dataSnapshot.getValue(AdminOrders.class).getDate();
                        timeavup = dataSnapshot.getValue(AdminOrders.class).getTime();
                        extraItemsavup = dataSnapshot.getValue(AdminOrders.class).getExtra();
                        transvup=dataSnapshot.getValue(AdminOrders.class).getTransactionId();

                        String keytime=dateavup+" "+timeavup;
                        usernameavtxtvup.setText("Name: "+usernameavup);
                        userphoneavtxtvup.setText("Phone: "+userphoneavup);
                        useraddressavtxtvup.setText("Address: "+useraddressavup);
                        totalAmountavtxtvup.setText("Total Amount: "+totalAmountavup+" Rs/-");
                        dateavtxtvup.setText("Ordered on: "+keytime);
                        extraItemsavtxtvup.setText("Extra Items: "+extraItemsavup);
                        transavtxtvup.setText("Transaction ID: "+transvup);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {



                    }
                });
    }




}