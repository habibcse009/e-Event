package com.project.eevent.FoodManagement.Product;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.project.eevent.Constant;
import com.project.eevent.FoodManagement.Product.adapter.ProductAdapterFM;
import com.project.eevent.FoodManagement.Product.model.ProductFM;
import com.project.eevent.R;
import com.project.eevent.remote.ApiClient;
import com.project.eevent.remote.ApiInterface;
import com.tt.whorlviewlibrary.WhorlView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllProductFMActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ProductAdapterFM productAdapter;
    private List<ProductFM> productList;
    private ApiInterface apiInterface;

    private ProgressBar progressBar;
    private WhorlView whorlView;
    SharedPreferences sharedPreferences;
    String cell, getType, getCell;
    ImageView imgNoProduct;

    int MAX_SIZE = 999;
    public String shopCell[] = new String[MAX_SIZE];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product_f_m);
        getSupportActionBar().setTitle("All Packages");

        whorlView = (WhorlView) this.findViewById(R.id.whorl2FM_allProduct);
        whorlView.start();
        // progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerFM_allProduct);
        imgNoProduct = findViewById(R.id.image_no_product);

        getCell = getIntent().getExtras().getString("getcell");

        getType = getIntent().getExtras().getString("type");

        //Fetching cell from shared preferences
        sharedPreferences = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        cell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");


        // set a GridLayoutManager with default vertical orientation and 3 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView


//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        fetchData("product", "", cell);

    }


    public void fetchData(String type, String key, String cell) {
        Call<List<ProductFM>> call;
        if (getType.equals("FoodManagement")) {

            call = apiInterface.getProductFM(type, key, cell);
        } else {
            call = apiInterface.getAllProductFM(type, key, cell);
        }
        call.enqueue(new Callback<List<ProductFM>>() {
            @Override
            public void onResponse(Call<List<ProductFM>> call, Response<List<ProductFM>> response) {
                // progressBar.setVisibility(View.INVISIBLE);
                whorlView.setVisibility(View.INVISIBLE);

                productList = response.body();
                Log.d("response", productList.toString());


                if (productList.size() == 0) {
                    imgNoProduct.setVisibility(View.VISIBLE);
                    imgNoProduct.setImageResource(R.drawable.no_product);
                    productAdapter = new ProductAdapterFM(AllProductFMActivity.this, productList);

                    recyclerView.setAdapter(productAdapter);

                    productAdapter.notifyDataSetChanged();//for search
                } else {

                    imgNoProduct.setVisibility(View.GONE);

                    productAdapter = new ProductAdapterFM(AllProductFMActivity.this, productList);

                    recyclerView.setAdapter(productAdapter);

                    productAdapter.notifyDataSetChanged();//for search
                }


            }

            @Override
            public void onFailure(Call<List<ProductFM>> call, Throwable t) {
                //  progressBar.setVisibility(View.INVISIBLE);
                whorlView.setVisibility(View.INVISIBLE);
                Toast.makeText(AllProductFMActivity.this, "Error : " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        //for hide actionbar item
//        MenuItem itemAdd = (MenuItem) menu.findItem(R.id.add);
//        itemAdd.setVisible(false);
//


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchData("product", query, cell);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchData("product", newText, cell);
                return false;
            }
        });
        return true;
    }


    //menu item selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //when activity is resumed this method is called
    @Override
    protected void onResume() {
        super.onResume();
        fetchData("product", "", cell);
    }

}
