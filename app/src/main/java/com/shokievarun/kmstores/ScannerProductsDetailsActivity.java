package com.shokievarun.kmstores;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shokievarun.kmstores.Prevalent.Prevalent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ScannerProductsDetailsActivity extends AppCompatActivity {

    private Button addTCartButton2;
    private String productID,productText;
    private String productNamee="";
    private String productPricee="";
    private ElegantNumberButton numberButton2;
    private android.widget.TextView productPrice2,productName2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_products_details);

        addTCartButton2=(Button)findViewById(R.id.pd_add_to_cart_btn2);

        numberButton2=(ElegantNumberButton)findViewById(R.id.number_btn2);
        productName2=(android.widget.TextView) findViewById(R.id.product_name_details2);

        productPrice2=(android.widget.TextView) findViewById(R.id.product_price_details2);

        productText=getIntent().getStringExtra("productText");

        sort();



        addTCartButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();

            }
        });
    }

    private void sort(){

        char[] ch = new char[productText.length()];

        for(int i=0;i<productText.length();i++){
            ch[i] = productText.charAt(i);
        }

        for(int i=0;i<ch.length;i++){

            if(ch[i]>='A' && ch[i]<='z')
            {
                productNamee=productNamee+ch[i];
            }
            else if(ch[i]>='0' && ch[i]<='9')
            {
                productPricee=productPricee+ch[i];
            }
        }

        if(productNamee!="") {
            productName2.setText(productNamee);
        }else
        {
            productName2.setText("demo");
        }


        if(productPricee!="")
        {

            productPrice2.setText(productPricee);

        }else
        {
            productPrice2.setText("14");
        }

    }

    private void addToCart() {

        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate= Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("dd MMM,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

          productID=saveCurrentTime+saveCurrentDate;

        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String,Object> cartMap = new HashMap<>();

          String priceSet=productPrice2.getText().toString();
        long priceSetInt=Long.parseLong(priceSet);
        int valueLimit=99999;
        if(priceSetInt>valueLimit){
            priceSet="21";
        }

        cartMap.put("pid",productID);
        cartMap.put("pname",productName2.getText().toString());
        cartMap.put("price",priceSet);
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",numberButton2.getNumber());
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
                                                Toast.makeText(ScannerProductsDetailsActivity.this, "Added to Cart List", Toast.LENGTH_SHORT).show();
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
}