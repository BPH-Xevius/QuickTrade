package com.example.xeviu.quicktrade;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchItemResult extends AppCompatActivity {

    private ListView l_result;
    private FirebaseAuth mAuth;
    private DatabaseReference db,db2;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item_result);

        db=FirebaseDatabase.getInstance().getReference("productos");
        mAuth = FirebaseAuth.getInstance();
        l_result=(ListView)findViewById(R.id.lst_itemresult);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String key = extras.getString("keyvalue");
            String type = extras.getString("typevalue");

            if (type.equals("usrnme")) {
                db2 = FirebaseDatabase.getInstance().getReference("usuarios");
                db2.orderByChild(type).equalTo(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Users user = ds.getValue(Users.class);
                            uid = user.getId();
                        }
                        db.orderByChild("user").equalTo(uid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayAdapter<String> adapter;
                                ArrayList<String> list = new ArrayList<>();

                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    Items item = ds.getValue(Items.class);
                                    String name = item.getName();
                                    list.add(name);
                                }
                                adapter = new ArrayAdapter<>(SearchItemResult.this, android.R.layout.simple_list_item_1, list);
                                l_result.setAdapter(adapter);

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

            } else {

                db.orderByChild(type).equalTo(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayAdapter<String> adapter;
                        ArrayList<String> list = new ArrayList<>();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Items item = ds.getValue(Items.class);
                            String name = item.getName();
                            list.add(name);
                        }
                        adapter = new ArrayAdapter<>(SearchItemResult.this, android.R.layout.simple_list_item_1, list);
                        l_result.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        }


    }
}
