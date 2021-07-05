package aut.bme.hu.shoppinglist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import aut.bme.hu.shoppinglist.R;
import aut.bme.hu.shoppinglist.data.ShoppingItem;

public class ShoppingAdapter
        extends RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder> {

    private final List<ShoppingItem> items;

    private ShoppingItemClickListener listener;

    public ShoppingAdapter(ShoppingItemClickListener listener) {
        this.listener = listener;
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_shopping_list, parent, false);
        return new ShoppingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingViewHolder holder, int position) {
        ShoppingItem item = items.get(position);
        holder.nameTextView.setText(item.name);
        holder.descriptionTextView.setText(item.description);
        holder.categoryTextView.setText(item.category.name());
        holder.priceTextView.setText(item.estimatedPrice + " Ft");
        holder.iconImageView.setImageResource(getImageResource(item.category));
        holder.isBoughtCheckBox.setChecked(item.isBought);

        holder.item = item;
    }

    private @DrawableRes
    int getImageResource(ShoppingItem.Category category) {
        @DrawableRes int ret;
        switch (category) {
            case BOOK:
                ret = R.drawable.open_book;
                break;
            case ELECTRONIC:
                ret = R.drawable.lightning;
                break;
            case FOOD:
                ret = R.drawable.groceries;
                break;
            default:
                ret = 0;
        }
        return ret;
    }

    public void addItem(ShoppingItem item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void removeItem(ShoppingItem item, RecyclerView recyclerView) {
        int loc = items.indexOf(item);
        items.remove(loc);
        recyclerView.removeViewAt(loc);
        notifyItemRemoved(loc);
        notifyItemRangeRemoved(loc, items.size());
    }

    public void removeAll(RecyclerView recyclerView){
        while (items.size()>0){
            removeItem(items.get(0), recyclerView);
        }
    }

    public ShoppingItem getItemByNumber(int i){
        return items.get(i);
    }

    public void update(List<ShoppingItem> shoppingItems) {
        items.clear();
        items.addAll(shoppingItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void modifyItem(ShoppingItem changedItem) {
        int loc = items.indexOf(changedItem);
        ShoppingItem item = items.get(loc);
        item.category = changedItem.category;
        item.description = changedItem.description;
        item.name = changedItem.name;
        item.estimatedPrice = changedItem.estimatedPrice;
        item.isBought = changedItem.isBought;
        notifyItemChanged(loc);
    }

    public interface ShoppingItemClickListener{
        void onItemChanged(ShoppingItem item);
        void onItemRemoved(ShoppingItem item);
        void onItemModified(ShoppingItem item);
    }

    class ShoppingViewHolder extends RecyclerView.ViewHolder {

        ImageView iconImageView;
        TextView nameTextView;
        TextView descriptionTextView;
        TextView categoryTextView;
        TextView priceTextView;
        CheckBox isBoughtCheckBox;
        ImageButton removeButton;
        ImageButton changeButton;

        ShoppingItem item;

        ShoppingViewHolder(View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.ShoppingItemIconImageView);
            nameTextView = itemView.findViewById(R.id.ShoppingItemNameTextView);
            descriptionTextView = itemView.findViewById(R.id.ShoppingItemDescriptionTextView);
            categoryTextView = itemView.findViewById(R.id.ShoppingItemCategoryTextView);
            priceTextView = itemView.findViewById(R.id.ShoppingItemPriceTextView);
            isBoughtCheckBox = itemView.findViewById(R.id.ShoppingItemIsBoughtCheckBox);
            removeButton = itemView.findViewById(R.id.ShoppingItemRemoveButton);
            changeButton = itemView.findViewById(R.id.ShoppingItemChangedButton);

            isBoughtCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
 