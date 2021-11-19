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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shokievarun.kmstores.Model.MyOrders;

import java.util.ArrayList;

public class AdminOrdersBackUpActivity extends AppCompatActivity {



        private RecyclerView myorderListadmin;
        private DatabaseReference myordersAdminRef;
        private String date;
        private ArrayList<String> myList;
        private LinearLayoutManager mLayoutmanager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders_back_up);

            myordersAdminRef = FirebaseDatabase.getInstance().getReference().child("Orders Backup");


            myorderListadmin = findViewById(R.id.my_orders_list_admin);
            mLayoutmanager =new LinearLayoutManager(this);
            mLayoutmanager.setReverseLayout(true);
            mLayoutmanager.setStackFromEnd(true);
            myorderListadmin.setLayoutManager(mLayoutmanager);



            ImageView backimage =(ImageView)findViewById(R.id.back_arrow_my_orders_admin);


            backimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(AdminOrdersBackUpActivity.this,AdminHomeActivity.class);
                    finish();
                }
            });


            myOrdersListenerAdmin();

        }


        public void myOrdersListenerAdmin() {
            myList = new ArrayList<String>();
            myordersAdminRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    .setQuery(myordersAdminRef,MyOrders.class)
                    .build();

            FirebaseRecyclerAdapter<MyOrders, AdminOrdersBackUpActivity.AdminOrdersViewHolder> adapter=
                    new FirebaseRecyclerAdapter<MyOrders,  AdminOrdersBackUpActivity.AdminOrdersViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull  AdminOrdersBackUpActivity.AdminOrdersViewHolder holder, int position, @NonNull MyOrders model) {

                            final String name = myList.get(position);
                            holder.dateTimeAdmin.setText(name);


                            holder.showProductsBtnAdmin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String uID=getRef(position).getKey();
                                    Intent intent=new Intent(AdminOrdersBackUpActivity.this,AdminOrdersUserProductsActivity.class);
                                    intent.putExtra("uid",uID);
                                    startActivity(intent);
                                }
                            });


                            holder.userDetailsBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String uID=getRef(position).getKey();
                                    Intent intent=new Intent(AdminOrdersBackUpActivity.this,AdminOrdersUserDetailsActivity.class);
                                    intent.putExtra("uid",uID);
                                    startActivity(intent);
                                }
                            });

                        }

                        @NonNull
                        @Override
                        public  AdminOrdersBackUpActivity.AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_orders_backup_layout,parent,false);
                            return  new  AdminOrdersBackUpActivity.AdminOrdersViewHolder(view);
                        }
                    };

            adapter.notifyDataSetChanged();
            myorderListadmin.hasFixedSize();
            adapter.startListening();
            myorderListadmin.setAdapter(adapter);

        }




        public static class AdminOrdersViewHolder extends  RecyclerView.ViewHolder
        {
            public TextView dateTimeAdmin;
            public Button showProductsBtnAdmin,userDetailsBtn;

            public AdminOrdersViewHolder(@NonNull View itemView) {
                super(itemView);

                dateTimeAdmin=itemView.findViewById(R.id.myorder_date_time_admin);
                userDetailsBtn=itemView.findViewById(R.id.show_user_details_btn_admin);
                showProductsBtnAdmin=itemView.findViewById(R.id.show_myall_products_btn_admin);
            }
        }


    }

