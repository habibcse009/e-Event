package com.project.eevent.Customer.Orders;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.project.eevent.Constant;
import com.project.eevent.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainOrderListActivity extends AppCompatActivity {
    CardView cardCHCus, cardPhotographerCus, cardEDCus, cardFMCus;

    SharedPreferences sp;
    SharedPreferences.Editor editor;


    ShimmerTextView txtWelcomeNameCus, txtHelloCus;
    Shimmer shimmerCus;

    String UserCell;

    private ProgressDialog loading;


    //for double back press to exit
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_order_list);
        getSupportActionBar().setTitle("Customer Orders Panel");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button

        //Fetching mobile from shared preferences
        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String mobile = sp.getString(Constant.CELL_SHARED_PREF, "Not Available");
        UserCell = mobile;

        cardCHCus = findViewById(R.id.card_viewOrder_CH);
        cardPhotographerCus = findViewById(R.id.card_viewOrder_Photographer);
        cardEDCus = findViewById(R.id.card_viewOrder_ED);
        cardFMCus = findViewById(R.id.card_viewOrder_FM);


        Typeface tf = Typeface.createFromAsset(getAssets(), "Milkshake.ttf");

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();


        cardCHCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainOrderListActivity.this, CusOrderListChActivity.class);
                startActivity(intent);

            }
        });
        cardPhotographerCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainOrderListActivity.this, CusOrderListPhotographerActivity.class);
                startActivity(intent);

            }
        });
        cardEDCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainOrderListActivity.this, CusOrderListEdActivity.class);
                intent.putExtra("type", "Customer");
                startActivity(intent);

            }
        });
        cardFMCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainOrderListActivity.this, CusOrderListFmActivity.class);
                intent.putExtra("type", "Customer");
                startActivity(intent);

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