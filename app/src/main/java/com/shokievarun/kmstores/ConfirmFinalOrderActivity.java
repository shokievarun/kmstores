package com.shokievarun.kmstores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shokievarun.kmstores.Prevalent.Prevalent;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private String URL="https://fcm.googleapis.com/fcm/send";
    private EditText nameEditText,phoneEditText,addressEditText,cityEditText,extraItems;
    private TextView TotalAmounttConfirm;
    private Button confirmOrderBtn;
    private  String totalAmount="";
    private String transactionID="";
    private TextView transactionTxtview;
    private String key;
    private int once=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);
        mRequestQueue= Volley.newRequestQueue(this);
     //   FirebaseMessaging.getInstance().subscribeToTopic("neworder");

        totalAmount=getIntent().getStringExtra("Total Price");
        transactionID=getIntent().getStringExtra("tid");
        Toast.makeText(this,"Total Price = "+totalAmount+" Rs/-",Toast.LENGTH_SHORT).show();

        confirmOrderBtn=(Button)findViewById(R.id.confirm_final_order_btn);
        nameEditText=(EditText)findViewById(R.id.shipment_name);
        phoneEditText=(EditText)findViewById(R.id.shipment_phone_number);
        addressEditText=(EditText)findViewById(R.id.shipment_address);
        cityEditText=(EditText)findViewById(R.id.shipment_city);
        extraItems=(EditText)findViewById(R.id.add_extra_items);
        TotalAmounttConfirm=(TextView)findViewById(R.id.total_price_confirm);
        transactionTxtview=(TextView)findViewById(R.id.transactiontxtview);

        TotalAmounttConfirm.setText("Total Amount:  "+ totalAmount +" Rs/-");
        transactionTxtview.setText("Transaction Details: "+transactionID);



        ImageView backimage =(ImageView)findViewById(R.id.back_arrow_confirm);


        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
             //   myOrders();
              //  sendNotification();
              //  sendSMS1();

               /* if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

                    if(checkSelfPermission(Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                        sendSMS();
                    }else {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                    }
                }*/
            }
        });
    }




  /*  private void sendSMS(){
        String phoneNo="8884879145";
        String SMS="Hey, Hi! I have placed an order deliever as soon as possible";

        try{
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo,null,SMS,null,null);
            Toast.makeText(ConfirmFinalOrderActivity.this,"success", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(ConfirmFinalOrderActivity.this,"failed", Toast.LENGTH_SHORT).show();
        }
    }
*/


  /*  public void sendSMS1(){

        String phoneNo="88897567";
        String SMS="Hey, Hi! I have placed an order deliever as soon as possible";

        SmsManager mySmsManager = SmsManager.getDefault();
        mySmsManager.sendTextMessage(phoneNo,null, SMS, null, null);
    }
*/


    /*private void copyRecord(DatabaseReference fromPath, final DatabaseReference toPath) {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener()  {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }*/

    private void check() {


        if(once==0) {
            confirmOrder();
            orderBackup();
            orderBackupProducts();
            myOrders();
            sendNotification();
            once=1;
        }

      /*  if(TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your Phone Number", Toast.LENGTH_SHORT).show();
        }
        else
        {
            confirmOrder();
        }*/


        /*  if(TextUtils.isEmpty(nameEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your Full Name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your Phone Number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your Address", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cityEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your City", Toast.LENGTH_SHORT).show();
        }
        else
        {
            confirmOrder();
        }*/
    }

    private void confirmOrder() {

        final String saveCurrentDate,saveCurrentTime;

        Calendar calForDate= Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("dd MMM,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        HashMap<String,Object> orderMap = new HashMap<>();

        orderMap.put("totalAmount",totalAmount);
        orderMap.put("name",nameEditText.getText().toString());
        orderMap.put("phone",phoneEditText.getText().toString());
        orderMap.put("address",addressEditText.getText().toString());
        orderMap.put("city",cityEditText.getText().toString());
        orderMap.put("extra",extraItems.getText().toString());
        orderMap.put("date",saveCurrentDate);
        orderMap.put("time",saveCurrentTime);
        orderMap.put("state","not Shipped");
        orderMap.put("transactionId",transactionID);


        orderMap.put("username",Prevalent.currentOnlineUser.getName());
        orderMap.put("userphone",Prevalent.currentOnlineUser.getPhone());
        orderMap.put("useraddress",Prevalent.currentOnlineUser.getAddress());


        orderRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful())
                                    {

                                        Toast.makeText(ConfirmFinalOrderActivity.this,"Your  order has been placed succesfully",Toast.LENGTH_SHORT).show();

                                        Intent intent=new Intent(ConfirmFinalOrderActivity.this,HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });


    }


    private  void orderBackup(){

        final String saveCurrentDate,saveCurrentTime;

        Calendar calForDate= Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("dd MMM,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        key=saveCurrentDate+" "+saveCurrentTime;
     //   key="5242";
      //  key=saveCurrentTime;
        final DatabaseReference orderBackRef = FirebaseDatabase.getInstance().getReference().child("Orders Backup")
                .child(key)
                .child("User details");

        HashMap<String,Object> orderMapBackup = new HashMap<>();

        orderMapBackup.put("totalAmount",totalAmount);
        orderMapBackup.put("name",nameEditText.getText().toString());
        orderMapBackup.put("phone",phoneEditText.getText().toString());
        orderMapBackup.put("address",addressEditText.getText().toString());
        orderMapBackup.put("city",cityEditText.getText().toString());
        orderMapBackup.put("extra",extraItems.getText().toString());
        orderMapBackup.put("date",saveCurrentDate);
        orderMapBackup.put("time",saveCurrentTime);
        orderMapBackup.put("state","not Shipped");
        orderMapBackup.put("transactionId",transactionID);


        orderMapBackup.put("username",Prevalent.currentOnlineUser.getName());
        orderMapBackup.put("userphone",Prevalent.currentOnlineUser.getPhone());
        orderMapBackup.put("useraddress",Prevalent.currentOnlineUser.getAddress());

        orderBackRef.updateChildren(orderMapBackup);


    }


    private  void orderBackupProducts(){


        DatabaseReference fromPath = FirebaseDatabase.getInstance().getReference().child("Cart List")
                .child("Admin View")
                .child(Prevalent.currentOnlineUser.getPhone())
                .child("Products");

        DatabaseReference toPath = FirebaseDatabase.getInstance().getReference().child("Orders Backup")
                .child(key)
                .child("Items list");

        copyToOrdersBackup(fromPath,toPath);
    }

    private void copyToOrdersBackup(final DatabaseReference fromPath, final DatabaseReference toPath) {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                        if (firebaseError != null) {
                            System.out.println("Copy failed");
                        } else {
                            System.out.println("Success");

                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void myOrders()
    {
        String saveCurrentTime, saveCurrentDate;

        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate= new SimpleDateFormat("dd MMM, yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime=currentTime.format(calendar.getTime());

        String myOrdersKey=saveCurrentDate+" "+saveCurrentTime;

        DatabaseReference fromPath = FirebaseDatabase.getInstance().getReference().child("Cart List")
                .child("User View")
                .child(Prevalent.currentOnlineUser.getPhone())
                .child("Products");

        DatabaseReference toPath = FirebaseDatabase.getInstance().getReference().child("My Orders")
                .child(Prevalent.currentOnlineUser.getPhone())
                .child(myOrdersKey);

        copyRecord(fromPath,toPath);
    }
    private void copyRecord(final DatabaseReference fromPath, final DatabaseReference toPath) {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                        if (firebaseError != null) {
                            System.out.println("Copy failed");
                        } else {
                            System.out.println("Success");

                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private  void sendNotification(){

        JSONObject mainObj=new JSONObject();
        try{
            mainObj.put("to","/topics/"+"neworder");
            JSONObject notificationObj=new JSONObject();
            notificationObj.put("title","New Order");
            notificationObj.put("body","Check In");
            mainObj.put("notification",notificationObj);

            JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, URL,
                    mainObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            //   Toast.makeText(ConfirmFinalOrderActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){

                public Map<String,String> getHeaders() throws AuthFailureError{
                    Map<String,String> header=new HashMap<>();
                    header.put("content_type","application/json");
                    header.put("authorization","key=AAAA2cE5P78:APA91bE4IdaQFhEf5XJsDJT5qcVnlVq5MLwGvPnzVJaJwdI2OqPDjgwzwnH1mEBrAZwHRbvyWpXXzdv0qs-nPJpYC5AZEQvEKctprDrazF1w1x-Ljc2-fV-3bIewWL_SZB-B73gfiIEr");
                    return header;
                }
            };

            mRequestQueue.add(request);
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


   /* private void notification(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel= new NotificationChannel("5242","Success", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher_foreground);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"5242")
                .setSmallIcon(R.drawable.ic_notify_km)
                .setLargeIcon(rawBitmap)
                .setAutoCancel(true)
                .setContentText("Order Placed Successfully");
        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(this);
        managerCompat.notify(5242,builder.build());
    }
*/

}