package com.shokievarun.kmstores;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {


    private RequestQueue mRequestQueue;
    private EditText topicet,titleet,bodyet;
    private Button notifyBtn;
    private String URL="https://fcm.googleapis.com/fcm/send";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mRequestQueue= Volley.newRequestQueue(this);
        topicet=(EditText)findViewById(R.id.topic21);
        titleet=(EditText)findViewById(R.id.title21);
        bodyet=(EditText)findViewById(R.id.body21);
        notifyBtn=(Button)findViewById(R.id.notifbtn);

        ImageView backimage =(ImageView)findViewById(R.id.back_arrow21);


        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Intent intent=new Intent(CartActivity.this,HomeActivity.class);
                finish();
            }
        });

        notifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
             //   Toast.makeText(NotificationActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });



    }




    private  void sendNotification(){

        String topic=topicet.getText().toString();
        String title=titleet.getText().toString();
        String body=bodyet.getText().toString();

        JSONObject mainObj=new JSONObject();
        try{
            mainObj.put("to","/topics/"+topic);
            JSONObject notificationObj=new JSONObject();
            notificationObj.put("title",title);
            notificationObj.put("body",body);
            mainObj.put("notification",notificationObj);

            JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, URL,
                    mainObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(NotificationActivity .this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){

                public Map<String,String> getHeaders() throws AuthFailureError {
                    Map<String,String> header=new HashMap<>();
                    header.put("content_type","application/json");
                    header.put("authorization","key=AAAA2cE5P78:APA91bE4IdaQFhEf5XJsDJT5qcVnlVq5MLwGvPnzVJaJwdI2OqPDjgwzwnH1mEBrAZwHRbvyWpXXzdv0qs-nPJpYC5AZEQvEKctprDrazF1w1x-Ljc2-fV-3bIewWL_SZB-B73gfiIEr");
                    return header;
                }
            };

            mRequestQueue.add(request);
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}