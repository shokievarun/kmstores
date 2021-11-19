package com.shokievarun.kmstores;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shokievarun.kmstores.Model.Products;
import com.shokievarun.kmstores.ViewHolder.SellerItemsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SellerHomeActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private DatabaseReference unverifiedProductRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);


        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_add, R.id.navigation_logout)
                .build();


        BottomNavigationView bottomNav = findViewById(R.id.nav_view_bottom);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        ImageView backimage =(ImageView)findViewById(R.id.back_arrow_sh);


        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseAuth mAuth;
                mAuth=FirebaseAuth.getInstance();
                mAuth.signOut();

                Intent intent = new Intent(SellerHomeActivity.this, AdminHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        unverifiedProductRef= FirebaseDatabase.getInstance().getReference().child("Products");


        recyclerView=findViewById(R.id.seller_home_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(unverifiedProductRef.orderByChild("sid")
                                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()),Products.class).build();

        FirebaseRecyclerAdapter<Products, SellerItemsViewHolder> adapter=
                new FirebaseRecyclerAdapter<Products, SellerItemsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull SellerItemsViewHolder holder, int position, @NonNull Products model)
                    {

                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductPrice.setText("Price : Rs "+model.getPrice());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductStatus.setText("Product Status : "+model.getProductStatus());
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        final Products itemClick=model;
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final String productsId= model.getPid();
                                CharSequence options[]=new CharSequence[]
                                        {
                                                "Yes",
                                                "No"
                                        };
                                AlertDialog.Builder builder=new AlertDialog.Builder(SellerHomeActivity.this);
                                builder.setTitle("Do you want to Delete this Product.Are you sure?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        if(which==0)
                                        {
                                            deleteProduct(productsId);
                                        }
                                        if (which==1)
                                        {

                                        }
                                    }
                                });
                                builder.show();
                            }
                        });


                    }

                    @NonNull
                    @Override
                    public SellerItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item_view,parent,false);
                        SellerItemsViewHolder holder = new SellerItemsViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void deleteProduct(String productsId)
    {

        unverifiedProductRef.child(productsId)
               .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(SellerHomeActivity.this,"Item has been Deleted Sucessfully",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            Intent intentHome = new Intent(SellerHomeActivity.this, SellerHomeActivity.class);
                            startActivity(intentHome);
                           return true;
                        case R.id.navigation_add:

                            Intent intentcategory = new Intent(SellerHomeActivity.this, AdminCategoryActivity.class);
                            startActivity(intentcategory);



                            return true;
                        case R.id.navigation_logout:

                            final FirebaseAuth mAuth;
                            mAuth=FirebaseAuth.getInstance();
                            mAuth.signOut();

                            Intent intent = new Intent(SellerHomeActivity.this, AdminHomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            Toast.makeText(SellerHomeActivity.this,"Logged out",Toast.LENGTH_SHORT).show();
                            return true;
                    }
                    return false;
                }
            };


}