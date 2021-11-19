package com.shokievarun.kmstores;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shokievarun.kmstores.Model.Cart;
import com.shokievarun.kmstores.Model.Products;
import com.shokievarun.kmstores.Prevalent.Prevalent;
import com.shokievarun.kmstores.ViewHolder.CartViewHolder;
import com.shokievarun.kmstores.ViewHolder.MyRecyclerViewAdapter;

import java.util.ArrayList;

public class CartActivity<overTotalPrice> extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextProcessBtn,whatsappBtnCart;
    private TextView txtTotalAmount;
    private TextView txtMsg1;
    private int  overTotalPrice=0;
    private int oneProductSum=0;
    private MyRecyclerViewAdapter adapter;
    private  ArrayList<Products> tA;
    private int totalPrice;
    private LinearLayout linearLayoutcart;

    private String msg,phoneNo;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView=findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        linearLayoutcart=findViewById(R.id.linerar_layout_cart);


        nextProcessBtn=(Button)findViewById(R.id.next_process_btn);
        txtTotalAmount=(TextView)findViewById(R.id.total_price_cart);
        txtMsg1=(TextView)findViewById(R.id.msg1);
        whatsappBtnCart=(Button)findViewById(R.id.whatsapp_btn_cart);
        msg="Hey! Hi, when my order will be delivered? ";
        phoneNo="8884879145";


        ImageView backimage =(ImageView)findViewById(R.id.back_arrow_cart);


        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CartActivity.this,HomeActivity.class);
                finish();
            }
        });


        whatsappBtnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean installed=appInstalledOrNot("com.whatsapp");

                if(installed){
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+91"+phoneNo+"&text="+msg));
                    startActivity(intent);
                }else
                {
                    Toast.makeText(CartActivity.this,"Whatsapp not installed on your device",Toast.LENGTH_SHORT).show();
                }

            }
        });

        nextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(totalPrice<99)
                {
                    Toast.makeText(CartActivity.this,"Order Minimum 99 Rs/-  ",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(CartActivity.this,PaymentUpiActivity.class);
                    intent.putExtra("Total Price", String.valueOf(totalPrice));
                    startActivity(intent);
                }

            }
        });


    }

    private void totalAmount(){

        DatabaseReference totalAmountListRef= FirebaseDatabase.getInstance().getReference().child("Cart List")
                .child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Products");

        ValueEventListener eventListener= new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                 tA=new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Products pro=new Products(ds.child("price").getValue(String.class),
                            ds.child("quantity").getValue(String.class));

                    tA.add(pro);
                }
                  totalPrice=0;
                for(int i=0;i<tA.size();i++){
                    totalPrice = totalPrice+ ((Integer.valueOf(tA.get(i).getPrice()))*Integer.valueOf(tA.get(i).getQuantity()));
                }
                txtTotalAmount.setText("Total Amount:  "+ totalPrice +" Rs/-");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        totalAmountListRef.addListenerForSingleValueEvent(eventListener);
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




    @Override
    protected void onStart() {
        super.onStart();
        totalAmount();
    //    checkOrderState();
       // myCartDisplay();




    final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List");

          FirebaseRecyclerOptions<Cart> options=
                  new FirebaseRecyclerOptions.Builder<Cart>()
                  .setQuery(cartListRef.child("User View")
                  .child(Prevalent.currentOnlineUser.getPhone())
                  .child("Products"),Cart.class)
                  .build();

          FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                  =new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
              @Override
              protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {


                  holder.txtProductQuantity.setText("* "+model.getQuantity());
                  holder.txtProductRate.setText(model.getPrice());
                  holder.txtProductName.setText(model.getPname());
                  oneProductSum=((Integer.valueOf(model.getPrice()))*Integer.valueOf(model.getQuantity()));
                  holder.txtProductSumPrice.setText(oneProductSum+" Rs/-");


                  int oneTypeProductPrice=((Integer.valueOf(model.getPrice()))*Integer.valueOf(model.getQuantity()));

                  overTotalPrice=overTotalPrice+oneTypeProductPrice;



             //     txtTotalAmount.setText("Total Amount:  "+ overTotalPrice +" Rs/-");

                  holder.itemView.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          CharSequence options[]=new CharSequence[]
                                  {
                                          "Edit",
                                          "Remove"
                                  };
                          AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                          builder.setTitle("Cart Options");

                          builder.setItems(options, new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {

                                  if(which==0)
                                  {
                                      Intent intent=new Intent(CartActivity.this,ProductDetailsActivity.class);
                                      intent.putExtra("pid",model.getPid());
                                      startActivity(intent);
                                  }
                                  if(which==1)
                                  {
                                      cartListRef.child("User View")
                                              .child(Prevalent.currentOnlineUser.getPhone())
                                              .child("Products")
                                              .child(model.getPid())
                                              .removeValue()
                                              .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                  @Override
                                                  public void onComplete(@NonNull Task<Void> task) {

                                                      if(task.isSuccessful())
                                                      {
                                                          Toast.makeText(CartActivity.this,"Item Removed Succesfully.",Toast.LENGTH_SHORT).show();

                                                      }
                                                  }
                                              });
                                  }
                              }
                          });
                          builder.show();
                      }
                  });

              }



              @NonNull
              @Override
              public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                  View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                  CartViewHolder holder=new CartViewHolder(view);
                  return holder;
              }


          };

          recyclerView.setAdapter(adapter);
          adapter.startListening();
      }





      private void checkOrderState()
      {
          DatabaseReference ordersRef;
          ordersRef=FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());

          ordersRef.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  if(snapshot.exists())
                  {
                      String shippingState= snapshot.child("state").getValue().toString();
                      String userName= snapshot.child("name").getValue().toString();

                      if(shippingState.equals("shipped"))
                      {
                          txtTotalAmount.setText("Dear "+userName+"\n order is shipped succesfully");
                          recyclerView.setVisibility(View.GONE);
                          linearLayoutcart.setVisibility(View.GONE);

                          txtMsg1.setVisibility(View.VISIBLE);
                          txtMsg1.setText("Congratulations your previous order has been " + "shipped successfully Soon you will receive at your door step." + " For more details contact 8884871945");
                          nextProcessBtn.setVisibility(View.GONE);

                    //      Toast.makeText(CartActivity.this,"You can purchase more products once you receive your order",Toast.LENGTH_SHORT).show();
                      }
                      else if(shippingState.equals("not Shipped"))
                      {
                         txtTotalAmount.setText("Order Confirmed");
                         recyclerView.setVisibility(View.GONE);

                          linearLayoutcart.setVisibility(View.GONE);
                          whatsappBtnCart.setVisibility(View.VISIBLE);
                          txtMsg1.setVisibility(View.VISIBLE);
                          nextProcessBtn.setVisibility(View.GONE);

                       //   Toast.makeText(CartActivity.this,"You can purchase more products once you receive your order",Toast.LENGTH_SHORT).show();
                      }

                  }


              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });
      }


   /* public void myCartDisplay() {

        DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List")
                .child("User View").child(Prevalent.currentOnlineUser.getPhone());
        cartListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                double count = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot recordSnapshot: snapshot.getChildren()) {

                        double price = Double.valueOf(recordSnapshot.child("price").getValue(String.class));
                        double quantity = Double.valueOf(recordSnapshot.child("quantity").getValue(String.class));
                        count = count + (price*quantity);
                        txtTotalAmount.setText("Total Amount:  "+ overTotalPrice +" Rs/-");


                    }

                }
                adapter= new MyRecyclerViewAdapter(CartActivity.this,);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

*/


}