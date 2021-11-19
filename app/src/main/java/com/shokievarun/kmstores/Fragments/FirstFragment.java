package com.shokievarun.kmstores.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shokievarun.kmstores.Model.Products;
import com.shokievarun.kmstores.R;
import com.shokievarun.kmstores.ViewHolder.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;


public class FirstFragment extends Fragment {

    private String category="";
    private String type="";
    private ArrayList<Products> productsList;
    private DatabaseReference ProductsRef;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_first, container, false);
        setHasOptionsMenu(true);

        ProductsRef= FirebaseDatabase.getInstance().getReference().child("Products");

        recyclerView=(RecyclerView) view.findViewById(R.id.recycler_fragment_first);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        myOrdersListener();
        filterProducts(category);

        return view;
    }

     public void myOrdersListener() {
        productsList = new ArrayList<>();
        Query queryReflist=ProductsRef.orderByChild("productStatus").equalTo("Approved");
        ProductsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Products product = snapshot.getValue(Products.class);
                    productsList.add(product);

                }

                Collections.shuffle(productsList);
                adapter = new MyRecyclerViewAdapter(getActivity().getApplicationContext(), productsList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }




    public void filterProducts(String category) {
        productsList = new ArrayList<>();
        Query queryRef=ProductsRef.orderByChild("Category").equalTo(category);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Products product = snapshot.getValue(Products.class);
                    productsList.add(product);

                }

                Collections.shuffle(productsList);
                adapter = new MyRecyclerViewAdapter(getActivity().getApplicationContext(), productsList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}