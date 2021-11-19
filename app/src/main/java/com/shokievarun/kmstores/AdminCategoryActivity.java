package com.shokievarun.kmstores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminCategoryActivity extends AppCompatActivity {


    private TextView rice,cosmetic,oils,vegetables;
    private TextView atta,pulses,aachi,biscuits;
    private TextView cooldrinks,books,detergents,extras;

    private TextView txtview99,mrp;

    private TextView dailyDeals;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);



        rice=(TextView)findViewById(R.id.rice);
        aachi=(TextView)findViewById(R.id.masala_items);
        oils=(TextView)findViewById(R.id.oil);
        vegetables=(TextView)findViewById(R.id.vegetables);
        atta=(TextView)findViewById(R.id.atta);
        pulses=(TextView)findViewById(R.id.pulses);
        cosmetic=(TextView)findViewById(R.id.cosmetics);
        biscuits=(TextView)findViewById(R.id.biscuits);
        cooldrinks=(TextView)findViewById(R.id.cool_drinks);
        books=(TextView)findViewById(R.id.books);
        detergents=(TextView)findViewById(R.id.detergents);
        extras=(TextView)findViewById(R.id.extra_item);
        dailyDeals=(TextView)findViewById(R.id.daily_deals);

        txtview99=(TextView)findViewById(R.id.id99);
        mrp=(TextView)findViewById(R.id.mrp);

        ImageView backimage =(ImageView)findViewById(R.id.back_arrow_aca);


        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Intent intent=new Intent(CartActivity.this,HomeActivity.class);
                finish();
            }
        });

        txtview99.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("Category","99");
                startActivity(intent);
                finish();
            }
        });


        mrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("Category","MRP");
                startActivity(intent);
                finish();
            }
        });

        dailyDeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("Category","daily deals");
                startActivity(intent);
                finish();
            }
        });


        rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("Category","Rice");
                startActivity(intent);
                finish();
            }
        });


        cosmetic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("Category","Cosmetics");
                startActivity(intent);
                finish();
            }
        });

        oils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("Category","Oils");
                startActivity(intent);
                finish();
            }
        });

        vegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("Category","Vegetables");
                startActivity(intent);
                finish();
            }
        });

        atta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("Category","Atta");
                startActivity(intent);
                finish();
            }
        });

        pulses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("Category","Pulses");
                startActivity(intent);
                finish();
            }
        });

        aachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("Category","Masala Items");
                startActivity(intent);
                finish();
            }
        });

        biscuits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("Category","Biscuits");
                startActivity(intent);
                finish();
            }
        });

        cooldrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("Category","Cooldrinks");
                startActivity(intent);
                finish();
            }
        });

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("Category","Books");
                startActivity(intent);
                finish();
            }
        });

        detergents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("Category","Detergents");
                startActivity(intent);
                finish();
            }
        });

        extras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("Category","Extras");
                startActivity(intent);
                finish();
            }
        });
    }
}