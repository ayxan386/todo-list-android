package com.jsimplec.todolist.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jsimplec.todolist.R;
import com.jsimplec.todolist.activity.auth.LoginActivity;
import com.jsimplec.todolist.callback.SuccessErrorCallBack;
import com.jsimplec.todolist.httpclient.ItemsClient;
import com.jsimplec.todolist.model.ItemList;
import com.jsimplec.todolist.util.constants.StaticConstants;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ItemListAdapter listAdapter;
    private RecyclerView listView;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences(StaticConstants.PREFERENCE_TODO_AUTH, Context.MODE_PRIVATE);

        if (!checkForToken()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        listAdapter = new ItemListAdapter();

        listView.setAdapter(listAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    private boolean checkForToken() {
        return preferences.contains("token");
    }

    private void loadData() {
        ItemsClient.ITEMS_CLIENT
                .loadData(getToken(), new SuccessErrorCallBack<List<ItemList>>() {
                    @Override
                    public void onSuccess(List<ItemList> response) {
                        runOnUiThread(() -> listAdapter.submitList(response));
                    }

                    @Override
                    public void onError(String errorMessage) {
                        runOnUiThread(() -> showError(errorMessage));
                    }
                });
    }

    private String getToken() {
        return preferences.getString("token", "123");
    }

    private void showError(String errorMessage) {
        View rootView = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(rootView, errorMessage, Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.purple_500));
        snackbar.setAction("Retry", v -> {
            loadData();
            snackbar.dismiss();
        });
        snackbar.show();
    }
}
