package com.project.eevent.FoodManagement.Product.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.project.eevent.Constant;
import com.project.eevent.ConventionHall.Product.ProductDescriptionCHActivity;
import com.project.eevent.FoodManagement.Product.ProductDescriptionFMActivity;
import com.project.eevent.FoodManagement.Product.model.ProductFM;
import com.project.eevent.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapterFM extends RecyclerView.Adapter<ProductAdapterFM.MyViewHolder> {

    private List<ProductFM> productFM;
    Context context;

    public ProductAdapterFM(Context context, List<ProductFM> contacts) {
        this.context = context;
        this.productFM = contacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_photographer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(productFM.get(position).getName());
        holder.category.setText(productFM.get(position).getCategory());

        holder.price.setText(Constant.KEY_CURRENCY + productFM.get(position).getPrice());

        String url = Constant.MAIN_URL + "/product_image/" + productFM.get(position).getImage();

        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.not_found)
                .into(holder.img_product);


    }

    @Override
    public int getItemCount() {
        return productFM.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, price, category;
        ImageView img_product;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_namePhotographer);
            price = itemView.findViewById(R.id.txt_pricePhotographer);
            category = itemView.findViewById(R.id.txt_sizePhotographer);
            img_product = itemView.findViewById(R.id.img_productPhotographer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, ProductDescriptionFMActivity.class);

            i.putExtra("id", productFM.get(getAdapterPosition()).getProductId());
            i.putExtra("name", productFM.get(getAdapterPosition()).getName());
            i.putExtra("price", productFM.get(getAdapterPosition()).getPrice());
            i.putExtra("image", productFM.get(getAdapterPosition()).getImage());
            i.putExtra("category", productFM.get(getAdapterPosition()).getCategory());
            i.putExtra("quantity", productFM.get(getAdapterPosition()).getQuantity());
            i.putExtra("description", productFM.get(getAdapterPosition()).getDescription());
            i.putExtra("fm_cell", productFM.get(getAdapterPosition()).getFMCell());
            context.startActivity(i);
            Toast.makeText(context, productFM.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();

            Log.d("ID", " id: " + productFM.get(getAdapterPosition()).getProductId());
        }
    }
}