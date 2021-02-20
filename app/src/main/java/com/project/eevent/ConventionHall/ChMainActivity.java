package com.project.eevent.ConventionHall;

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
import com.project.eevent.ConventionHall.Order.OrderListCHActivity;
import com.project.eevent.ConventionHall.Product.AddProductCHActivity;
import com.project.eevent.ConventionHall.Product.AllProductCHActivity;
import com.project.eevent.ConventionHall.Profile.ProfileChActivity;
import com.project.eevent.LoginActivity;
import com.project.eevent.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import es.dmoral.toasty.Toasty;

public class ChMainActivity extends AppCompatActivity {
    CardView cardProfileCH, cardMyServices, cardCreateServices, cardContactUs, cardOrdersCH, cardLogoutCH, cardContractAdminCH;

    SharedPreferences sp;
    SharedPreferences.Editor editor;


    ShimmerTextView txtWelcomeNameCH, txtHelloCH;
    Shimmer shimmerCH;

    String UserCell;

    private ProgressDialog loading;


    //for double back press to exit
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch_main);

        getSupportActionBar().setTitle("Convention Hall Panel");
        //Fetching mobile from shared preferences
        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String mobile = sp.getString(Constant.CELL_SHARED_PREF, "Not Available");
        UserCell = mobile;

        cardProfileCH = findViewById(R.id.card_profileCH);
        cardMyServices = findViewById(R.id.card_viewProductCH);
        cardCreateServices = findViewById(R.id.card_addServicesCH);
        cardOrdersCH = findViewById(R.id.card_myOrderCH);
        cardLogoutCH = findViewById(R.id.card_logoutCH);


        Typeface tf = Typeface.createFromAsset(getAssets(), "Milkshake.ttf");

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        cardProfileCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChMainActivity.this, ProfileChActivity.class);
                startActivity(intent);

            }
        });


        cardMyServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChMainActivity.this, AllProductCHActivity.class);
                intent.putExtra("type", "ConventionHall");
                startActivity(intent);

            }
        });
        cardCreateServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChMainActivity.this, AddProductCHActivity.class);
                intent.putExtra("type", "ConventionHall");
                startActivity(intent);

            }
        });


        cardOrdersCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChMainActivity.this, OrderListCHActivity.class);
                startActivity(intent);

            }
        });


        cardLogoutCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.clear();
                editor.apply();
                // finishAffinity();
                Toasty.info(ChMainActivity.this, "Log out from Convention Hall panel!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChMainActivity.this, LoginActivity.class);
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
                Intent intent = new Intent(ChMainActivity.this, ContactUsActivity.class);
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