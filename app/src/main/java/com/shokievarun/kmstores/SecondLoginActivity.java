package com.shokievarun.kmstores;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.shokievarun.kmstores.Model.Users;
import com.shokievarun.kmstores.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class SecondLoginActivity extends AppCompatActivity {

    private Button LoginButton;
    private EditText InputNumber,InputPassword;
    private ProgressDialog loadingBar;
    private String parentDbName="Users";
    private android.widget.CheckBox checkBoxRememberme;
    private TextView AdminLink,NotAdminLink,ForgetPasswordLink,gmaillink;
    private String email,subject,body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_login);

        LoginButton=(Button)findViewById(R.id.login_button);
        InputNumber=(EditText)findViewById(R.id.login_phone_number_input);
        InputPassword=(EditText)findViewById(R.id.login_password_input);
        loadingBar=new ProgressDialog(this);
        checkBoxRememberme=(CheckBox) findViewById(R.id.remember_me_chkb);
        AdminLink=(TextView)findViewById(R.id.admin_panel_link);
        NotAdminLink=(TextView)findViewById(R.id.not_admin_panel_link);
        ForgetPasswordLink=(TextView)findViewById(R.id.forget_password_link);
        gmaillink=(TextView)findViewById(R.id.gmaillink);

        Paper.init(this);


        gmaillink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email="21svrx@gmail.com";
                subject="To K M Stores";
                body="";

                Intent intent=new Intent(Intent.ACTION_SENDTO);
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{email});
                intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                intent.putExtra(Intent.EXTRA_TEXT,body);
                //   intent.setType("message/rfc822");
                intent.setData(Uri.parse("mailto:"));
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(SecondLoginActivity.this,"Gmail not installed on your device",Toast.LENGTH_SHORT).show();
                }


            }
        });


        ForgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SecondLoginActivity.this, ResetPasswordActivity.class);
                intent.putExtra("check","login");
                startActivity(intent);
            }
        });


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });


        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName="Admins";
            }
        });

        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName="Users";
            }
        });


    }



    private void LoginUser() {

        String phone="+91"+InputNumber.getText().toString();
        String password=InputPassword.getText().toString();

        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this,"Please enter your phone number...",Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please enter your password...",Toast.LENGTH_SHORT).show();
        } else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            allowAccessToAccount(phone,password);
        }
    }

    private void allowAccessToAccount(String phone, String password) {


        if(parentDbName.equals("Users")) {
            if (checkBoxRememberme.isChecked()) {
                Paper.book().write(Prevalent.UserPhoneKey, phone);
                Paper.book().write(Prevalent.UserPasswordKey, password);
            }
        }

        if(parentDbName.equals("Admins")) {
            if (checkBoxRememberme.isChecked()) {
                Paper.book().write(Prevalent.AdminPhoneKey, phone);
                Paper.book().write(Prevalent.AdminPasswordKey, password);
            }
        }

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(phone).exists())
                {
                    Users userData=dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if(userData.getPhone().equals(phone))
                    {
                        if(userData.getPassword().equals(password))
                        {
                           if(parentDbName.equals("Admins"))
                           {
                               Toast.makeText(SecondLoginActivity.this,"Welcome Admin you, Logged in Succesful",Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();

                               Intent intent= new Intent(SecondLoginActivity.this,AdminHomeActivity.class);
                               startActivity(intent);
                               finish();
                           }
                           else if(parentDbName.equals("Users"))
                               {
                               Toast.makeText(SecondLoginActivity.this,"Login Succesful",Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();

                               Intent intent= new Intent(SecondLoginActivity.this,HomeActivity.class);
                               Prevalent.currentOnlineUser=userData;
                               startActivity(intent);
                               finish();
                               }
                        }else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(SecondLoginActivity.this,"Password is Incorrect",Toast.LENGTH_SHORT).show();
                        }
                    }
                }else
                {
                    Toast.makeText(SecondLoginActivity.this,"Account with this "+phone+" number do not exists",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(SecondLoginActivity.this,"You need to create a new account",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
