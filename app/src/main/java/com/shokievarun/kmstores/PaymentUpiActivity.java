package com.shokievarun.kmstores;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;

public class PaymentUpiActivity extends AppCompatActivity {


        private AppCompatButton btnBuyNow,codBtn;
        private  String totalAmountpayment,nameStr,upiIdStr,noteStr;
        private  TextView txtTotalAmountpaymentUpi;


        final int UPI_PAYMENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_upi);

        btnBuyNow = findViewById(R.id.btnBuyNow);
        codBtn=findViewById(R.id.codBtn);
        txtTotalAmountpaymentUpi=(TextView)findViewById(R.id.total_price_paymentupi);

      //  totalAmountpayment=getIntent().getStringExtra("Total Price");
       totalAmountpayment="1";
     //   upiIdStr="paytmqr2810050501011jmt083u2vcp@paytm";
     //   upiIdStr="Q128826072";
      //  upiIdStr="MS2006031359476070000013";
        upiIdStr="6363890271@okbizaxis";
        nameStr="Mrs Manjula C";
        noteStr="For K M Stores";

        txtTotalAmountpaymentUpi.setText("Total Amount:  "+ totalAmountpayment+" Rs/-");
        ImageView backimage =(ImageView)findViewById(R.id.back_arrow_payment);


        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Intent intent=new Intent(CartActivity.this,HomeActivity.class);
                finish();
            }
        });

        btnBuyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    payUsingUpi(totalAmountpayment, upiIdStr, nameStr, noteStr);
                }
            });

        codBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PaymentUpiActivity.this,ConfirmFinalOrderActivity.class);
                intent.putExtra("Total Price", String.valueOf(totalAmountpayment));
                intent.putExtra("tid", "COD");
                startActivity(intent);
            }
        });


        }



        void payUsingUpi(String amount, String upiId, String name, String note) {

            Uri uri = Uri.parse("upi://pay").buildUpon()
                    .appendQueryParameter("pa", upiId)
                    .appendQueryParameter("pn", name)
                    .appendQueryParameter("tn", note)
                    .appendQueryParameter("am", amount)
                    .appendQueryParameter("cu", "INR")
                    .build();


            Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
            upiPayIntent.setData(uri);

            // will always show a dialog to user to choose an app
            Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

            // check if intent resolves
            if(null != chooser.resolveActivity(getPackageManager())) {
                startActivityForResult(chooser, UPI_PAYMENT);
            } else {
                Toast.makeText(PaymentUpiActivity.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case UPI_PAYMENT:
                    if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                        if (data != null) {
                            String trxt = data.getStringExtra("response");
                            Log.d("UPI", "onActivityResult: " + trxt);
                            ArrayList<String> dataList = new ArrayList<>();
                            dataList.add(trxt);
                            upiPaymentDataOperation(dataList);
                        } else {
                            Log.d("UPI", "onActivityResult: " + "Return data is null");
                            ArrayList<String> dataList = new ArrayList<>();
                            dataList.add("nothing");
                            upiPaymentDataOperation(dataList);
                        }
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                    break;
            }
        }

        private void upiPaymentDataOperation(ArrayList<String> data) {
            if (isConnectionAvailable(PaymentUpiActivity.this)) {
                String str = data.get(0);
                Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
                String paymentCancel = "";
                if(str == null) str = "discard";
                String status = "";
                String approvalRefNo = "";
                String response[] = str.split("&");
                for (int i = 0; i < response.length; i++) {
                    String equalStr[] = response[i].split("=");
                    if(equalStr.length >= 2) {
                        if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                            status = equalStr[1].toLowerCase();
                        }
                        else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                            approvalRefNo = equalStr[1];
                        }
                    }
                    else {
                        paymentCancel = "Payment cancelled by user.";
                    }
                }

                if (status.equals("success")) {
                    //Code to handle successful transaction here.
                    Toast.makeText(PaymentUpiActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                    Log.d("UPI", "responseStr: "+approvalRefNo);
                    Intent intent = new Intent(PaymentUpiActivity.this,ConfirmFinalOrderActivity.class);
                    intent.putExtra("Total Price", String.valueOf(totalAmountpayment));
                    intent.putExtra("tid", String.valueOf(approvalRefNo));
                    startActivity(intent);
                }
                else if("Payment cancelled by user.".equals(paymentCancel)) {
                  //  Toast.makeText(PaymentUpiActivity.this, "Payment processing...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PaymentUpiActivity.this,ConfirmFinalOrderActivity.class);
                    intent.putExtra("Total Price", String.valueOf(totalAmountpayment));
                    intent.putExtra("tid","UPI Payment");
                    startActivity(intent);
                }
                else {
              //      Toast.makeText(PaymentUpiActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PaymentUpiActivity.this,ConfirmFinalOrderActivity.class);
                    intent.putExtra("Total Price", String.valueOf(totalAmountpayment));
                    intent.putExtra("tid","UPI Payment");
                    startActivity(intent);
                }
            } else {
                Toast.makeText(PaymentUpiActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
            }
        }

        public static boolean isConnectionAvailable(PaymentUpiActivity context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isConnected()
                        && netInfo.isConnectedOrConnecting()
                        && netInfo.isAvailable()) {
                    return true;
                }
            }
            return false;
        }
    }