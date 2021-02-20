package com.project.eevent.EventDecorator;

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
import com.project.eevent.EventDecorator.Order.OrderListEDActivity;
import com.project.eevent.EventDecorator.Product.AddProductEDActivity;
import com.project.eevent.EventDecorator.Product.AllProductEDActivity;
import com.project.eevent.EventDecorator.Profile.ProfileEdActivity;
import com.project.eevent.LoginActivity;
import com.project.eevent.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import es.dmoral.toasty.Toasty;

public class EdMainActivity extends AppCompatActivity {
    CardView cardProfileED, cardMyServices, cardCreateServices, cardContactUs, cardOrdersED, cardLogoutED, cardContractAdminED;

    SharedPreferences sp;
    SharedPreferences.Editor editor;


    ShimmerTextView txtWelcomeNameED, txtHelloED;
    Shimmer shimmerED;

    String UserCell;

    private ProgressDialog loading;


    //for double back press to exit
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ed_main);

        getSupportActionBar().setTitle("Event Decorator Panel");
        //Fetching mobile from shared preferences
        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String mobile = sp.getString(Constant.CELL_SHARED_PREF, "Not Available");
        UserCell = mobile;

        cardProfileED = findViewById(R.id.card_profileED);
        cardMyServices = findViewById(R.id.card_viewProductED);
        cardCreateServices = findViewById(R.id.card_addServicesED);
        cardOrdersED = findViewById(R.id.card_myOrderED);
        cardLogoutED = findViewById(R.id.card_logoutED);


        Typeface tf = Typeface.createFromAsset(getAssets(), "Milkshake.ttf");

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        cardProfileED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EdMainActivity.this, ProfileEdActivity.class);
                startActivity(intent);

            }
        });


        cardMyServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EdMainActivity.this, AllProductEDActivity.class);
                intent.putExtra("type", "EventDecorator");
                startActivity(intent);

            }
        });
        cardCreateServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EdMainActivity.this, AddProductEDActivity.class);
                intent.putExtra("type", "EventDecorator");
                startActivity(intent);

            }
        });


        cardOrdersED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EdMainActivity.this, OrderListEDActivity.class);
                startActivity(intent);

            }
        });


        cardLogoutED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.clear();
                editor.apply();
                // finishAffinity();
                Toasty.info(EdMainActivity.this, "Log out from Event Decorator panel!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EdMainActivity.this, LoginActivity.class);
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
                Intent intent = new Intent(EdMainActivity.this, ContactUsActivity.class);
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