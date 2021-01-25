package com.project.eevent.Photographer.Product.adapter;

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
import com.project.eevent.Photographer.Product.ProductDescriptionPhotographerActivity;
import com.project.eevent.Photographer.Product.model.ProductPhotographer;
import com.project.eevent.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapterPhotographer extends RecyclerView.Adapter<ProductAdapterPhotographer.MyViewHolder> {

    private List<ProductPhotographer> productPhotographers;
    Context context;

    public ProductAdapterPhotographer(Context context, List<ProductPhotographer> contacts) {
        this.context = context;
        this.productPhotographers = contacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_photographer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(productPhotographers.get(position).getName());
        holder.category.setText(productPhotographers.get(position).getCategory());

        holder.price.setText(Constant.KEY_CURRENCY + productPhotographers.get(position).getPrice());

        String url = Constant.MAIN_URL + "/product_image/" + productPhotographers.get(position).getImage();

        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.not_found)
                .into(holder.img_product);


    }

    @Override
    public int getItemCount() {
        return productPhotographers.size();
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
            Intent i = new Intent(context, ProductDescriptionPhotographerActivity.class);

            i.putExtra("id", productPhotographers.get(getAdapterPosition()).getProductId());
            i.putExtra("name", productPhotographers.get(getAdapterPosition()).getName());
            i.putExtra("price", productPhotographers.get(getAdapterPosition()).getPrice());
            i.putExtra("image", productPhotographers.get(getAdapterPosition()).getImage());
            i.putExtra("category", productPhotographers.get(getAdapterPosition()).getCategory());
            i.putExtra("quantity", productPhotographers.get(getAdapterPosition()).getQuantity());
            i.putExtra("description", productPhotographers.get(getAdapterPosition()).getDescription());
            i.putExtra("photographer_cell", productPhotographers.get(getAdapterPosition()).getPhotographerCell());
            context.startActivity(i);
            Toast.makeText(context, productPhotographers.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();

            Log.d("ID", " id: " + productPhotographers.get(getAdapterPosition()).getProductId());
        }
    }
}