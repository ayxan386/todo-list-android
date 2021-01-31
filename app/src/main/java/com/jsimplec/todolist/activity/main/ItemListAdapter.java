package com.jsimplec.todolist.activity.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.jsimplec.todolist.R;
import com.jsimplec.todolist.model.ItemList;

import java.util.List;

public class ItemListAdapter extends ListAdapter<ItemList, ItemListAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemList itemList = getItem(position);
        holder.title.setText(itemList.getName());
        holder.content.setText(itemList.getItems().toString());
        holder.date.setText(itemList.getUpdateDate());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView content;
        private final TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            date = itemView.findViewById(R.id.date);
        }
    }

    public ItemListAdapter() {
        super(DIFF_CALLBACK);
    }

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
}
