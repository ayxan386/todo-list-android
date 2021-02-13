package com.jsimplec.todolist.activity.main;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.jsimplec.todolist.R;
import com.jsimplec.todolist.callback.ErrorCallBack;
import com.jsimplec.todolist.callback.StartActivityCallBack;
import com.jsimplec.todolist.callback.SuccessErrorCallBack;
import com.jsimplec.todolist.httpclient.ItemsClient;
import com.jsimplec.todolist.model.Item;

import org.jetbrains.annotations.NotNull;

public class ItemAdapter extends ListAdapter<Item, ItemAdapter.ViewHolder> {

    private final StartActivityCallBack<Item> callBack;
    private SuccessErrorCallBack<Item> deleteItemCallBack;

    protected ItemAdapter(StartActivityCallBack<Item> callBack, SuccessErrorCallBack<Item> deleteItemCallBack) {
        super(DIFF_CALLBACK);
        this.callBack = callBack;
        this.deleteItemCallBack = deleteItemCallBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view, callBack);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final MaterialCheckBox status;
        private final StartActivityCallBack<Item> callBack;
        private final Button deleteButton;
        private Item item;
        private boolean hasBinded;

        public ViewHolder(@NonNull View itemView, StartActivityCallBack<Item> callBack) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            status = itemView.findViewById(R.id.itemCheckBox);
            deleteButton = itemView.findViewById(R.id.itemDeleteButton);

            this.callBack = callBack;
        }

        @NotNull
        private CompoundButton.OnCheckedChangeListener updateStatus() {
            ErrorCallBack callback = errorMessage -> Log.e("ItemAdapter http call", errorMessage);
            return (buttonView, isChecked) ->
                    ItemsClient.ITEMS_CLIENT
                            .updateItem(
                                    item.buildItem(item.getContent(), Item.getStatusFromBool(isChecked)),
                                    callback);
        }

        public void bind(Item item) {
            this.item = item;
            if (!hasBinded) {
                status.setOnCheckedChangeListener(updateStatus());
                title.setOnClickListener(view -> callBack.startActivity(ItemDetailActivity.class, item));

                deleteButton.setOnClickListener(v -> ItemsClient.ITEMS_CLIENT.deleteItem(item, deleteItemCallBack));

                hasBinded = true;
            }
            title.setText(item.getTitle());
            status.setChecked(Item.getActiveStatus(item.getStatus()));
        }
    }

    private static final DiffUtil.ItemCallback<Item> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Item>() {
                @Override
                public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
                    return false;
                }

                @Override
                public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
                    return false;
                }
            };
}
