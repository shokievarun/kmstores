package com.shokievarun.kmstores;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shokievarun.kmstores.Fragments.FirstFragment;
import com.shokievarun.kmstores.Fragments.SecondFragment;
import com.shokievarun.kmstores.Fragments.ThirdFragment;
import com.shokievarun.kmstores.Model.Products;
import com.shokievarun.kmstores.Prevalent.Prevalent;
import com.shokievarun.kmstores.ViewHolder.MyRecyclerViewAdapter;
import com.shokievarun.kmstores.ViewHolder.PagerAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private String category="";
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView userNameTextView;
    private CircleImageView profileImageView;
    private View headerView;
    private ArrayList<Products>  productsList;
     private String type="";
     private MyRecyclerViewAdapter adapter;

     private  ViewPager viewPager;
     private  TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        viewPager=findViewById(R.id.viewpagercontent);
        setupViewPager(viewPager);

        tabLayout=findViewById(R.id.tablayout7);
        tabLayout.setupWithViewPager(viewPager);



        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null) {
            type = getIntent().getExtras().get("Admin").toString();
        }

        ProductsRef= FirebaseDatabase.getInstance().getReference().child("Products");


        Paper.init(this);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("K M Stores");
        setSupportActionBar(toolbar);




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!type.equals("Admin")) {
                    Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent);
                }
            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        if(!type.equals("Admin")) {

            headerView= navigationView.getHeaderView(0);
            userNameTextView = headerView.findViewById(R.id.user_profile_name);
            //   profileImageView=headerView.findViewById(R.id.user_profile_image);
            userNameTextView.setText("Hello, "+ Prevalent.currentOnlineUser.getName());
            //  Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);
        }

      /*  recyclerView=findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
       layoutManager=new LinearLayoutManager(this);
       recyclerView.setLayoutManager(layoutManager);*/



       if(!type.equals("Admin"))
       {
       //    myOrdersListener();
       //    filterProducts(category);

       }


    }


    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FirstFragment(), "Needs");
        adapter.addFrag(new SecondFragment(), "99↓");
        adapter.addFrag(new ThirdFragment(), "MRP↓");
        viewPager.setAdapter(adapter);
    }



    /*public void myOrdersListener() {
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
                adapter = new MyRecyclerViewAdapter(HomeActivity.this, productsList);
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
                adapter = new MyRecyclerViewAdapter(HomeActivity.this, productsList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
*/


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id==R.id.action_search)
        {
            if (!type.equals("Admin")) {
                Intent intent = new Intent(HomeActivity.this, UserCategoryActivity.class);
                startActivity(intent);
                return true;
            }
            else
                {
                Intent intent = new Intent(HomeActivity.this, UserCategoryActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);
                return true;
                }
        }
        else if(id==R.id.action_qrcode) {
            if (!type.equals("Admin")) {
                Intent intent = new Intent(HomeActivity.this, ScannerActivity.class);
                startActivity(intent);
                return true;
            }
        }
      /*  else if(id==R.id.action_category)
        {
            if (!type.equals("Admin")) {
                Intent intent = new Intent(HomeActivity.this, UserCategoryActivity.class);
                startActivity(intent);
                return true;
            }
            else
            {
                Intent intent = new Intent(HomeActivity.this, UserCategoryActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);
                return true;
            }
        }
        else if(id==R.id.action_all)
        {
        //    myOrdersListener();
            return true;
        }
        else if(id==R.id.action_daily_deals)
        {
            category="daily deals";
         //   filterProducts(category);
            return true;
        }
        else if(id==R.id.action_1)
        {
            category="Rice";
         //   filterProducts(category);
            return true;
        }
        else if(id==R.id.action_2)
        {
            category="Oils";
          //  filterProducts(category);
            return true;
        }
        else if(id==R.id.action_3)
        {
            category="Vegetables";
        //    filterProducts(category);
            return true;
        }
        else if(id==R.id.action_4)
        {
            category="Pulses";
        //    filterProducts(category);
            return true;
        }
        else if(id==R.id.action_5)
        {
            category="Biscuits";
        //    filterProducts(category);
            return true;
        }
        else if(id==R.id.action_6)
        {
            category="Cosmetics";
      //      filterProducts(category);
            return true;
        }
        else if(id==R.id.action_7)
        {
            category="Detergents";
       //     filterProducts(category);
            return true;
        }
        else if(id==R.id.action_8)
        {
            category="Masala Items";
       //     filterProducts(category);
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();
        if (id == R.id.nav_cart) {
            if (!type.equals("Admin")) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_search) {
            if (!type.equals("Admin")) {
                Intent intent = new Intent(HomeActivity.this, UserCategoryActivity.class);
                startActivity(intent);
            }
        }
        else if (id == R.id.nav_orders)
        {
            if (!type.equals("Admin")) {
                Intent intent = new Intent(HomeActivity.this, MyOrdersActivity.class);
                startActivity(intent);
            }
        }
     /*   else if (id == R.id.nav_categories)
        {

        }*/
        else if (id == R.id.nav_settings) {
            if (!type.equals("Admin")) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }

        }  else if (id == R.id.nav_about_us)
        {
            if (!type.equals("Admin")) {
                Intent intent = new Intent(HomeActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        }
        else if (id == R.id.nav_logout) {

            if (!type.equals("Admin")) {
                Paper.book().destroy();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
        else if (id == R.id.nav_about_us)
        {
            if (!type.equals("Admin")) {
                Intent intent = new Intent(HomeActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }




}