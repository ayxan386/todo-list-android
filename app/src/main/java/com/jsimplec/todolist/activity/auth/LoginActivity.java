package com.jsimplec.todolist.activity.auth;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.jsimplec.todolist.R;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private TextInputLayout passwordLayout;
    private TextInputLayout usernameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        usernameLayout = findViewById(R.id.usernameInputLayout);
        passwordLayout = findViewById(R.id.passwordInputLayout);

        loginButton.setOnClickListener((v) -> handleLoginButtonClicked());
        usernameLayout.getEditText().addTextChangedListener(clearErrors(usernameLayout));
        passwordLayout.getEditText().addTextChangedListener(clearErrors(passwordLayout));
    }

    private void handleLoginButtonClicked() {
        String username = usernameLayout.getEditText().getText().toString();
        String password = passwordLayout.getEditText().getText().toString();

        if (username.isEmpty()) {
            usernameLayout.setError("Username cannot be empty");
        } else if (password.isEmpty()) {
            passwordLayout.setError("Password cannot be empty");
        } else {
            login(username, password);
        }
    }

    private void login(String username, String password) {
        Log.i("Login", String.format("logging user %s %s\n", username, password));
    }

    private TextWatcher clearErrors(TextInputLayout textInputLayout) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }
}