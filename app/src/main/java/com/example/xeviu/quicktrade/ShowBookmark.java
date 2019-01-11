package com.example.xeviu.quicktrade;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowBookmark extends AppCompatActivity {

    private ListView li_items;
    private FirebaseAuth mAuth;
    private DatabaseReference db;
    private String user,item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bookmark);

        li_items=(ListView)findViewById(R.id.list_items);

        db= FirebaseDatabase.getInstance().getReference("bookmark");
        mAuth = FirebaseAuth.getInstance();

        db.orderByChild("user").equalTo(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            ArrayAdapter<String> adapter;
                            ArrayList<String> list=new ArrayList<>();

                            for(DataSnapshot ds: dataSnapshot.getChildren()){
                                Bookmark bm = new Bookmark(user,item);
                                String name=bm.getItem();
                                list.add(name);
                            }
                            adapter = new ArrayAdapter<>(ShowBookmark.this,android.R.layout.simple_list_item_1,list);
                            li_items.setAdapter(adapter);
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

        li_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String itemname= li_items.getItemAtPosition(position).toString();

                Intent i = new Intent(ShowBookmark.this, ShowItem.class);
                i.putExtra("itemname",itemname);
                startActivity(i);
            }
        });
    }
}
