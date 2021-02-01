package com.jsimplec.todolist.activity.auth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.jsimplec.todolist.R;
import com.jsimplec.todolist.activity.main.MainActivity;
import com.jsimplec.todolist.callback.SuccessErrorCallBack;
import com.jsimplec.todolist.util.constants.StaticConstants;
import com.jsimplec.todolist.model.TokenResponseDTO;

import static com.jsimplec.todolist.httpclient.AuthClient.AUTH_CLIENT;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFERENCE_NAME = StaticConstants.PREFERENCE_TODO_AUTH;
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
        AUTH_CLIENT.login(username, password, new SuccessErrorCallBack<TokenResponseDTO>() {
            @Override
            public void onSuccess(TokenResponseDTO response) {
                saveToStore("token", response.getToken());
                saveToStore("username", username);
                runOnUiThread(() -> directToMainPage());
            }

            @Override
            public void onError(String errorMessage) {
                runOnUiThread(() -> showErrorDialog(errorMessage));
            }
        });
    }

    private void showErrorDialog(String errorMessage) {
        new AlertDialog.Builder(this)
                .setMessage(errorMessage)
                .show();
    }

    private void directToMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void saveToStore(String key, String value) {
        SharedPreferences preferences = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).apply();
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