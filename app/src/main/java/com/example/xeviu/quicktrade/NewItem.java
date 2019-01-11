package com.example.xeviu.quicktrade;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewItem extends AppCompatActivity {

    private EditText f_name,f_descrip,f_cat,f_price;
    private Spinner s_category;
    private Button b_add;
    private FirebaseAuth mAuth;
    private DatabaseReference db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        f_name=(EditText)findViewById(R.id.fld_name);
        f_descrip=(EditText)findViewById(R.id.fld_description);
        f_price=(EditText)findViewById(R.id.fld_price);
        b_add=(Button) findViewById(R.id.btn_newitem);
        s_category=(Spinner) findViewById(R.id.spn_category);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_category.setAdapter(adapter);


        db=FirebaseDatabase.getInstance().getReference("productos");

        mAuth = FirebaseAuth.getInstance();

        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String name =f_name.getText().toString();
                final String desc =f_descrip.getText().toString();
                final String cat =s_category.getSelectedItem().toString();
                final String price =f_price.getText().toString();
                final String user=mAuth.getUid();

                db.orderByChild("name").equalTo(f_name.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){
                            Toast.makeText(NewItem.this,R.string.msg_existingitem,Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            adddb(name,desc,cat,price,user);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




            }
        });

    }


    private void adddb(String name,String desc,String cat, String price,String user)
    {

        String key=db.push().getKey();
        Items item = new Items(name,desc,cat,price,user);
        db.child(key).setValue(item);

    }




}
