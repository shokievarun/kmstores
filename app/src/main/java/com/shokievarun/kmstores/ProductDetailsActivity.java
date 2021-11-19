package com.shokievarun.kmstores;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shokievarun.kmstores.Model.Products;
import com.shokievarun.kmstores.Prevalent.Prevalent;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private Button addTCartButton;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private android.widget.TextView productPrice,productDescription,productName;
    private String name,price,description;
    private  String productID="", state="Normal";
    private DatabaseReference ref,stateRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

      productID=getIntent().getStringExtra("pid");

        ref=FirebaseDatabase.getInstance().getReference("Products");
        Query query=ref.orderByChild("pid").equalTo(productID);




        addTCartButton=(Button)findViewById(R.id.pd_add_to_cart_btn);
        productImage=(ImageView) findViewById(R.id.product_image_details);
        numberButton=(ElegantNumberButton)findViewById(R.id.number_btn);
        productName=(android.widget.TextView) findViewById(R.id.product_name_details);
        productDescription=(android.widget.TextView) findViewById(R.id.product_description_details);
        productPrice=(android.widget.TextView) findViewById(R.id.product_price_details);


        getProductDetails(productID);

        addTCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();

               /* if(state.equals("Order Placed") || state.equals("not Shipped"))
                {
                    Toast.makeText(ProductDetailsActivity.this,"You can purchase more products once your previous order is delivered",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    addingToCartList();

                }*/
            }
        });

    }

    protected void onStart() {
        super.onStart();
     //   checkOrderState();
    }

    private void addingToCartList() {

        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate= Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("dd MMM,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());



        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String,Object> cartMap = new HashMap<>();



        cartMap.put("pid",productID);
        cartMap.put("pname",productName.getText().toString());
        cartMap.put("price",productPrice.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",numberButton.getNumber());
        cartMap.put("discount","");



        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                .child("Products").child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            cartListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                                    .child("Products").child(productID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(ProductDetailsActivity.this, "Added to Cart List", Toast.LENGTH_SHORT).show();
                                             //   Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                                              //  startActivity(intent);
                                                finish();
                                            }
                                        }
                                    }); 
                        }
                    }
                });
    }





    private void getProductDetails(String productID) {

        DatabaseReference productsRef= FirebaseDatabase.getInstance().getReference().child("Products");
        Query query=ref.orderByChild("pid").equalTo(productID);

        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    Products products=snapshot.getValue(Products.class);
                    name=products.getPname();
                    price=products.getPrice();
                    description=products.getDescription();


                    productName.setText(name);
                    productPrice.setText(price);
                    productDescription.setText(description);
                    Picasso.get().load(products.getImage()).into(productImage);




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void checkOrderState()
    {
        DatabaseReference ordersRef;
        ordersRef=FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String shippingState= snapshot.child("state").getValue().toString();

                    if(shippingState.equals("shipped"))
                    {

                        state="Order Shipped";

                    }
                    else if(shippingState.equals("not Shipped"))
                    {

                        state="Order Placed";

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}