package com.example.xeviu.quicktrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchItem extends AppCompatActivity {

    private String key,type,data;
    private Spinner s_category;
    private EditText f_searchname,f_searchprice,f_searchuser;
    private Button b_searchname,b_searchcategory,b_searchprice,b_searchuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        f_searchname=(EditText)findViewById(R.id.fld_searchname);
        f_searchprice=(EditText)findViewById(R.id.fld_searchprice);
        f_searchuser=(EditText)findViewById(R.id.fld_searchuser);
        b_searchname=(Button) findViewById(R.id.btn_searchname);
        b_searchcategory=(Button) findViewById(R.id.btn_searchcategory);
        b_searchprice=(Button) findViewById(R.id.btn_searchprice);
        b_searchuser=(Button) findViewById(R.id.btn_searchuser);
        s_category=(Spinner) findViewById(R.id.spn_searchcategory);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_category.setAdapter(adapter);


        b_searchname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key=f_searchname.getText().toString();
                type="name";
                data(key,type);
            }
        });

        b_searchcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key=s_category.getSelectedItem().toString();
                type="category";
                data(key,type);
            }
        });

        b_searchprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key=f_searchprice.getText().toString();
                type="price";
                data(key,type);
            }
        });

        b_searchuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key=f_searchuser.getText().toString();
                type="usrnme";
                data(key,type);

            }
        });
    }

    public void data(String key,String type){

        Intent i = new Intent(SearchItem.this, SearchItemResult.class);
        i.putExtra("keyvalue",key);
        i.putExtra("typevalue",type);
        startActivity(i);
    }
}
