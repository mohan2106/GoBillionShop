package com.mohan.gobillionshop.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mohan.gobillionshop.R;
import com.mohan.gobillionshop.productDetails;

import java.util.ArrayList;

public class wishlistAdapter extends RecyclerView.Adapter<wishlistAdapter.ViewHolder> {

    private ArrayList<wishlistItem> itemList;
    private Context context;

    public wishlistAdapter(ArrayList<wishlistItem> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.wishlist_single_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final wishlistItem p = itemList.get(position);
        Glide.with(context).load("https://images.unsplash.com/photo-1534534665817-8493579d3fde?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=578&q=80").placeholder(R.drawable.demo).fitCenter().centerCrop().into(holder.img);
        holder.name.setText(p.getName());
        holder.desc.setText(p.getDescription());
        holder.price.setText("$" + String.valueOf(p.getPrice()));
        holder.rate.setRating(p.rating);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, productDetails.class);
                context.startActivity(intent);
            }
        });
        final int q = p.getQualtity();
        holder.quantity.setText(String.valueOf(q));
        holder.inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int av = itemList.get(position).getQualtity();
                itemList.get(position).setQualtity(av+1);
                holder.quantity.setText(String.valueOf(av+1));
            }
        });
        holder.dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int av = itemList.get(position).getQualtity();
                if(av<=0){
                    Toast.makeText(context, "You don't have any item left", Toast.LENGTH_SHORT).show();
                }else{
                    itemList.get(position).setQualtity(av-1);
                    holder.quantity.setText(String.valueOf(av-1));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name,desc,price,quantity;
        RatingBar rate;
        ImageButton inc,dec;

        public ViewHolder(View view){
            super(view);
            img = view.findViewById(R.id.productImage);
            name = view.findViewById(R.id.productName);
            desc = view.findViewById(R.id.productDescription);
            price = view.findViewById(R.id.productPrice);
            rate = view.findViewById(R.id.productRatingBar);
            quantity = view.findViewById(R.id.itemCount);
            inc = view.findViewById(R.id.btn_inc);
            dec = view.findViewById(R.id.btn_dec);
        }
    }
}
