package com.jsimplec.todolist.activity.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.jsimplec.todolist.R;
import com.jsimplec.todolist.callback.ErrorCallBack;
import com.jsimplec.todolist.httpclient.ItemsClient;
import com.jsimplec.todolist.model.Item;

import org.jetbrains.annotations.NotNull;

public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item_detail);
        Item item = getIntent().getExtras().getParcelable("item");

        TextView title = findViewById(R.id.detailTitle);
        TextInputLayout content = findViewById(R.id.detailContent);
        TextView date = findViewById(R.id.updateDate);

        title.setText(item.getTitle());
        content.getEditText().setText(item.getContent());
        date.setText(item.getUpdateDate());

        Button submitButton = findViewById(R.id.detailSubmit);
        submitButton.setOnClickListener(updateItemRequest(item, content));

    }

    @NotNull
    private View.OnClickListener updateItemRequest(Item item, TextInputLayout content) {
        return v -> {
            Item updatedItem = item.buildItem(content.getEditText().getText().toString(), item.getStatus());
            ItemsClient.ITEMS_CLIENT.updateItem(updatedItem, (ErrorCallBack) errorMessage -> {
                runOnUiThread(() -> displayErrorSnackBar(errorMessage));
            });
        };
    }

    private void displayErrorSnackBar(String errorMessage) {
        View rootView = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(rootView, errorMessage, Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.purple_500));
        snackbar.setAction("Close", v -> {
            snackbar.dismiss();
        });
        snackbar.show();
    }
}