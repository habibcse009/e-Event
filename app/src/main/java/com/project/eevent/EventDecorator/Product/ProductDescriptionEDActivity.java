package com.project.eevent.EventDecorator.Product;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.project.eevent.Constant;
import com.project.eevent.ConventionHall.ChMainActivity;
import com.project.eevent.R;

import java.util.HashMap;
import java.util.Map;

public class ProductDescriptionEDActivity extends AppCompatActivity {
    String name, price, image, description, ed_cell, id, category, quantity;
    ImageView imgProduct;
    TextView txtName, txtPrice, txtDescription, txtCategory, txtQuantity;
    Button txtOrder, txtViewReview, txtDeleteProduct, txtViewShop;
    ProgressDialog loading;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description_e_d);
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("Product Details");

        imgProduct = findViewById(R.id.img_productDesED_proDetails);
        txtName = findViewById(R.id.txt_product_nameED_proDetails);
        txtPrice = findViewById(R.id.txt_priceED_proDetails);
        txtCategory = findViewById(R.id.txt_categoryED_proDetails);
        txtQuantity = findViewById(R.id.txt_quantityED_proDetails);
        txtDescription = findViewById(R.id.txt_descriptionED_proDetails);
        txtOrder = findViewById(R.id.txt_orderED_proDetails);
        txtViewReview = findViewById(R.id.txt_view_reviewED_proDetails);
        txtViewReview.setVisibility(View.INVISIBLE);
        txtDeleteProduct = findViewById(R.id.txt_deleteProductED_proDetails);
        txtViewShop = findViewById(R.id.txt_viewAllProductsED_proDetails);
        txtViewShop.setVisibility(View.INVISIBLE);

        //Fetching cell from shared preferences
        sharedPreferences = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String account_type = sharedPreferences.getString(Constant.AC_TYPE_SHARED_PREF, "Not Available");

        if (account_type.equals("EventDecorator")) {
            txtOrder.setVisibility(View.GONE);
        }
        if (account_type.equals("Customer")) {
            txtDeleteProduct.setVisibility(View.GONE);
        }
        name = getIntent().getExtras().getString("name");
        price = getIntent().getExtras().getString("price");
        category = getIntent().getExtras().getString("category");
        quantity = getIntent().getExtras().getString("quantity");
        description = getIntent().getExtras().getString("description");
        image = getIntent().getExtras().getString("image");
        ed_cell = getIntent().getExtras().getString("ed_cell");
        id = getIntent().getExtras().getString("id");
        String url = Constant.MAIN_URL + "/product_image/" + image;

        txtName.setText(name);
        txtPrice.setText(Constant.KEY_CURRENCY + price + "/Package");
        txtDescription.setText(description);
        txtCategory.setText("Package Type : " + category);
        txtQuantity.setText("Package Quantity : " + quantity + " Days");

        Glide.with(ProductDescriptionEDActivity.this)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.not_found)
                .into(imgProduct);


        txtOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //egula comment hobe na
                Intent intent = new Intent(ProductDescriptionEDActivity.this, OrderEDActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("price", price);
                intent.putExtra("category", category);
                intent.putExtra("quantity", quantity);
                intent.putExtra("ed_cell", ed_cell);
                intent.putExtra("id", id);

                startActivity(intent);

            }
        });

        txtViewShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDescriptionEDActivity.this, AllProductEDActivity.class);

                intent.putExtra("getcell", ed_cell);

                startActivity(intent);

            }
        });


/*
        txtViewReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ProductDescriptionEDActivity.this, ViewReviewActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("ed_cell", ed_cell);
                startActivity(intent);


            }
        });
*/


        txtDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductDescriptionEDActivity.this);
                builder.setMessage("Want to delete this product ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                DeleteFromServer(id);//call method

                                dialog.cancel();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Perform Your Task Here--When No is pressed
                                dialog.cancel();
                            }
                        }).show();
            }
        });

    }

    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //Delete method for deleting contacts
    public void DeleteFromServer(final String id) {
        loading = new ProgressDialog(this);

        loading.setMessage("Delete item processing....");
        loading.show();

        String URL = Constant.DELETE_PRODUCT_ED_URL + "?product_id=" + id;


        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //for track response in logcat
                        Log.d("RESPONSE", response);
                        // Log.d("RESPONSE", userCell);


                        //If we are getting success from server
                        if (response.equals("success")) {

                            loading.dismiss();
                            //Starting profile activity

                            Intent intent = new Intent(ProductDescriptionEDActivity.this, ChMainActivity.class);
                            Toasty.success(ProductDescriptionEDActivity.this, " Successfully Deleted!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                        }


                        //If we are getting success from server
                        else if (response.equals("failure")) {

                            loading.dismiss();
                            //Starting profile activity


                            Toasty.error(ProductDescriptionEDActivity.this, " Delete fail!", Toast.LENGTH_SHORT).show();

                        } else {

                            loading.dismiss();
                            Toasty.error(ProductDescriptionEDActivity.this, "Network Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                        Toast.makeText(ProductDescriptionEDActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request

                params.put(Constant.KEYPDODUCT_ID, id);

                Log.d("ID", id);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(ProductDescriptionEDActivity.this);
        requestQueue.add(stringRequest);

    }
}
