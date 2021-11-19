package com.shokievarun.kmstores;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SellerRegistrationActivity extends AppCompatActivity {


  /*  private Button sellerAlreadyRegisteredBtn;
    private Button registerButton;
    private EditText nameInput,phoneInput,emailInput,passwordInput,adressInput;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    */
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);

     /*   mAuth=FirebaseAuth.getInstance();
        loadingBar=new ProgressDialog(this);


        sellerAlreadyRegisteredBtn=(Button)findViewById(R.id.seller_already_registered_btn);
        registerButton=(Button)findViewById(R.id.seller_register_btn);
        nameInput=(EditText)findViewById(R.id.name_seller);
        phoneInput=(EditText)findViewById(R.id.phone_seller);
        emailInput=(EditText)findViewById(R.id.email_seller);
        passwordInput=(EditText)findViewById(R.id.password_seller);
        adressInput=(EditText)findViewById(R.id.shop_address_seller);



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                registerSeller();
            }
        });

        sellerAlreadyRegisteredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SellerRegistrationActivity.this,SellerLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });*/
    }

   /* private void registerSeller()
    {


        String name=nameInput.getText().toString();
        String phone=phoneInput.getText().toString();
        String email=emailInput.getText().toString();
        String password=passwordInput.getText().toString();
        String address=adressInput.getText().toString();

        if(!name.equals("") && !phone.equals("") && !email.equals("") && !password.equals("") && !address.equals(""))
        {

            loadingBar.setTitle("Creating Seller Account");
            loadingBar.setMessage("Please wait,while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                final DatabaseReference rootRef;
                                rootRef= FirebaseDatabase.getInstance().getReference();

                                String sid=mAuth.getCurrentUser().getUid();

                                HashMap<String,Object> sellerMap=new HashMap<>();
                                sellerMap.put("sid",sid);
                                sellerMap.put("phone",phone);
                                sellerMap.put("email",email);
                                sellerMap.put("address",address);
                                sellerMap.put("name",name);
                                sellerMap.put("password",password);

                                rootRef.child("Sellers").child(sid).updateChildren(sellerMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                loadingBar.dismiss();

                                                Intent intent = new Intent(SellerRegistrationActivity.this, SellerHomeActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                                Toast.makeText(SellerRegistrationActivity.this,"Registered Successfully...",Toast.LENGTH_SHORT).show();

                                            }
                                        });
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(SellerRegistrationActivity.this,"Please Complete the registration form",Toast.LENGTH_SHORT).show();
        }

    }*/
}