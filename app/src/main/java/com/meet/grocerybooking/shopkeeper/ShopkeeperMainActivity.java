package com.meet.grocerybooking.shopkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.card.MaterialCardView;
import com.meet.grocerybooking.R;
import com.meet.grocerybooking.StartActivity;
import com.meet.grocerybooking.shopkeeper.chat.ManageMessagesActivity;
import com.meet.grocerybooking.shopkeeper.products.AddProductActivity;
import com.meet.grocerybooking.shopkeeper.products.ShopkeeperManageProductsActivity;

public class ShopkeeperMainActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("sLogin", MODE_PRIVATE);
        editor = sharedPreferences.edit();

//        if (sharedPreferences.getString("isLogin", "false").equals("false")) {
//            startActivity(new Intent(ShopkeeperMainActivity.this, StartActivity.class));
//            finish();
//        }

        MaterialCardView addProduct = findViewById(R.id.add_product);
        MaterialCardView manageProducts = findViewById(R.id.s_manage_products);
        MaterialCardView manageMessages = findViewById(R.id.manage_messages);
        Button logOut = findViewById(R.id.s_btn_logout);

        addProduct.setOnClickListener(this);
        manageProducts.setOnClickListener(this);
        manageMessages.setOnClickListener(this);
        logOut.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.add_product:
                intent = new Intent(ShopkeeperMainActivity.this, AddProductActivity.class);
                startActivity(intent);
                break;
            case R.id.s_manage_products:
                intent = new Intent(ShopkeeperMainActivity.this, ShopkeeperManageProductsActivity.class);
                startActivity(intent);
                break;
            case R.id.manage_messages:
                intent = new Intent(ShopkeeperMainActivity.this, ManageMessagesActivity.class);
                startActivity(intent);
                break;
            case R.id.s_btn_logout:
                editor.putString("isLogin", "false");
                editor.commit();
                intent = new Intent(ShopkeeperMainActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}