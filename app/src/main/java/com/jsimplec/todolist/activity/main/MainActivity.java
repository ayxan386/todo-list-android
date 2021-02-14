package com.jsimplec.todolist.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jsimplec.todolist.R;
import com.jsimplec.todolist.activity.ItemListForm;
import com.jsimplec.todolist.activity.auth.LoginActivity;
import com.jsimplec.todolist.callback.SuccessErrorCallBack;
import com.jsimplec.todolist.httpclient.ItemsClient;
import com.jsimplec.todolist.model.ItemList;
import com.jsimplec.todolist.util.constants.StaticConstants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ItemListAdapter listAdapter;
    private RecyclerView listView;
    private SharedPreferences preferences;
    public static Context applicationContext;
    public static MainActivity mainActivity;
    private ProgressBar progressBar;
    private View addButton;
    private List<ItemList> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;

        applicationContext = getApplicationContext();
        preferences = getSharedPreferences(StaticConstants.PREFERENCE_TODO_AUTH, Context.MODE_PRIVATE);

        if (!checkForToken()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        listAdapter = new ItemListAdapter((activity, data) -> {
            Intent intent = new Intent(MainActivity.this, activity);
            intent.putExtra("item", data);
            MainActivity.this.startActivity(intent);
        });

        listView.setAdapter(listAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = findViewById(R.id.mainLoaderBar);

        addButton = findViewById(R.id.mainAddList);
        addButton.setOnClickListener(v -> openAddListView());

        loadData();
    }

    private void openAddListView() {
        Intent intent = new Intent(this, ItemListForm.class);
        startActivity(intent);
    }

    private boolean checkForToken() {
        return preferences.contains("token");
    }

    public void loadData() {
        setProgressBar(true);
        ItemsClient.ITEMS_CLIENT
                .loadData(getToken(), new SuccessErrorCallBack<List<ItemList>>() {

                    @Override
                    public void onSuccess(List<ItemList> response) {
                        setProgressBar(false);
                        data = response;
                        renderList();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        showError(errorMessage);
                        runOnUiThread(() -> {
                            setProgressBar(false);
                        });
                    }
                });
    }

    public void renderList() {
        runOnUiThread(() -> {
            listAdapter.submitList(data);
            listAdapter.notifyDataSetChanged();
        });
    }

    private String getToken() {
        return preferences.getString("token", "123");
    }

    public void showMainError(String errorMessage) {
        runOnUiThread(() -> showError(errorMessage));
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

    public void setProgressBar(boolean isVisible) {
        runOnUiThread(() -> {
            if (isVisible) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void addListItem(ItemList itemList) {
        ArrayList<ItemList> newData = new ArrayList<>(data);
        newData.add(itemList);
        data = newData;
    }

    public void removeListItem(ItemList itemList, int position) {
        ArrayList<ItemList> newData = new ArrayList<>(data);
        newData.remove(itemList);
        data = newData;
    }
}
