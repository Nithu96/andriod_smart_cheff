package com.niwarthana.smartchef.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.niwarthana.smartchef.R;

public class MainActivity extends AppCompatActivity {
    private FrameLayout btnOption1, btnOption2, btnOption3, btnOption4, btnOption5, btnOption6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOption1 = findViewById(R.id.btn_option_1);
        btnOption2 = findViewById(R.id.btn_option_2);
        btnOption3 = findViewById(R.id.btn_option_3);
        btnOption4 = findViewById(R.id.btn_option_4);
        btnOption5 = findViewById(R.id.btn_option_5);
        btnOption6 = findViewById(R.id.btn_option_6);

        /**
         * Star your app Register Product Activity
         */
        btnOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterProductActivity.class);
                startActivity(i);
            }
        });

        /**
         * Star your app Display Product Activity
         */
        btnOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DisplayProductsActivity.class);
                startActivity(i);
            }
        });

        /**
         * Star your app  Availability Activity
         */
        btnOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AvailabilityActivity.class);
                startActivity(i);
            }
        });

        /**
         * Star your app Edit Products Activity
         */
        btnOption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditProductsActivity.class);
                startActivity(i);
            }
        });

        /**
         * Star your app Search Activity
         */
        btnOption5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });

        /**
         * Star your app Search Activity
         */
        btnOption6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RecipesActivity.class);
                startActivity(i);
            }
        });
    }
}
