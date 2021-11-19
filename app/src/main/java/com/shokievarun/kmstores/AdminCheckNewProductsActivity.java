package com.shokievarun.kmstores;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shokievarun.kmstores.Model.Products;
import com.shokievarun.kmstores.ViewHolder.ProductViewHolder;
import com.squareup.picasso.Picasso;

public class AdminCheckNewProductsActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference unverifiedProductRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_new_products);

        unverifiedProductRef= FirebaseDatabase.getInstance().getReference().child("Products");

        ImageView backimage =(ImageView)findViewById(R.id.back_arrow_acnpa);


        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent=new Intent(CartActivity.this,HomeActivity.class);
                finish();
            }
        });

        recyclerView=findViewById(R.id.approve_list);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(unverifiedProductRef.orderByChild("productStatus").equalTo("Not Approved"),Products.class).build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model)
                    {

                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductPrice.setText("Price : Rs "+model.getPrice());
                        holder.txtProductDescription.setText(model.getDescription());
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
                                AlertDialog.Builder builder=new AlertDialog.Builder(AdminCheckNewProductsActivity.this);
                                builder.setTitle("Do you want to Approve this Product.Are you sure?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                       if(which==0)
                                       {
                                           changeProductsState(productsId);
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
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout,parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void changeProductsState(String productsId)
    {

        unverifiedProductRef.child(productsId).child("productStatus").setValue("Approved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(AdminCheckNewProductsActivity.this,"Item has been Approved , and it is now available for sale from seller to customer",Toast.LENGTH_SHORT).show();
            }
        });
    }
}