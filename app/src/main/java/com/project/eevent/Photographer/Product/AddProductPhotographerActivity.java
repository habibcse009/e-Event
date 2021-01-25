package com.project.eevent.Photographer.Product;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.eevent.Constant;
import com.project.eevent.Photographer.Product.model.ProductUploadPhotographer;
import com.project.eevent.R;
import com.project.eevent.remote.ApiClient;
import com.project.eevent.remote.ApiInterface;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductPhotographerActivity extends AppCompatActivity {
    EditText etxtProductNamePhotographer, etxtQuantityPhotographer, etxtCategoryPhotographer, etxtPricePhotographerPhotographer, etxtDescriptionPhotographer;
    Button txtChooseImage, txtSubmit;
    ImageView imgProduct;
    String mediaPath, product_name, product_quantity, product_category, product_description, product_price, PhotographerCell;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_photographer);
        getSupportActionBar().setTitle("Add Product");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button

        etxtProductNamePhotographer = findViewById(R.id.etxt_Photographer_product_name);
        etxtCategoryPhotographer = findViewById(R.id.etxt_Photographer_categoryProduct);
        etxtDescriptionPhotographer = findViewById(R.id.etxt_Photographer_descriptionProduct);
        etxtPricePhotographerPhotographer = findViewById(R.id.etxt_Photographer_priceProduct);
        imgProduct = findViewById(R.id.image_Addproduct);
        etxtQuantityPhotographer = findViewById(R.id.etxt_Photographer_quantityProduct);


        txtChooseImage = findViewById(R.id.btn_Photographer_imageAddProduct);
        txtSubmit = findViewById(R.id.txt_Photographer_submitAddProduct);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        //Fetching cell from shared preferences
        sharedPreferences = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        PhotographerCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");


        //For choosing account type and open alert dialog
        etxtCategoryPhotographer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] categoryList = {"Stock Photography", "Portrait Photography", "Wedding Photography", "Aerial Photography", "Real Estate Photo Service", "Others"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AddProductPhotographerActivity.this);
                builder.setTitle("SELECT CATEGORY OR TYPE");
                //builder.setIcon(R.drawable.ic_gender);


                builder.setCancelable(false);
                builder.setItems(categoryList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                etxtCategoryPhotographer.setText(categoryList[position]);
                                break;

                            case 1:
                                etxtCategoryPhotographer.setText(categoryList[position]);
                                break;

                            case 2:
                                etxtCategoryPhotographer.setText(categoryList[position]);
                                break;
                            case 3:
                                etxtCategoryPhotographer.setText(categoryList[position]);
                                break;
                            case 4:
                                etxtCategoryPhotographer.setText(categoryList[position]);
                                break;
                            case 5:
                                etxtCategoryPhotographer.setText(categoryList[position]);
                                break;


                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        dialog.dismiss();
                    }
                });


                AlertDialog categoryDialog = builder.create();

                categoryDialog.show();
            }

        });

        txtChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddProductPhotographerActivity.this, ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
                startActivityForResult(intent, 1213);
            }
        });


        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                product_name = etxtProductNamePhotographer.getText().toString().trim();
                product_category = etxtCategoryPhotographer.getText().toString().trim();
                product_description = etxtDescriptionPhotographer.getText().toString().trim();
                product_price = etxtPricePhotographerPhotographer.getText().toString().trim();
                product_quantity = etxtQuantityPhotographer.getText().toString();

                if (product_name.isEmpty()) {
                    etxtProductNamePhotographer.setError("Product name can't empty!");
                    etxtProductNamePhotographer.requestFocus();
                } else if (product_category.isEmpty()) {
                    etxtCategoryPhotographer.setError("Product category can't empty!");
                    etxtCategoryPhotographer.requestFocus();
                } else if (product_quantity.isEmpty()) {
                    etxtQuantityPhotographer.setError("Input product quantity per package");
                    etxtQuantityPhotographer.requestFocus();
                } else if (product_price.isEmpty()) {
                    etxtPricePhotographerPhotographer.setError("Product price can't empty!");
                    etxtPricePhotographerPhotographer.requestFocus();
                } else if (product_description.isEmpty()) {
                    etxtDescriptionPhotographer.setError("Product description can't empty!");
                    etxtDescriptionPhotographer.requestFocus();
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddProductPhotographerActivity.this);
                    builder.setMessage("Want to Add Product ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                    // Perform Your Task Here--When Yes Is Pressed.
                                    //call method
                                    uploadFile(product_name, product_category, product_price, product_description);
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


            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            // When an Image is picked
            if (requestCode == 1213 && resultCode == RESULT_OK && null != data) {


                mediaPath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
                Bitmap selectedImage = BitmapFactory.decodeFile(mediaPath);
                imgProduct.setImageBitmap(selectedImage);


            }


        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }


    // Uploading Image/Video
    private void uploadFile(String name, String category, String price, String description) {
        progressDialog.show();

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        RequestBody p_name = RequestBody.create(MediaType.parse("text/plain"), product_name);
        RequestBody p_category = RequestBody.create(MediaType.parse("text/plain"), product_category);
        RequestBody p_quantity = RequestBody.create(MediaType.parse("text/plain"), product_quantity);
        RequestBody p_price = RequestBody.create(MediaType.parse("text/plain"), product_price);
        RequestBody p_description = RequestBody.create(MediaType.parse("text/plain"), product_description);
        RequestBody photographer_cell = RequestBody.create(MediaType.parse("text/plain"), PhotographerCell);


        ApiInterface getResponse = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ProductUploadPhotographer> call = getResponse.uploadFile(fileToUpload, filename, p_name, p_category, p_quantity, p_price, p_description, photographer_cell);
        call.enqueue(new Callback<ProductUploadPhotographer>() {
            @Override
            public void onResponse(Call<ProductUploadPhotographer> call, Response<ProductUploadPhotographer> response) {
                ProductUploadPhotographer serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toasty.success(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddProductPhotographerActivity.this, AllProductPhotographerActivity.class));

                    } else {
                        Toasty.error(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ProductUploadPhotographer> call, Throwable t) {

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
}
