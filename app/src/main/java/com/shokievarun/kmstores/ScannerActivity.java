package com.shokievarun.kmstores;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;


public class ScannerActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    private DatabaseReference searchRef;
    private String productText="";
    private  ArrayList<String>  names=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);



        searchRef= FirebaseDatabase.getInstance().getReference().child("Products");
        populateSearch();
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        productText=result.getText();


                        Toast.makeText(ScannerActivity.this, productText, Toast.LENGTH_SHORT).show();


                        productCheck(productText);
                        finish();

                      /*  Intent intent = new Intent(ScannerActivity.this, ScannerProductsDetailsActivity.class);
                        intent.putExtra("productText", productText);
                        startActivity(intent);
                        finish();*/


                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();
      //  mCodeScanner.startPreview();

    }

    private void requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                mCodeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                  Toast.makeText(ScannerActivity.this,"Camera Permission Required",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
               token.continuePermissionRequest();
            }
        }).check();
    }


    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }


    private void productCheck(String productText) {


        for (int i = 0; i < names.size(); i++) {

            if (names.get(i).equals(productText)) {


                Intent run= new Intent(ScannerActivity.this,ProductDetailsActivity.class);
                run.putExtra("pid",productText);
                startActivity(run);
                finish();

            }
        }


          /*  String str = //the string which you want to compare
                    ArrayList myArray =// my array list
            boolean isStringExists = myArray.contains(str);*/

        //       String data=names.get(i);
        //       Log.d("myTag", names.get(i));
      /*  boolean val = names.contains(productText);
        if (val) {

            Intent intent = new Intent(ScannerActivity.this, ProductDetailsActivity.class);
            intent.putExtra("pid", productText);
            finish();
        } else {
            Log.d("myout5", names.get(0));
            Log.d("myout6", names.get(1));
            Log.d("mytxt7", productText);
            Intent intent = new Intent(ScannerActivity.this, ScannerProductsDetailsActivity.class);
            intent.putExtra("productText", productText);
            startActivity(intent);
            finish();
        }
*/
      /*  Intent intent = new Intent(ScannerActivity.this, ScannerProductsDetailsActivity.class);
        intent.putExtra("productText", productText);
        startActivity(intent);
        finish();*/
    }





    private void populateSearch(){

        ValueEventListener eventListener= new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String n=snapshot.child("pid").getValue(String.class);
                    names.add(n);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        searchRef.addListenerForSingleValueEvent(eventListener);
    }
}
