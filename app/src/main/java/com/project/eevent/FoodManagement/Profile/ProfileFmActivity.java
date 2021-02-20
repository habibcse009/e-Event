package com.project.eevent.FoodManagement.Profile;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.eevent.Constant;
import com.project.eevent.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFmActivity extends AppCompatActivity {
    TextView txtNameFM, txtUserNameFM, txtGenderFM, txtAddressFM, txtMobileFM, txtEmailFM, txtLocationFM, txtPasswordFM;
    Button btnEditProfile;

    String UserCell;

    private ProgressDialog loading;

    SharedPreferences sharedPreferences;

    ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_fm);

        //Fetching mobile from shared preferences
        sharedPreferences = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String mobile = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        UserCell = mobile;
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("Profile");


        txtNameFM = findViewById(R.id.txtNameFM);
        txtUserNameFM = findViewById(R.id.txtUsernameFM);
        txtMobileFM = findViewById(R.id.txtMobileFM);
        txtEmailFM = findViewById(R.id.txtEmailFM);
        txtGenderFM = findViewById(R.id.txtGenderFM);
        txtLocationFM = findViewById(R.id.txtLocationFM);
        txtAddressFM = findViewById(R.id.txtAddressFM);


        profilePic = findViewById(R.id.profile_image);
        btnEditProfile = findViewById(R.id.btnEditProfileFM);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileFmActivity.this, EditProfileFmActivity.class);
                intent.putExtra("name", txtNameFM.getText());
                intent.putExtra("mobile", txtMobileFM.getText());
                intent.putExtra("email", txtEmailFM.getText());
                intent.putExtra("location", txtLocationFM.getText());
                intent.putExtra("address", txtAddressFM.getText());
                intent.putExtra("gender", txtGenderFM.getText());
                startActivity(intent);
            }
        });

        //call function
        getData();

    }


    private void getData() {

        //loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        //showing progress dialog
        loading = new ProgressDialog(ProfileFmActivity.this);
        loading.setMessage("Please wait....");
        loading.show();


        String url = Constant.PROFILE_FM_URL + UserCell;  // url for connecting php file

        Log.d("URL", url);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("Volley Error", "Error:" + error);
                        Toasty.error(ProfileFmActivity.this, "No Internet Connection!", Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ProfileFmActivity.this);
        requestQueue.add(stringRequest);
    }


    private void showJSON(String response) {


        Log.d("RESPONSE", response);

        String name = "";
        String gender = "";
        String mobile = "";
        String email = "";
        String location = "";
        String address = "";


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constant.JSON_ARRAY);
            JSONObject ProfileData = result.getJSONObject(0);

            name = ProfileData.getString(Constant.KEY_NAME);
            gender = ProfileData.getString(Constant.KEY_GENDER);
            mobile = ProfileData.getString(Constant.KEY_MOBILE);
            email = ProfileData.getString(Constant.KEY_EMAIL);
            location = ProfileData.getString(Constant.KEY_LOCATION);
            address = ProfileData.getString(Constant.KEY_ADDRESS);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);

        txtNameFM.setText(name);
        txtUserNameFM.setText(name);
        txtGenderFM.setText(gender);

//        if (gender.equals("Female"))
//        {
//            profilePic.setImageResource(R.drawable.girl);
//        }


        txtMobileFM.setText(mobile);
        txtEmailFM.setText(email);
        txtLocationFM.setText(location);
        txtAddressFM.setText(address);

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