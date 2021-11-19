package com.shokievarun.kmstores;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {


    private Button CreateAccountButton;
    private EditText InputName,InputAddress,InputPassword;
    private ProgressDialog loadingBar;
    private String name,address,password;
     private String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        phoneNumber=getIntent().getStringExtra("phnno");
        CreateAccountButton=(Button)findViewById(R.id.register_login_button);
        InputName=(EditText)findViewById(R.id.name_register);
        InputAddress=(EditText)findViewById(R.id.address_register);
        InputPassword=(EditText)findViewById(R.id.password_register);
        loadingBar=new ProgressDialog(this);


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });

    }

    private void CreateAccount() {
         name=InputName.getText().toString();
         address=InputAddress.getText().toString();
         password=InputPassword.getText().toString();

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this,"Please enter your name...",Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(address))
        {
            Toast.makeText(this,"Please enter address...",Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please enter your password...",Toast.LENGTH_SHORT).show();
        } else
        {
           loadingBar.setTitle("Create Account");
           loadingBar.setMessage("Please wait while we are checking the credentials");
           loadingBar.setCanceledOnTouchOutside(false);
           loadingBar.show();

           ValidatePhoneNumber(name,address,phoneNumber,password);
        }
    }

    private void ValidatePhoneNumber(String name, String address,String phoneNumber, String password) {

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();


        String saveCurrentTime, saveCurrentDate;

        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate= new SimpleDateFormat("dd MMM, yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());




        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!(dataSnapshot.child("Users").child(phoneNumber).exists())){

                    HashMap<String,Object> userdataMap=new HashMap<>();
                    userdataMap.put("name",name);
                    userdataMap.put("address",address);
                    userdataMap.put("phone",phoneNumber);
                    userdataMap.put("password",password);
                    userdataMap.put("joindate",saveCurrentDate);

                    RootRef.child("Users").child(phoneNumber).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this,"Congratulations your account has been created",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent= new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this,"Network Error: Please try again after sometimes",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }else
                {
                    updatePassword();
                  //  Toast.makeText(RegisterActivity.this,"This  "+phoneNumber+"  Already exists",Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                   Toast.makeText(RegisterActivity.this,"Password changed successfully",Toast.LENGTH_SHORT).show();

                    Intent intent= new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updatePassword()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object> userdataMaps = new HashMap<>();
        userdataMaps.put("name",name);
        userdataMaps.put("address",address);
        userdataMaps.put("phone",phoneNumber);
        userdataMaps.put("password",password);
        ref.child(phoneNumber).updateChildren(userdataMaps);


    }

}