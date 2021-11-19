package com.shokievarun.kmstores;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SellerLoginActivity extends AppCompatActivity {

/*
    private Button sellerLoginBtn;
    private EditText emailInput, passwordInput;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

       /* passwordInput = (EditText) findViewById(R.id.password_seller_login);
        emailInput = (EditText) findViewById(R.id.email_seller_login);
        sellerLoginBtn = (Button) findViewById(R.id.seller_login_btn);
        loadingBar = new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        sellerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginSeller();
            }
        });*/

    }

  /*  private void loginSeller() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (!email.equals("") && !password.equals("")) {

            loadingBar.setTitle("Seller Account Login");
            loadingBar.setMessage("Please wait,while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Intent intent = new Intent(SellerLoginActivity.this, SellerHomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(SellerLoginActivity.this,"Please enter mail and password",Toast.LENGTH_SHORT).show();
        }
    }*/
}