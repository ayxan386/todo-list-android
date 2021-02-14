package com.jsimplec.todolist.activity.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.jsimplec.todolist.R;
import com.jsimplec.todolist.callback.StartActivityCallBack;
import com.jsimplec.todolist.callback.SuccessErrorCallBack;
import com.jsimplec.todolist.httpclient.ItemsClient;
import com.jsimplec.todolist.model.Item;
import com.jsimplec.todolist.model.ItemList;

public class ItemListAdapter extends ListAdapter<ItemList, ItemListAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<ItemList> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ItemList>() {
                @Override
                public boolean areItemsTheSame(@NonNull ItemList oldData,
                                               @NonNull ItemList newData) {
                    return oldData.equals(newData);
                }

                @Override
                public boolean areContentsTheSame(@NonNull ItemList oldData,
                                                  @NonNull ItemList newData) {
                    return oldData.equals(newData);
                }
            };

    private StartActivityCallBack callBack;
    private SuccessErrorCallBack<Item> deleteItemCallBack;

    public ItemListAdapter(StartActivityCallBack callBack) {
        super(DIFF_CALLBACK);
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_layout, parent, false);
        return new ViewHolder(view, callBack);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemList itemList = getItem(position);
        holder.bind(itemList, position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final RecyclerView content;
        private final ItemAdapter itemAdapter;
        private final TextInputLayout itemTitle;
        private int position;
        private final MaterialButton addItemButton;
        private ItemList data;

        public ViewHolder(@NonNull View itemView, StartActivityCallBack startActivityCallBack) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            itemTitle = itemView.findViewById(R.id.itemListItemTitle);
            addItemButton = itemView.findViewById(R.id.itemListSave);
            content = itemView.findViewById(R.id.content);

            itemAdapter = new ItemAdapter(startActivityCallBack, deleteItemCallBack);
            content.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            content.setAdapter(itemAdapter);

        }

        private void saveNewItem() {
            MainActivity.mainActivity.setProgressBar(true);
            ItemsClient.ITEMS_CLIENT.saveItem(new Item(null,
                    itemTitle.getEditText().getText().toString(),
                    null,
                    "IN_PROGRESS",
                    null,
                    data.getId()), new SuccessErrorCallBack<Item>() {
                @Override
                public void onSuccess(Item response) {
                    MainActivity.mainActivity.setProgressBar(false);
                    bind(data.addItem(response), position);
                }

                @Override
                public void onError(String errorMessage) {
                    MainActivity.mainActivity.setProgressBar(false);
                    MainActivity.mainActivity.showMainError(errorMessage);
                }
            });
        }

        public void bind(ItemList itemList, int position) {
            data = itemList;
            this.position = position;
            if (data != null) {
                addItemButton.setOnClickListener(v -> saveNewItem());
            }
            title.setText(itemList.getName());
            itemAdapter.submitList(itemList.getItems());

            deleteItemCallBack = new SuccessErrorCallBack<Item>() {
                @Override
                public void onSuccess(Item response) {
                    data = itemList.deleteItem(response);
                    MainActivity.mainActivity.removeListItem(data, position);
                    MainActivity.mainActivity.addListItem(data);
                    MainActivity.mainActivity.renderList();
                }

                @Override
                public void onError(String errorMessage) {
                    MainActivity.mainActivity.showMainError(errorMessage);
                }
            };
        }
    }
}
