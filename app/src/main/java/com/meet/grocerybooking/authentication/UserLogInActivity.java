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
import com.meet.grocerybooking.user.UserMainActivity;

import java.util.Objects;

public class UserLogInActivity extends AppCompatActivity {

    private EditText email, pwd;

    private String sEmail, sPwd;

    private FirebaseAuth auth;

    private ProgressDialog pd;

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(UserLogInActivity.this, UserMainActivity.class));
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log_in);

        pd = new ProgressDialog(this);

        email = findViewById(R.id.login_email);
        pwd = findViewById(R.id.login_password);
        Button btnLogin = findViewById(R.id.btn_login);
        TextView openReg = findViewById(R.id.tv_reg);

        auth = FirebaseAuth.getInstance();

        SharedPreferences sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getString("isLogin", "false").equals("yes")) {
            editor.putString("isLogin", "false");
            editor.apply();
        }

        btnLogin.setOnClickListener(v -> validateData());

        openReg.setOnClickListener(v -> {
            startActivity(new Intent(UserLogInActivity.this, UserRegisterActivity.class));
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
            loginUSer();
        }
    }

    private void loginUSer() {
        pd.setMessage("Loading...");
        pd.show();

        auth.signInWithEmailAndPassword(sEmail, sPwd).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                pd.dismiss();
                startActivity(new Intent(UserLogInActivity.this, UserMainActivity.class));
                finish();
            } else {
                Toast.makeText(UserLogInActivity.this, "Error : " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            pd.dismiss();
            Toast.makeText(UserLogInActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}