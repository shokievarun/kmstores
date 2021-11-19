package com.shokievarun.kmstores;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shokievarun.kmstores.Model.AdminOrders;

public class AdminOrdersUserDetailsActivity extends AppCompatActivity {



    private DatabaseReference AdminOrderUserDetailsRef;
    private String userID = "";
    private TextView usernameavtxtv,userphoneavtxtv,useraddressavtxtv,totalAmountavtxtv,dateavtxtv,extraItemsavtxtv,transactionavtxtv;

    private String usernameav,userphoneav,useraddressav,totalAmountav,dateav,timeav,extraItemsav,transactionav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders_user_details);

        usernameavtxtv=(TextView)findViewById(R.id.order_user_nameav);
        userphoneavtxtv=(TextView)findViewById(R.id.order_user_phone_numberav);
        useraddressavtxtv=(TextView)findViewById(R.id.order_user_address_cityav);
        totalAmountavtxtv=(TextView)findViewById(R.id.order_total_av);
        dateavtxtv=(TextView)findViewById(R.id.order_date_timeav);
        extraItemsavtxtv=(TextView)findViewById(R.id.order_extraav);
        transactionavtxtv=(TextView)findViewById(R.id.transactionaav);

        userID = getIntent().getStringExtra("uid");

        AdminOrderUserDetailsRef = FirebaseDatabase.getInstance().getReference().child("Orders Backup")
                .child(userID).child("User details");

        getUserData();


        ImageView backimage =(ImageView)findViewById(R.id.back_arrow_aaoud);


        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Intent intent=new Intent(CartActivity.this,HomeActivity.class);
                finish();
            }
        });

    }



    private void getUserData() {




        AdminOrderUserDetailsRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //here you will get the data
                         usernameav = dataSnapshot.getValue(AdminOrders.class).getUsername();
                         userphoneav = dataSnapshot.getValue(AdminOrders.class).getUserphone();
                         useraddressav = dataSnapshot.getValue(AdminOrders.class).getUseraddress();
                         totalAmountav = dataSnapshot.getValue(AdminOrders.class).getTotalAmount();
                         dateav = dataSnapshot.getValue(AdminOrders.class).getDate();
                         timeav = dataSnapshot.getValue(AdminOrders.class).getTime();
                        extraItemsav = dataSnapshot.getValue(AdminOrders.class).getExtra();
                        transactionav=dataSnapshot.getValue(AdminOrders.class).getTransactionId();

                        String keytime=dateav+" "+timeav;
                        usernameavtxtv.setText("Name: "+usernameav);
                        userphoneavtxtv.setText("Phone: "+userphoneav);
                        useraddressavtxtv.setText("Address: "+useraddressav);
                        totalAmountavtxtv.setText("Total Amount: "+totalAmountav+" Rs/-");
                        dateavtxtv.setText("Ordered on: "+keytime);
                        extraItemsavtxtv.setText("Extra Items: "+extraItemsav);
                        transactionavtxtv.setText("Transaction ID: "+transactionav);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {



                    }
                });
    }


}