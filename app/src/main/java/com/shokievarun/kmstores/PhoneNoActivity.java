package com.shokievarun.kmstores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;

public class PhoneNoActivity extends AppCompatActivity {


    private EditText regphoneNo;
   private  Button getotpbtn;
   private CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_no);



        regphoneNo=findViewById(R.id.phone_numberedittextkm);
        getotpbtn=findViewById(R.id.reg_phn_no_btn);
        ccp=findViewById(R.id.ccpkm);
        ccp.registerCarrierNumberEditText(regphoneNo);


        ImageView backimage =(ImageView)findViewById(R.id.back_arrow_phone_no);


        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     Intent intent=new Intent(CartActivity.this,HomeActivity.class);
                finish();
            }
        });


        getotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PhoneNoActivity.this,VerifyPhoneNoActivity.class);
                intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
                startActivity(intent);
                finish();
            }
        });

    }


}