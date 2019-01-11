package com.example.xeviu.quicktrade;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowItem extends AppCompatActivity {

    private TextView t_itemname,t_description,t_category,t_price,t_username;
    private Button b_fav;
    private DatabaseReference db,db2,db3;
    private FirebaseAuth mAuth;
    private String user,item;
    private boolean isfav=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String itemname = extras.getString("itemname");
            //The key argument here must match that used in the other activity

            mAuth = FirebaseAuth.getInstance();

            t_itemname=(TextView)findViewById(R.id.tit_itemname);
            t_description=(TextView)findViewById(R.id.txt_description);
            t_category=(TextView)findViewById(R.id.txt_category);
            t_price=(TextView)findViewById(R.id.txt_price);
            t_username=(TextView)findViewById(R.id.txt_username);
            b_fav=(Button)findViewById(R.id.btn_fav);

            db=FirebaseDatabase.getInstance().getReference("productos");
            db2=FirebaseDatabase.getInstance().getReference("usuarios");
            db3=FirebaseDatabase.getInstance().getReference("bookmark");

           db.orderByChild("name").equalTo(itemname).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        Items item=ds.getValue(Items.class);
                        t_itemname.setText(item.getName());
                        t_description.setText(item.getDescription());
                        t_category.setText(item.getCategory());
                        t_price.setText(item.getPrice());

                        db2.orderByChild("id").equalTo(item.getUser()).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                Users us= dataSnapshot.getValue(Users.class);

                                t_username.setText(us.getUsrnme());

                            }
                            @Override
                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            }
                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                            }
                            @Override
                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });



                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


           db3.orderByChild("item").equalTo(itemname).addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   db3.orderByChild("user").equalTo(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           isfav=true;

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });

           if (isfav==false){
               b_fav.setText("Añadir a favoritos");
               b_fav.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       user=mAuth.getUid();
                       item=t_itemname.getText().toString();
                       adddb(user,item);
                   }
               });
           }
           else if (isfav==true){
               b_fav.setText("Esta en favoritos");
           }


        }


    }

    private void adddb(String user,String item)
    {
        String key=db.push().getKey();
        Bookmark bm = new Bookmark(user,item);
        db.child(key).setValue(bm);
    }
}
