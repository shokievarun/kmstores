package com.shokievarun.kmstores;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.shokievarun.kmstores.Model.Users;
import com.shokievarun.kmstores.Prevalent.Prevalent;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "PushNotification";
    private static final String CHANNEL_ID = "101";
    private Button joinNowButton,loginButton;
    private TextView sellerBegin;
    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        joinNowButton= (Button)findViewById(R.id.main_join_now_button);
        loginButton=(Button)findViewById(R.id.main_login_button);
    //    sellerBegin=(TextView)findViewById(R.id.seller_begin_link);
        loadingBar = new ProgressDialog(this);


        Paper.init(this);


        createNotificationChannel();

        //neworder for order notification
        //kmstores user topic
        FirebaseMessaging.getInstance().subscribeToTopic("kmstores");







/*
        sellerBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this,SellerRegistrationActivity.class);
                startActivity(intent);
            }
        });*/




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this,SecondLoginActivity.class);
                startActivity(intent);

            }
        });


        String AdminPhoneKey = Paper.book().read(Prevalent.AdminPhoneKey);
        String AdminPasswordKey = Paper.book().read(Prevalent.AdminPasswordKey);

        if (AdminPhoneKey != "" && AdminPasswordKey != "")
        {
            if (!TextUtils.isEmpty(AdminPhoneKey)  &&  !TextUtils.isEmpty(AdminPasswordKey))
            {
                AllowAccesstoAdmin(AdminPhoneKey, AdminPasswordKey);

                loadingBar.setTitle("Already Logged in");
                loadingBar.setMessage("Please wait.....");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }


        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this,PhoneNoActivity.class);
                startActivity(intent);
            }
        });

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (UserPhoneKey != "" && UserPasswordKey != "")
        {
            if (!TextUtils.isEmpty(UserPhoneKey)  &&  !TextUtils.isEmpty(UserPasswordKey))
            {
                AllowAccess(UserPhoneKey, UserPasswordKey);

                loadingBar.setTitle("Already Logged in");
                loadingBar.setMessage("Please wait.....");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }


    }

    private void AllowAccess(final String phone, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child("Users").child(phone).exists())
                {
                    Users usersData = dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                        //    Toast.makeText(LoginActivity.this, "Please wait, you are already logged in...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            Prevalent.currentOnlineUser = usersData;
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            loadingBar.dismiss();
                      //      Toast.makeText(LoginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
               //     Toast.makeText(LoginActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void AllowAccesstoAdmin(final String phone, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child("Admins").child(phone).exists())
                {
                    Users usersData = dataSnapshot.child("Admins").child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
            //                Toast.makeText(LoginActivity.this, "Please wait, you are already logged in...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                            Prevalent.currentOnlineUser = usersData;
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            loadingBar.dismiss();
                   //         Toast.makeText(LoginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
          //          Toast.makeText(LoginActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "firebaseNotifChannel";
            String description = "Receive Firebase notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


   /* @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser!=null)
        {
            Intent intent = new Intent(LoginActivity.this, SellerHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }*/
}