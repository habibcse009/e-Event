package com.project.eevent.Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.project.eevent.Admin.Profile.ProfileAdminActivity;
import com.project.eevent.Admin.ViewUsers.ChListActivity;
import com.project.eevent.Admin.ViewUsers.CusListActivity;
import com.project.eevent.Admin.ViewUsers.EdListActivity;
import com.project.eevent.Admin.ViewUsers.FmListActivity;
import com.project.eevent.Admin.ViewUsers.PhotographerListActivity;
import com.project.eevent.Constant;
import com.project.eevent.LoginActivity;
import com.project.eevent.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import es.dmoral.toasty.Toasty;

public class AdminMainActivity extends AppCompatActivity {

    CardView cardProfileAdmin, cardCHAdmin, cardCusAdmin, cardEDAdmin, cardFMAdmin, cardPhotographerAdmin, cardLogoutAdmin;

    SharedPreferences sp;
    SharedPreferences.Editor editor;


    ShimmerTextView txtWelcomeNameAdmin, txtHelloAdmin;
    Shimmer shimmerAdmin;

    String UserCell;

    //for double back press to exit
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);


        getSupportActionBar().setTitle("Admin Panel");
        //Fetching mobile from shared preferences
        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String mobile = sp.getString(Constant.CELL_SHARED_PREF, "Not Available");
        UserCell = mobile;

        cardProfileAdmin = findViewById(R.id.card_profileAdmn);
        cardCHAdmin = findViewById(R.id.card_viewChAdmn);
        cardCusAdmin = findViewById(R.id.card_cusAdmn);
        cardEDAdmin = findViewById(R.id.card_edAdmn);
        cardFMAdmin = findViewById(R.id.card_fmAdmn);
        cardPhotographerAdmin = findViewById(R.id.card_photographerAdmn);
        //cardDashboadAdmin.setVisibility(View.INVISIBLE);
        cardLogoutAdmin = findViewById(R.id.card_logoutAdmn);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Milkshake.ttf");
        //txtTitle.setTypeface(tf);

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        cardProfileAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, ProfileAdminActivity.class);
                startActivity(intent);

            }
        });

        cardPhotographerAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, PhotographerListActivity.class);
                startActivity(intent);

            }
        });


        cardFMAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, FmListActivity.class);
                startActivity(intent);

            }
        });
        cardEDAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, EdListActivity.class);
                startActivity(intent);

            }
        });
        cardCusAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, CusListActivity.class);
                startActivity(intent);

            }
        });
        cardCHAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, ChListActivity.class);
                startActivity(intent);

            }
        });


        cardLogoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                editor.clear();
                editor.apply();
                // finishAffinity();
                Toasty.info(AdminMainActivity.this, "Log out from admin panel!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminMainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });


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