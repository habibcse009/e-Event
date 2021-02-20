package com.project.eevent.EventDecorator.Product.adapter;

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
import com.project.eevent.EventDecorator.Product.ProductDescriptionEDActivity;
import com.project.eevent.EventDecorator.Product.model.ProductED;
import com.project.eevent.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapterED extends RecyclerView.Adapter<ProductAdapterED.MyViewHolder> {

    private List<ProductED> productED;
    Context context;

    public ProductAdapterED(Context context, List<ProductED> contacts) {
        this.context = context;
        this.productED = contacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_photographer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(productED.get(position).getName());
        holder.category.setText(productED.get(position).getCategory());

        holder.price.setText(Constant.KEY_CURRENCY + productED.get(position).getPrice());

        String url = Constant.MAIN_URL + "/product_image/" + productED.get(position).getImage();

        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.not_found)
                .into(holder.img_product);


    }

    @Override
    public int getItemCount() {
        return productED.size();
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
            Intent i = new Intent(context, ProductDescriptionEDActivity.class);

            i.putExtra("id", productED.get(getAdapterPosition()).getProductId());
            i.putExtra("name", productED.get(getAdapterPosition()).getName());
            i.putExtra("price", productED.get(getAdapterPosition()).getPrice());
            i.putExtra("image", productED.get(getAdapterPosition()).getImage());
            i.putExtra("category", productED.get(getAdapterPosition()).getCategory());
            i.putExtra("quantity", productED.get(getAdapterPosition()).getQuantity());
            i.putExtra("description", productED.get(getAdapterPosition()).getDescription());
            i.putExtra("ed_cell", productED.get(getAdapterPosition()).getEDCell());
            context.startActivity(i);
            Toast.makeText(context, productED.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();

            Log.d("ID", " id: " + productED.get(getAdapterPosition()).getProductId());
        }
    }
}