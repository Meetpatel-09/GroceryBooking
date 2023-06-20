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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.meet.grocerybooking.R;

import java.util.HashMap;
import java.util.Objects;

public class ShopkeeperRegisterActivity extends AppCompatActivity {

    private EditText fName, shopName, email, pwd, cPwd, num;
    private String sName;
    private String sSName;
    private String sEmail;
    private String sPwd;
    private String sNum;

    private FirebaseAuth auth;
    private DatabaseReference reference;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_register);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        pd = new ProgressDialog(this);

        fName = findViewById(R.id.s_reg_name);
        shopName = findViewById(R.id.s_reg_shop_name);
        email = findViewById(R.id.s_reg_email);
        pwd = findViewById(R.id.s_reg_password);
        cPwd = findViewById(R.id.s_reg_c_password);
        num = findViewById(R.id.s_reg_phone);

        Button btnReg = findViewById(R.id.s_btn_reg);
        TextView openLogIn = findViewById(R.id.s_tv_login);

        SharedPreferences sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getString("isLogin", "false").equals("yes")) {
            editor.putString("isLogin", "false");
            editor.apply();
        }

        btnReg.setOnClickListener(v -> validateData());

        openLogIn.setOnClickListener(v -> {
            startActivity(new Intent(ShopkeeperRegisterActivity.this, UserLogInActivity.class));
            finish();
        });
    }

    private void openCProfile() {
        startActivity(new Intent(this, ShopkeeperLogInActivity.class));
        finish();
    }

    private void validateData() {
        sName = fName.getText().toString();
        sSName = shopName.getText().toString();
        sEmail = email.getText().toString();
        sPwd = pwd.getText().toString();
        String sCPwd = cPwd.getText().toString();
        sNum = num.getText().toString();

        if (sName.isEmpty()) {
            fName.setError("Required");
            fName.requestFocus();
        } else if (sSName.isEmpty()) {
            shopName.setError("Required");
            shopName.requestFocus();
        } else if (sEmail.isEmpty()) {
            email.setError("Required");
            email.requestFocus();
        } else if (sPwd.isEmpty()) {
            pwd.setError("Required");
            pwd.requestFocus();
        } else if (sCPwd.isEmpty()) {
            pwd.setError("Required");
            pwd.requestFocus();
        } else if (sNum.isEmpty()) {
            num.setError("Required");
            num.requestFocus();
        } else if (sNum.length() != 10) {
            num.setError("Invalid");
            num.requestFocus();
        } else {
            if (!sPwd.equals(sCPwd)) {
                cPwd.setError("Password not match");
            } else {
                createShopkeeper();
            }
        }
    }

    private void createShopkeeper() {
        pd.setMessage("Loading...");
        pd.show();
        auth.createUserWithEmailAndPassword(sEmail, sPwd).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                uploadData();
            } else {
                pd.dismiss();
                Toast.makeText(ShopkeeperRegisterActivity.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            pd.dismiss();
            Toast.makeText(ShopkeeperRegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void uploadData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("id", Objects.requireNonNull(auth.getCurrentUser()).getUid());
        map.put("name", sName);
        map.put("shopName", sSName);
        map.put("email", sEmail);
        map.put("phone", sNum);
        map.put("imageUrl", "default");

        reference.child("shopkeeper").child(auth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                pd.dismiss();
                Toast.makeText(ShopkeeperRegisterActivity.this, "Registration Successful!!!", Toast.LENGTH_SHORT).show();
                openCProfile();
            } else {
                pd.dismiss();
                Toast.makeText(ShopkeeperRegisterActivity.this, "Error : " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            pd.dismiss();
            Toast.makeText(ShopkeeperRegisterActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

}