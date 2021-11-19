package com.shokievarun.kmstores;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {


    private  String CategoryName,Description,Price,Pname,saveCurrentDate,saveCurrentTime;
    private ImageView InputProductImage;
    private EditText InputProductName,InputProductDescription,InputProductPrice;
    private Button AddNewProductButton;
    private static final int GalleryPick=1;
    private Uri ImageUri;
    private String productRandomKey, downloadImageuRL;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef,sellersRef;
    private ProgressDialog loadingBar;
    private String parentDbName="Users";

    private String sName,sAddress,sPhone,sEmail,sId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        CategoryName=getIntent().getExtras().get("Category").toString();
        ProductImagesRef= FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef= FirebaseDatabase.getInstance().getReference().child("Products");
        sellersRef= FirebaseDatabase.getInstance().getReference().child("Sellers");
        loadingBar=new ProgressDialog(this);

        InputProductImage=(ImageView)findViewById(R.id.select_product_image);
        InputProductName=(EditText)findViewById(R.id.product_name);
        InputProductDescription=(EditText)findViewById(R.id.product_description_add);
        InputProductPrice=(EditText)findViewById(R.id.product_price);
        AddNewProductButton=(Button)findViewById(R.id.add_new_product);



        ImageView backimage =(ImageView)findViewById(R.id.back_arrow_anpa);


        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
     //           Intent intent=new Intent(CartActivity.this,HomeActivity.class);
                finish();
            }
        });


        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateProductData();
            }
        });


        sellersRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists())
                        {
                            sName=snapshot.child("name").getValue().toString();
                            sPhone=snapshot.child("phone").getValue().toString();
                            sAddress=snapshot.child("address").getValue().toString();
                            sId=snapshot.child("sid").getValue().toString();
                            sEmail=snapshot.child("email").getValue().toString();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    private void openGallery() {
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryPick && resultCode==RESULT_OK && data!=null){
            ImageUri=data.getData();
            InputProductImage.setImageURI(ImageUri);
        }
    }

    private void validateProductData() {

        Description=InputProductDescription.getText().toString().toLowerCase();
        Price=InputProductPrice.getText().toString().toLowerCase();
        Pname=InputProductName.getText().toString().toLowerCase();

        if(ImageUri==null)
        {
            Toast.makeText(this,"Product image is mandatory...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description))
        {
            Toast.makeText(this,"Please write Product Description...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Price))
        {
            Toast.makeText(this,"Please write Product Price...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Pname))
        {
            Toast.makeText(this,"Please write Product Pname...",Toast.LENGTH_SHORT).show();
        }
        else
        {
            storeProductInformation();
        }
    }

    private void storeProductInformation() {

        loadingBar.setTitle("Add new Product");
        loadingBar.setMessage("Dear Seller,Please wait while we are adding the new Product");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate= new SimpleDateFormat("dd MMM, yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());


        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime=currentTime.format(calendar.getTime());

        productRandomKey=saveCurrentDate +" "+saveCurrentTime;

        StorageReference filePath=ProductImagesRef.child(ImageUri.getLastPathSegment()+productRandomKey+".jpg");

        final UploadTask uploadTask=filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message=e.toString();
                Toast.makeText(AdminAddNewProductActivity.this,"Error: "+message,Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNewProductActivity.this,"Product Image Uploaded Successfully!",Toast.LENGTH_SHORT).show();

                Task<Uri>  urltask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if(!task.isSuccessful())
                        {

                            throw task.getException();
                        }

                        downloadImageuRL=filePath.getDownloadUrl().toString();
                        return  filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful())
                        {
                            downloadImageuRL=task.getResult().toString();

                    //        Toast.makeText(AdminAddNewProductActivity.this,"got the Product image Url Succesfully...",Toast.LENGTH_SHORT).show();

                            saveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void saveProductInfoToDatabase() {

        HashMap<String, Object> productMap=new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",Description);
        productMap.put("image",downloadImageuRL);
        productMap.put("Category",CategoryName);
        productMap.put("price",Price);
        productMap.put("pname",Pname);
        productMap.put("productStatus","Not Approved");

        productMap.put("sellerName",sName);
        productMap.put("sellerAddress",sAddress);
        productMap.put("sellerPhone",sPhone);
        productMap.put("sellerEmail",sEmail);
        productMap.put("sid",sId);


        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Intent intent= new Intent(AdminAddNewProductActivity.this,SellerHomeActivity.class);
                            startActivity(intent);
                            finish();

                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewProductActivity.this,"Product added Succesfully...",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message=task.getException().toString();
                            Toast.makeText(AdminAddNewProductActivity.this,"Error: "+ message,Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


}