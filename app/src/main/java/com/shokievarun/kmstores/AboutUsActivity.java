package com.shokievarun.kmstores;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AboutUsActivity extends AppCompatActivity {

   private String msg,phnno;
   private String email,subject,body;
   private Button whatsappbtn,mapBtn,gMailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about_us);

        whatsappbtn=(Button)findViewById(R.id.whatsapp_btn);
        mapBtn=(Button)findViewById(R.id.map_btn);
        gMailBtn=(Button)findViewById(R.id.gmail_btn);


        msg="Hey! Hi, when my order will be delivered? ";
        phnno="8884879145";


        gMailBtn.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(AboutUsActivity.this,"Gmail not installed on your device",Toast.LENGTH_SHORT).show();
                }


            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Uri mapUri = Uri.parse("geo:0,0?q=13.013885,77.547833");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        whatsappbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean installed=appInstalledOrNot("com.whatsapp");

                if(installed){
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+91"+phnno+"&text="+msg));
                    startActivity(intent);
                }else
                {
                    Toast.makeText(AboutUsActivity.this,"Whatsapp not installed on your device",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private boolean appInstalledOrNot(String s)
    {
        PackageManager packageManager=getPackageManager();
        boolean app_installed;
        try{
            packageManager.getPackageInfo(s,PackageManager.GET_ACTIVITIES);
            app_installed=true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed=false;
        }
        return app_installed;
    }
}