package com.example.xeviu.quicktrade;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainItems extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference db;
    private Button b_manageitems,b_itemsearch;
    private ListView li_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_items);

        li_items=(ListView)findViewById(R.id.list_items);
        b_manageitems=(Button)findViewById(R.id.btn_manageitems);
        b_itemsearch=(Button)findViewById(R.id.btn_itemsearch);

        db=FirebaseDatabase.getInstance().getReference("productos");

        mAuth = FirebaseAuth.getInstance();

        b_itemsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SearchItem.class);
                startActivityForResult(i,1);
            }
        });

        b_manageitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ManageItems.class);
                startActivityForResult(i,1);
            }
        });

        db.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){

                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            ArrayAdapter<String> adapter;
                            ArrayList<String> list=new ArrayList<>();

                            for(DataSnapshot ds: dataSnapshot.getChildren()){
                                Items item=ds.getValue(Items.class);
                                String name=item.getName();
                                list.add(name);
                            }
                            adapter = new ArrayAdapter<>(MainItems.this,android.R.layout.simple_list_item_1,list);
                            li_items.setAdapter(adapter);




                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else{

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

                Intent i = new Intent(MainItems.this, ShowItem.class);
                i.putExtra("itemname",itemname);
                startActivity(i);
            }
        });



    }
}
