package com.meet.grocerybooking.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.meet.grocerybooking.R;
import com.meet.grocerybooking.shopkeeper.ShopkeeperMainActivity;

import java.util.Objects;

public class ShopkeeperLogInActivity extends AppCompatActivity {


    private EditText email, pwd;

    private String sEmail, sPwd;

    private FirebaseAuth auth;

    private ProgressDialog pd;

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (FirebaseAuth.getInstance().getCurrentUser() != null){
//            startActivity(new Intent(ShopkeeperLogInActivity.this, ShopkeeperMainActivity.class));
//            finish();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_log_in);

        pd = new ProgressDialog(this);

        email = findViewById(R.id.shopkeeper_login_email);
        pwd = findViewById(R.id.shopkeeper_login_password);
        Button btnLogin = findViewById(R.id.shopkeeper_btn_login);
        TextView openReg = findViewById(R.id.s_tv_reg);

        auth = FirebaseAuth.getInstance();

        SharedPreferences sharedPreferences = this.getSharedPreferences("sLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

//        if (sharedPreferences.getString("isLogin", "false").equals("yes")) {
//            editor.putString("sIsLogin", "false");
//            editor.apply();
//        }

        btnLogin.setOnClickListener(v -> validateData());

        openReg.setOnClickListener(v -> {
            startActivity(new Intent(ShopkeeperLogInActivity.this, ShopkeeperRegisterActivity.class));
            finish();
        });
    }

    private void validateData() {
        sEmail = email.getText().toString();
        sPwd = pwd.getText().toString();

        if (sEmail.isEmpty()) {
            email.setError("Required");
            email.requestFocus();
        } else if (sPwd.isEmpty()) {
            pwd.setError("Required");
            pwd.requestFocus();
        } else {
            loginShopkeeper();
        }
    }

    private void loginShopkeeper() {
        pd.setMessage("Loading...");
        pd.show();


//        FirebaseDatabase.getInstance().getReference().child("shopkeeper").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.hasChild(auth.getCurrentUser().getUid())) {

                    auth.signInWithEmailAndPassword(sEmail, sPwd).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            pd.dismiss();
                            startActivity(new Intent(ShopkeeperLogInActivity.this, ShopkeeperMainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(ShopkeeperLogInActivity.this, "Error : " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(e -> {
                        pd.dismiss();
                        Toast.makeText(ShopkeeperLogInActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
//                } else {
//                    Toast.makeText(ShopkeeperLogInActivity.this, "You are not a Shopkeeper", Toast.LENGTH_SHORT).show();
//                }
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
}