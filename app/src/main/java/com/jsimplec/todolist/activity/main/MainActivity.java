package com.jsimplec.todolist.activity.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jsimplec.todolist.R;
import com.jsimplec.todolist.callback.SuccessErrorCallBack;
import com.jsimplec.todolist.httpclient.ItemsClient;
import com.jsimplec.todolist.model.ItemList;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ItemListAdapter listAdapter;
    private RecyclerView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        listAdapter = new ItemListAdapter();

        listView.setAdapter(listAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    private void loadData() {
        ItemsClient.ITEMS_CLIENT
                .loadData("token", new SuccessErrorCallBack<List<ItemList>>() {
                    @Override
                    public void onSuccess(List<ItemList> response) {
                        Log.i("Main", "load success");
                        runOnUiThread(() -> listAdapter.submitList(response));
                    }

                    @Override
                    public void onError(String errorMessage) {
                        runOnUiThread(() -> showError(errorMessage));
                    }
                });
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
