package com.meet.grocerybooking.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.meet.grocerybooking.R;
import com.meet.grocerybooking.admin.AdminMainActivity;

public class AdminLogInActivity extends AppCompatActivity {

    private EditText etEmail, etPwd;
    private Button login;

    private String email, pwd;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_log_in);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("isLogin", "false").equals("yes")) {
            openDash();
        }

        etEmail = findViewById(R.id.admin_login_email);
        etPwd = findViewById(R.id.admin_login_password);

        login = findViewById(R.id.admin_btn_login);

        login.setOnClickListener(v -> validateData());

    }

    private void validateData() {
        email = etEmail.getText().toString();
        pwd = etPwd.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError("Please enter email");
            etEmail.requestFocus();
        } else if (pwd.isEmpty()) {
            etPwd.setError("Please enter email");
            etPwd.requestFocus();
        } else if (email.equals("admin@gmail.com") && pwd.equals("123456")) {
            editor.putString("isLogin", "yes");
            editor.commit();
            openDash();
        } else {
            Toast.makeText(this, "Please enter correct email and password.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openDash() {
        startActivity(new Intent(AdminLogInActivity.this, AdminMainActivity.class));
        finish();
    }
}