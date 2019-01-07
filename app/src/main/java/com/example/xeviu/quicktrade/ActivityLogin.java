package com.example.xeviu.quicktrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ActivityLogin extends AppCompatActivity {


    private EditText f_eml,f_psw;
    private Button b_login, b_newuser;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        f_eml=(EditText)findViewById(R.id.fld_email);
        f_psw=(EditText)findViewById(R.id.fld_pswd);
        b_login=(Button)findViewById(R.id.btn_log);
        b_newuser=(Button)findViewById(R.id.btn_new);

        mAuth = FirebaseAuth.getInstance();

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email =f_eml.getText().toString();
                String password =f_psw.getText().toString();

                login(email,password);

            }
        });

        b_newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ActivityReg.class);
                startActivityForResult(i,1);
            }
        });
    }

    private void login(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(ActivityLogin.this, "Login successful.",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);

                        } else {
                            Toast.makeText(ActivityLogin.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}

