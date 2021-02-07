package com.jsimplec.todolist.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.jsimplec.todolist.R;
import com.jsimplec.todolist.callback.SuccessErrorCallBack;
import com.jsimplec.todolist.httpclient.ItemsClient;
import com.jsimplec.todolist.model.ItemList;

public class ItemListForm extends AppCompatActivity {

    private TextInputLayout titleLayout;
    private MaterialButton saveButton;
    private ProgressBar progressBar;
    public static ItemListForm itemListForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_form);

        itemListForm = this;

        titleLayout = findViewById(R.id.itemListFormListTitleInput);
        saveButton = findViewById(R.id.itemListFormSaveButton);
        progressBar = findViewById(R.id.itemListFormProgressBar);

        saveButton.setOnClickListener(v -> addItemList());
    }

    private void addItemList() {
        this.setSpinnerVisibility(true);
        String title = titleLayout.getEditText().getText().toString();
        Log.i("item list form", title);
        ItemsClient.ITEMS_CLIENT
                .saveList(title, new SuccessErrorCallBack<ItemList>() {
                    @Override
                    public void onSuccess(ItemList response) {
                        Log.i("itemlist form", response.toString());
                        setSpinnerVisibility(false);
                        finishAffinity();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.i("itemlist form", errorMessage);
                        showErrorMessage(errorMessage);
                        setSpinnerVisibility(false);
                    }
                });
    }

    private void setSpinnerVisibility(boolean state) {
        runOnUiThread(() -> {
            if (state) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showErrorMessage(String message) {
        View rootView = findViewById(android.R.id.content);
        runOnUiThread(() -> {
            Snackbar snackbar = Snackbar.make(rootView, message, BaseTransientBottomBar.LENGTH_INDEFINITE);
            snackbar.setActionTextColor(getResources().getColor(R.color.purple_500));
            snackbar.setActionTextColor(getResources().getColor(R.color.white));
            snackbar.setAction("Dismiss", v -> snackbar.dismiss());
            snackbar.show();
        });
    }
}