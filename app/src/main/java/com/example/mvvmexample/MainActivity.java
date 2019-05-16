package com.example.mvvmexample;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @NonNull
    private ListView categoriesLst;
    @NonNull
    private ListView couponsLst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        categoriesLst = findViewById(R.id.category_list);
        couponsLst = findViewById(R.id.coupon_list);


    }
    
}
