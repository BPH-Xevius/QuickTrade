package com.example.xeviu.quicktrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ActivityReg extends AppCompatActivity {


    private EditText f_email,f_psssword,f_username,f_name,f_lastname,f_address;
    private Button b_reg, b_can;
    private FirebaseAuth mAuth;
    private DatabaseReference db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        f_email=(EditText)findViewById(R.id.fld_email);
        f_psssword=(EditText)findViewById(R.id.f_psssword);
        f_username=(EditText)findViewById(R.id.fld_username);
        f_name=(EditText)findViewById(R.id.fld_name);
        f_lastname=(EditText)findViewById(R.id.fld_lastname);
        f_address=(EditText)findViewById(R.id.fld_adress);
        b_reg=(Button)findViewById(R.id.btn_newuser);
        b_can=(Button)findViewById(R.id.btn_cancel);

        db=FirebaseDatabase.getInstance().getReference("usuarios");

        mAuth = FirebaseAuth.getInstance();

        b_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email =f_email.getText().toString();
                final String password =f_psssword.getText().toString();
                final String username =f_username.getText().toString();
                final String name =f_name.getText().toString();
                final String lastname=f_lastname.getText().toString();
                final String address=f_address.getText().toString();

                db.orderByChild("usrnme").equalTo(f_username.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            Toast.makeText(ActivityReg.this,R.string.msg_existinguser,Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            check(email,password,username,name,lastname,address);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        b_can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent i=new Intent();
                setResult(RESULT_CANCELED,i);
                finish();
            }
        });
    }

    private void check(String email,String password,String username,String name,String lastname,String address)
    {
        if(!TextUtils.isEmpty(email)){
            if(!TextUtils.isEmpty(password)) {
                if(!TextUtils.isEmpty(username)) {
                    if(!TextUtils.isEmpty(name)) {
                        if(!TextUtils.isEmpty(lastname)) {
                            if(!TextUtils.isEmpty(address)){
                                registr(email,password);


                            }
                            else{
                                Toast.makeText(ActivityReg.this, R.string.msg_noaddress, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(ActivityReg.this, R.string.msg_nolastname, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(ActivityReg.this, R.string.msg_noname, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(ActivityReg.this, R.string.msg_nousername, Toast.LENGTH_SHORT).show(); }
            }
            else{
                Toast.makeText(ActivityReg.this, R.string.msg_nopassword, Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(ActivityReg.this, R.string.msg_noemail, Toast.LENGTH_SHORT).show();
        }
    }

    private void registr(String email, String password)
    {


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(ActivityReg.this, "Authentication successful.",
                                    Toast.LENGTH_SHORT).show();
                            adddb();





                        } else {
                            Toast.makeText(ActivityReg.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void adddb()
    {

        String username =f_username.getText().toString();
        String name =f_name.getText().toString();
        String lastname=f_lastname.getText().toString();
        String address=f_address.getText().toString();
        String id=mAuth.getUid();
        String key=db.push().getKey();
        Users us = new Users(id,username,name,lastname,address);
        db.child(key).setValue(us);
        mAuth.signOut();
    }




}
