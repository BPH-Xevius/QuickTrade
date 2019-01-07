package com.example.xeviu.quicktrade;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountSettings extends AppCompatActivity {


    private EditText f_name,f_lastname,f_address;
    private Button b_update, b_canel;
    private String key;
    private FirebaseAuth mAuth;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);


        f_name=(EditText)findViewById(R.id.fld_name);
        f_lastname=(EditText)findViewById(R.id.fld_lastname);
        f_address=(EditText)findViewById(R.id.fld_address);

        b_update=(Button)findViewById(R.id.btn_update);
        b_canel=(Button)findViewById(R.id.btn_cancel);

        mAuth = FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance().getReference("usuarios");

        db.orderByChild("id").equalTo(mAuth.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Users us= dataSnapshot.getValue(Users.class);

                f_name.setText(us.name);
                f_lastname.setText(us.lastname);
                f_address.setText(us.address);
                key=dataSnapshot.getKey();

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

        b_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=f_name.getText().toString();
                String lastname=f_lastname.getText().toString();
                String address=f_address.getText().toString();
                DatabaseReference ref= db.child(key);
                Map<String, Object> update = new HashMap<>();
                update.put("name",name);
                update.put("lastname",lastname);
                update.put("address",address);
                ref.updateChildren(update);
            }
        });

        b_canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent i=new Intent();
                setResult(RESULT_CANCELED,i);
                finish();
            }
        });
    }
}
