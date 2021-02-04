package com.project.eevent.Photographer;

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
import com.project.eevent.LoginActivity;
import com.project.eevent.Photographer.Order.OrderListPhotographerActivity;
import com.project.eevent.Photographer.Product.AddProductPhotographerActivity;
import com.project.eevent.Photographer.Product.AllProductPhotographerActivity;
import com.project.eevent.Photographer.Profile.ProfilePhotographerActivity;
import com.project.eevent.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import es.dmoral.toasty.Toasty;

public class PhotographerMainActivity extends AppCompatActivity {
    CardView cardProfilePhgrpr, cardMyServices, cardCreateServices, cardContactUs, cardOrdersPhgrpr, cardLogoutPhgrpr, cardContractAdminPhgrpr;

    SharedPreferences sp;
    SharedPreferences.Editor editor;


    ShimmerTextView txtWelcomeNamePhgrpr, txtHelloPhgrpr;
    Shimmer shimmerPhgrpr;

    String UserCell;

    private ProgressDialog loading;


    //for double back press to exit
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer_main);

        getSupportActionBar().setTitle("Photographer Panel");
        //Fetching mobile from shared preferences
        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String mobile = sp.getString(Constant.CELL_SHARED_PREF, "Not Available");
        UserCell = mobile;

        cardProfilePhgrpr = findViewById(R.id.card_profilePhgrpr);
        cardMyServices = findViewById(R.id.card_viewProductPhgrpr);
        cardCreateServices = findViewById(R.id.card_addServicesPhgrpr);
        cardOrdersPhgrpr = findViewById(R.id.card_myOrderPhgrpr);
        cardLogoutPhgrpr = findViewById(R.id.card_logoutPhgrpr);


        Typeface tf = Typeface.createFromAsset(getAssets(), "Milkshake.ttf");

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        cardProfilePhgrpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotographerMainActivity.this, ProfilePhotographerActivity.class);
                startActivity(intent);

            }
        });


        cardMyServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotographerMainActivity.this, AllProductPhotographerActivity.class);
                intent.putExtra("type", "Photographer");
                startActivity(intent);

            }
        });
        cardCreateServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotographerMainActivity.this, AddProductPhotographerActivity.class);
                intent.putExtra("type", "Photographer");
                startActivity(intent);

            }
        });


        cardOrdersPhgrpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        //        Intent intent = new Intent(PhotographerMainActivity.this, OrderListPhotographerActivity.class);
          //      startActivity(intent);

            }
        });


        cardLogoutPhgrpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.clear();
                editor.apply();
                // finishAffinity();
                Toasty.info(PhotographerMainActivity.this, "Log out from Photographer panel!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PhotographerMainActivity.this, LoginActivity.class);
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
                Intent intent = new Intent(PhotographerMainActivity.this, ContactUsActivity.class);
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