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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNoActivity extends AppCompatActivity {

   private  String  phoneNo,otpId;
   private  Button verify_btn;
   private EditText verify_code;

   private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_no);

        mAuth= FirebaseAuth.getInstance();
        verify_btn=findViewById(R.id.verify_btn);
        verify_code=findViewById(R.id.verificatin_code_entered_by_user);

        phoneNo=getIntent().getStringExtra("mobile").toString();


        initiateOtp();

        ImageView backimage =(ImageView)findViewById(R.id.back_arrow_phone_no_otp);


        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Intent intent=new Intent(CartActivity.this,HomeActivity.class);
                finish();
            }
        });

        verify_btn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(verify_code.getText().toString().isEmpty())
                    Toast.makeText(VerifyPhoneNoActivity.this,"Blank Field cannot be proceed",Toast.LENGTH_LONG).show();
                else if(verify_code.getText().toString().length()!=6)
                    Toast.makeText(VerifyPhoneNoActivity.this,"Invalid Otp",Toast.LENGTH_LONG).show();
                else {
                    PhoneAuthCredential credential= PhoneAuthProvider.getCredential(otpId,verify_code.getText().toString());
                    verifyCodeFn(credential);
                }
            }
        }));



    }

    private void initiateOtp() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        otpId = s;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        verifyCodeFn(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(VerifyPhoneNoActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }


                });
    }


    private void verifyCodeFn(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(VerifyPhoneNoActivity.this,RegisterActivity.class);
                    intent.putExtra("phnno",phoneNo);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(VerifyPhoneNoActivity.this,"Sign in code error",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}