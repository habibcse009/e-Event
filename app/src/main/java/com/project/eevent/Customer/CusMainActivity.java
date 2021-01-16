package com.project.eevent.Customer;

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
import com.project.eevent.Customer.Profile.ProfileCusActivity;
import com.project.eevent.LoginActivity;
import com.project.eevent.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import es.dmoral.toasty.Toasty;

public class CusMainActivity extends AppCompatActivity {
    CardView cardProfileCus, cardCHCus, cardPhotographerCus, cardEDCus, cardFMCus, cardContactUs, cardOrdersCus, cardLogoutCus, cardContractAdminCus;

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
        setContentView(R.layout.activity_cus_main);

        getSupportActionBar().setTitle("Customer Panel");
        //Fetching mobile from shared preferences
        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String mobile = sp.getString(Constant.CELL_SHARED_PREF, "Not Available");
        UserCell = mobile;

        cardProfileCus = findViewById(R.id.card_profileCus);
        cardCHCus = findViewById(R.id.card_viewCH);
        cardPhotographerCus = findViewById(R.id.card_viewPhotographer);
        cardEDCus = findViewById(R.id.card_viewED);
        cardFMCus = findViewById(R.id.card_viewFM);
        cardContactUs = findViewById(R.id.card_aboutUs);
        cardOrdersCus = findViewById(R.id.card_myOrder);
        cardLogoutCus = findViewById(R.id.card_logoutCus);


        Typeface tf = Typeface.createFromAsset(getAssets(), "Milkshake.ttf");

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        cardProfileCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CusMainActivity.this, ProfileCusActivity.class);
                startActivity(intent);

            }
        });


        cardContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CusMainActivity.this, ContactUsActivity.class);
                startActivity(intent);

            }
        });


        cardOrdersCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Intent intent = new Intent(CusMainActivity.this, OrderListCusActivity.class);
                //   startActivity(intent);

            }
        });

        cardCHCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent = new Intent(CusMainActivity.this, AllProductsActivity.class);
                intent.putExtra("type", "Customer");
                startActivity(intent);
*/
            }
        });
        cardPhotographerCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent = new Intent(CusMainActivity.this, AllProductsActivity.class);
                intent.putExtra("type", "Customer");
                startActivity(intent);
*/
            }
        });
        cardEDCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent = new Intent(CusMainActivity.this, AllProductsActivity.class);
                intent.putExtra("type", "Customer");
                startActivity(intent);
*/
            }
        });
        cardFMCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent = new Intent(CusMainActivity.this, AllProductsActivity.class);
                intent.putExtra("type", "Customer");
                startActivity(intent);
*/
            }
        });


        cardLogoutCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.clear();
                editor.apply();
                // finishAffinity();
                Toasty.info(CusMainActivity.this, "Log out from customer panel!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CusMainActivity.this, LoginActivity.class);
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
                Intent intent = new Intent(CusMainActivity.this, ContactUsActivity.class);
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