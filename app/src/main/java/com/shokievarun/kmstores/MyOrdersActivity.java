package com.shokievarun.kmstores;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shokievarun.kmstores.Model.MyOrders;
import com.shokievarun.kmstores.Prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity {



    private RecyclerView myorderList;
    private DatabaseReference myordersRef;
    private String date;

    private ArrayList<String> myList;
    private  LinearLayoutManager mLayoutmanager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        myordersRef = FirebaseDatabase.getInstance().getReference().child("My Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        myorderList = findViewById(R.id.my_orders_list);
        mLayoutmanager =new LinearLayoutManager(this);
        mLayoutmanager.setReverseLayout(true);
        mLayoutmanager.setStackFromEnd(true);
        myorderList.setLayoutManager(mLayoutmanager);



        ImageView backimage =(ImageView)findViewById(R.id.back_arrow_my_orders);


        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyOrdersActivity.this,HomeActivity.class);
                finish();
            }
        });


          myOrdersListener();

    }


    public void myOrdersListener() {
        myList = new ArrayList<String>();
        myordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    date = snapshot.getKey();
                    myList.add(date);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<MyOrders> options= new FirebaseRecyclerOptions.Builder<MyOrders>()
                .setQuery(myordersRef,MyOrders.class)
                .build();

        FirebaseRecyclerAdapter<MyOrders,MyOrdersViewHolder> adapter=
                new FirebaseRecyclerAdapter<MyOrders, MyOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MyOrdersViewHolder holder, int position, @NonNull MyOrders model) {

                        final String name = myList.get(position);
                        holder.dateTime.setText(name);


                        holder.showProductsBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uID=getRef(position).getKey();
                                Intent intent=new Intent(MyOrdersActivity.this,MyOrderProductsActivity.class);
                                intent.putExtra("uid",uID);
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public MyOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_orders_layout,parent,false);
                        return  new MyOrdersViewHolder(view);
                    }
                };

        adapter.notifyDataSetChanged();
        myorderList.hasFixedSize();
        adapter.startListening();
        myorderList.setAdapter(adapter);

    }




    public static class MyOrdersViewHolder extends  RecyclerView.ViewHolder
    {
         public TextView dateTime;
         public Button showProductsBtn;

        public MyOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTime=itemView.findViewById(R.id.myorder_date_time);
            showProductsBtn=itemView.findViewById(R.id.show_myall_products_btn);
        }
    }


}

