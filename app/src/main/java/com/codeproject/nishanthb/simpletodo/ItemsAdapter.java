package com.codeproject.nishanthb.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;
    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Use layout inflator to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
        // Wrap this inside a view Holder and return it.
        return new ViewHolder(todoView);
    }

    //responsible to bind data to a specific view Holder.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Grab the item at position.
        String item = items.get(position);
        //Bind the item to the specified view holder
        holder.bind(item);
    }
    // Tells recycler how many items are there in the list.
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Container to provide easy access to views that represent each row of the list.
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvitem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvitem = itemView.findViewById(android.R.id.text1);
        }
        // Update the view inside the Viewholder with the data
        public void bind(String item) {
            tvitem.setText(item);
            tvitem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Remove from the recycler View
//                    Notify the adapter which position in the recyler was long pressed.
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
