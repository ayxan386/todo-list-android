package com.jsimplec.todolist.activity.main;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jsimplec.todolist.R;
import com.jsimplec.todolist.model.Item;

public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item_detail);
        Item item = getIntent().getExtras().getParcelable("item");
        Log.i("Item detail", item.toString());

        TextView title = findViewById(R.id.detailTitle);
        TextView content = findViewById(R.id.detailContent);
        TextView date = findViewById(R.id.updateDate);

        title.setText(item.getTitle());
        content.setText(item.getContent());
        date.setText(item.getUpdateDate());

    }
}