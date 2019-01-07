package com.example.xeviu.quicktrade;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class OwnItem extends AppCompatActivity {

    private String key;
    private EditText f_itemname,f_description,f_price;
    private Spinner s_category;
    private Button b_updateitem,b_deleteitem,b_cancel;
    private DatabaseReference db,db2;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_item);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String itemname = extras.getString("itemname");
            //The key argument here must match that used in the other activity



            mAuth = FirebaseAuth.getInstance();

            f_itemname=(EditText) findViewById(R.id.fld_itemname);
            f_description=(EditText) findViewById(R.id.fld_description);
            s_category=(Spinner) findViewById(R.id.spn_category);
            f_price=(EditText) findViewById(R.id.fld_price);
            b_updateitem=(Button) findViewById(R.id.btn_updateitem);
            b_deleteitem=(Button) findViewById(R.id.btn_deleteitem);
            b_cancel=(Button) findViewById(R.id.btn_cancel);

           ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.planets_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s_category.setAdapter(adapter);

            db=FirebaseDatabase.getInstance().getReference("productos");

            db.orderByChild("name").equalTo(itemname).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    key= dataSnapshot.getKey();
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


            db.orderByChild("name").equalTo(itemname).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        Items item=ds.getValue(Items.class);
                        f_itemname.setText(item.getName());
                        f_description.setText(item.getDescription());
                        f_price.setText(item.getPrice());
                        if(item.getCategory().equals("Tecnología")){
                            s_category.setSelection(0);
                        }
                        else if(item.getCategory().equals("Coches")){
                            s_category.setSelection(1);
                        }
                        else if(item.getCategory().equals("Hogar")){
                            s_category.setSelection(2);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            b_updateitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name=f_itemname.getText().toString();
                    String description=f_description.getText().toString();
                    String price=f_price.getText().toString();
                    String category=s_category.getSelectedItem().toString();

                    DatabaseReference ref= db.child(key);
                    Map<String, Object> update = new HashMap<>();
                    update.put("name",name);
                    update.put("description",description);
                    update.put("price",price);
                    update.put("category",category);
                    ref.updateChildren(update);

                }
            });

            b_deleteitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseReference ref= db.child(key);
                    ref.removeValue();

                }
            });

            b_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent();
                    setResult(RESULT_CANCELED,i);
                    finish();
                }
            });
        }




    }
}