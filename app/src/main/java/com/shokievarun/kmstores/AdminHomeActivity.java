package com.shokievarun.kmstores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class AdminHomeActivity extends AppCompatActivity {


    private Button LogoutBtn,CheckOrdersBtn,MaintainProductsBtn,checkApproveProductsBtn,AddProductsBtn,OrdersBackupBtn,NotificationBtn;
    private Button GenerateQRCodeBtn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        LogoutBtn=(Button)findViewById(R.id.admin_logout_btn);
        CheckOrdersBtn=(Button)findViewById(R.id.check_orders_btn);
        MaintainProductsBtn=(Button)findViewById(R.id.maintain_products_btn);
        checkApproveProductsBtn=(Button)findViewById(R.id.check_and_approve_new_products_btn);
        OrdersBackupBtn=(Button)findViewById(R.id.admin_orders_backup_btn);
        NotificationBtn=(Button)findViewById(R.id.notification_btn);
        GenerateQRCodeBtn=(Button)findViewById(R.id.generateqrcode_btn);


        AddProductsBtn=(Button)findViewById(R.id.add__admin_products_btn);
        mAuth= FirebaseAuth.getInstance();


        NotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminHomeActivity.this, NotificationActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);

            }
        });


        checkApproveProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminHomeActivity.this, AdminCheckNewProductsActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);

            }
        });


        OrdersBackupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminHomeActivity.this,AdminOrdersBackUpActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);

            }
        });

        MaintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminHomeActivity.this,SearchProductsActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);

            }
        });

        GenerateQRCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminHomeActivity.this,GenerateQRCodeActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);

            }
        });
        AddProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               loginSeller();

            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Paper.book().destroy();
                Intent intent=new Intent(AdminHomeActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();



            }
        });


        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(AdminHomeActivity.this,AdminNewOrdersActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    private void loginSeller() {
        String email = "test@test.com";
        String password = "tester";

        if (!email.equals("") && !password.equals("")) {
/*

            loadingBar.setTitle("Seller Account Login");
            loadingBar.setMessage("Please wait,while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
*/

            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Intent intent = new Intent(AdminHomeActivity.this, SellerHomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(AdminHomeActivity.this,"Please enter mail and password",Toast.LENGTH_SHORT).show();
        }
    }
}