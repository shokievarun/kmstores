package com.shokievarun.kmstores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.shokievarun.kmstores.Model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProductsActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText name,price,description;
    private Button applyChangesBtn,deleteBtn;
    private android.widget.TextView productPrice,productDescription,productName;
    private ImageView productImage;
    private  String productID="";
    private DatabaseReference productsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_products);

        productID=getIntent().getStringExtra("pid");
        productsRef= FirebaseDatabase.getInstance().getReference().child("Products");
        Query query=productsRef.orderByChild("pid").equalTo(productID);

        applyChangesBtn=(Button)findViewById(R.id.apply_changes_btn);
        deleteBtn=(Button)findViewById(R.id.delete_product_btn);
        name=(EditText)findViewById(R.id.product_name_maintain);
        price=(EditText)findViewById(R.id.product_price_maintain);
        description=(EditText)findViewById(R.id.product_description_maintain);
        imageView=(ImageView)findViewById(R.id.product_image_maintain);

        displaySpecificProductInfo(productID);

        ImageView backimage =(ImageView)findViewById(R.id.back_arrow_aamp);


        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         //       Intent intent=new Intent(CartActivity.this,HomeActivity.class);
                finish();
            }
        });

        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChanges(productID);
            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteThisProduct(productID);
            }
        });

    }

    private void deleteThisProduct(String productID) {

        productsRef.child(productID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Intent intent= new Intent(AdminMaintainProductsActivity.this,AdminHomeActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);
                finish();

                Toast.makeText(AdminMaintainProductsActivity.this,"Product deleted Successfully...",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void applyChanges(String productID) {

        String pName= name.getText().toString().toLowerCase();
        String pPrice=   price.getText().toString().toLowerCase();
        String pDescription= description.getText().toString().toLowerCase();

        if(pName.equals(""))
        {
            Toast.makeText(AdminMaintainProductsActivity.this,"Write down Product Name...",Toast.LENGTH_SHORT).show();
        }
        else if(pPrice.equals(""))
        {
            Toast.makeText(AdminMaintainProductsActivity.this,"Write down Product Price...",Toast.LENGTH_SHORT).show();
        }
        else if(pDescription.equals(""))
        {
            Toast.makeText(AdminMaintainProductsActivity.this,"Write down Product Description...",Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> productMap=new HashMap<>();
            productMap.put("pid",productID);
            productMap.put("description",pDescription);
            productMap.put("price",pPrice);
            productMap.put("pname",pName);

            productsRef.child(productID).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(AdminMaintainProductsActivity.this,"Changes Applied Successfully...",Toast.LENGTH_SHORT).show();

                        Intent intent= new Intent(AdminMaintainProductsActivity.this,AdminHomeActivity.class);
                        intent.putExtra("Admin","Admin");
                        startActivity(intent);
                        finish();

                    }

                }
            });
        }
    }

    private void displaySpecificProductInfo(String productID) {
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {

                    if(snapshot.exists()) {

                        Products products=snapshot.getValue(Products.class);



                        String pName = products.getPname();
                        String pPrice = products.getPrice();
                        String pDescription = products.getDescription();
                        String pImage = products.getImage();

                        name.setText(pName);
                        price.setText(pPrice);
                        description.setText(pDescription);
                        Picasso.get().load(pImage).into(imageView);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}