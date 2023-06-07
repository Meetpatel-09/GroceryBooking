package com.meet.grocerybooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.meet.grocerybooking.authentication.AdminLogInActivity;
import com.meet.grocerybooking.authentication.ShopkeeperLogInActivity;
import com.meet.grocerybooking.authentication.UserLogInActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btnUser = findViewById(R.id.btn_user_login);
        Button btnShopkeeper = findViewById(R.id.btn_shopkeeper_login);
        Button btnAdmin = findViewById(R.id.btn_admin_login);

        btnUser.setOnClickListener(v -> {
            startActivity(new Intent(StartActivity.this, UserLogInActivity.class));
        });

        btnShopkeeper.setOnClickListener(v -> {
            startActivity(new Intent(StartActivity.this, ShopkeeperLogInActivity.class));
        });

        btnAdmin.setOnClickListener(v -> {
            startActivity(new Intent(StartActivity.this, AdminLogInActivity.class));
        });
    }
}