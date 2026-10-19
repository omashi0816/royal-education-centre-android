package com.royaleducenter.student.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.royaleducenter.student.R;
import com.royaleducenter.student.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    private MaterialButton btnLogin;
    private ProgressBar progressBar;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);

        // දැනටමත් ලොග් වී ඇත්නම් කෙලින්ම Dashboard එකට යන්න
        if (sessionManager.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);

        btnLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String username = etUsername.getText() != null ? etUsername.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Enter any username");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Enter any password");
            return;
        }

        setLoading(true);

        // --- SUPER MOCK LOGIN ---
        // ඕනෑම Username එකක් සහ Password එකක් ඇතුළත් කළ විට ලොග් වීමට ඉඩ ලබා දී ඇත.
        new android.os.Handler().postDelayed(() -> {
            setLoading(false);
            sessionManager.saveLoginData(
                "offline_token_12345",
                10,
                "Amal Wijesinghe",
                "S0001",
                username.contains("@") ? username : "student1@royaledu.com",
                "+94 77 666 7777",
                ""
            );
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, 500);
    }

    private void setLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        btnLogin.setEnabled(!loading);
        etUsername.setEnabled(!loading);
        etPassword.setEnabled(!loading);
    }
}
