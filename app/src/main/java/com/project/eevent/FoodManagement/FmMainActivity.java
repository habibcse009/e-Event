package com.project.eevent.FoodManagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.project.eevent.Constant;
import com.project.eevent.ContactUsActivity;
import com.project.eevent.FoodManagement.Order.OrderListFMActivity;
import com.project.eevent.FoodManagement.Product.AddProductFMActivity;
import com.project.eevent.FoodManagement.Product.AllProductFMActivity;
import com.project.eevent.FoodManagement.Profile.ProfileFmActivity;
import com.project.eevent.LoginActivity;
import com.project.eevent.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import es.dmoral.toasty.Toasty;

public class FmMainActivity extends AppCompatActivity {
    CardView cardProfileFM, cardMyServices, cardCreateServices, cardContactUs, cardOrdersFM, cardLogoutFM, cardContractAdminFM;

    SharedPreferences sp;
    SharedPreferences.Editor editor;


    ShimmerTextView txtWelcomeNameFM, txtHelloFM;
    Shimmer shimmerFM;

    String UserCell;

    private ProgressDialog loading;


    //for double back press to exit
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fm_main);


        getSupportActionBar().setTitle("Food Management Panel");
        //Fetching mobile from shared preferences
        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String mobile = sp.getString(Constant.CELL_SHARED_PREF, "Not Available");
        UserCell = mobile;

        cardProfileFM = findViewById(R.id.card_profileFM);
        cardMyServices = findViewById(R.id.card_viewProductFM);
        cardCreateServices = findViewById(R.id.card_addServicesFM);
        cardOrdersFM = findViewById(R.id.card_myOrderFM);
        cardLogoutFM = findViewById(R.id.card_logoutFM);


        Typeface tf = Typeface.createFromAsset(getAssets(), "Milkshake.ttf");

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        cardProfileFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FmMainActivity.this, ProfileFmActivity.class);
                startActivity(intent);

            }
        });


        cardMyServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FmMainActivity.this, AllProductFMActivity.class);
                intent.putExtra("type", "FoodManagement");
                startActivity(intent);

            }
        });
        cardCreateServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FmMainActivity.this, AddProductFMActivity.class);
                intent.putExtra("type", "FoodManagement");
                startActivity(intent);

            }
        });


        cardOrdersFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FmMainActivity.this, OrderListFMActivity.class);
                startActivity(intent);

            }
        });


        cardLogoutFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.clear();
                editor.apply();
                // finishAffinity();
                Toasty.info(FmMainActivity.this, "Log out from Food Management panel!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FmMainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        //call function
        //  getData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.guide_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {


            case R.id.menu_guide:
                Intent intent = new Intent(FmMainActivity.this, ContactUsActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //double backpress to exit
    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {

            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //finish();
            finishAffinity();

        } else {
            Toasty.info(this, "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

}