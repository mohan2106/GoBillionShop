package com.mohan.gobillionshop.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mohan.gobillionshop.R;
import com.mohan.gobillionshop.productDetails;

import java.util.ArrayList;

public class productAdapter extends RecyclerView.Adapter<productAdapter.ViewHolder> {
    private ArrayList<productClass> itemList;
    private Context context;

    public productAdapter(ArrayList<productClass> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_product, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        productClass p = itemList.get(position);
        Glide.with(context).load(p.getImage()).placeholder(R.drawable.demo).fitCenter().centerCrop().into(holder.img);
        holder.name.setText(p.getName());
        holder.desc.setText(p.getDescription());
        holder.price.setText("$" + String.valueOf(p.getPrice()));
        holder.rate.setRating(p.rating);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.gobillionproduct.com/"+String.valueOf(itemList.get(position).getId());
                Intent intent = new Intent(context, productDetails.class);
                intent.putExtra("id",String.valueOf(itemList.get(position).getId()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name,desc,price;
        RatingBar rate;
        public ViewHolder(View view){
            super(view);
            img = view.findViewById(R.id.productImage);
            name = view.findViewById(R.id.productName);
            desc = view.findViewById(R.id.productDescription);
            price = view.findViewById(R.id.productPrice);
            rate = view.findViewById(R.id.productRatingBar);
        }
    }

    public void updateList(ArrayList<productClass> list){
        itemList = list;
        notifyDataSetChanged();
    }
}
