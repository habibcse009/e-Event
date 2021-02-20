package com.project.eevent.FoodManagement.Profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.eevent.Constant;
import com.project.eevent.R;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class EditProfileFmActivity extends AppCompatActivity {
    EditText extUsernameFM, extMobileFM, extEmailFM, extLocationFM, extAddressFM, extGenderFM, extPasswordFM;
    ImageView imgProfile;

    ProgressDialog loading;
    TextView txtNameFM;
    Button btnUpdateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_fm);

        extUsernameFM = findViewById(R.id.txt_editusernameFM);
        extMobileFM = findViewById(R.id.txt_editmobileFM);
        extEmailFM = findViewById(R.id.txt_editemailFM);
        extGenderFM = findViewById(R.id.txt_editgenderFM);
        extLocationFM = findViewById(R.id.txt_editlocationFM);
        extAddressFM = findViewById(R.id.txt_editaddressFM);
        extPasswordFM = findViewById(R.id.txt_editpasswordFM);
        txtNameFM = findViewById(R.id.txteditNameFM);
        btnUpdateProfile = findViewById(R.id.btn_updateFM);
        imgProfile = findViewById(R.id.profile_image);


        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("Update Profile");


        String getName = getIntent().getExtras().getString("name");
        String getMobile = getIntent().getExtras().getString("mobile");
        String getEmail = getIntent().getExtras().getString("email");
        String getGender = getIntent().getExtras().getString("gender");
        String getLocation = getIntent().getExtras().getString("location");
        String getAddress = getIntent().getExtras().getString("address");


        extUsernameFM.setText(getName);
        txtNameFM.setText(getName);
        extMobileFM.setText(getMobile);
        extMobileFM.setEnabled(false);
        extEmailFM.setText(getEmail);


        extLocationFM.setText(getLocation);
        extAddressFM.setText(getAddress);
        extGenderFM.setText(getGender);
        //extPasswordFM.setText("*******");


//        if(getGender.equals("Female"))
//        {
//            imgProfile.setImageResource(R.drawable.girl);
//        }

        extLocationFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] cityList = {"Dhaka", "Chittagong", "Sylhet", "Rajshahi", "Barishal", "Khulna", "Rangpur", "Mymensingh"};

                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileFmActivity.this);
                builder.setTitle("SELECT DIVISION");
                builder.setIcon(R.drawable.ic_location);


                builder.setCancelable(false);
                builder.setItems(cityList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:

                                extLocationFM.setText("Dhaka");
                                break;

                            case 1:

                                extLocationFM.setText("Chittagong");
                                break;

                            case 2:

                                extLocationFM.setText("Sylhet");
                                break;

                            case 3:

                                extLocationFM.setText("Rajshahi");
                                break;

                            case 4:

                                extLocationFM.setText("Barishal");
                                break;

                            case 5:

                                extLocationFM.setText("Khulna");
                                break;

                            case 6:

                                extLocationFM.setText("Rangpur");
                                break;

                            case 7:

                                extLocationFM.setText("Mymensingh");
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


                AlertDialog locationTypeDialog = builder.create();

                locationTypeDialog.show();
            }
        });


        extGenderFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] genderList = {"Male", "Female"};

                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileFmActivity.this);
                builder.setTitle("SELECT GENDER");


                builder.setCancelable(false);
                builder.setItems(genderList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                extGenderFM.setText(genderList[position]);
                                break;

                            case 1:
                                extGenderFM.setText(genderList[position]);
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


                AlertDialog accountTypeDialog = builder.create();

                accountTypeDialog.show();
            }
        });


        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileFmActivity.this);
                builder.setMessage("Want to Update Profile?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                // Perform Your Task Here--When Yes Is Pressed.
                                UpdateProfile();
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
        });
    }


    //update contact method
    public void UpdateProfile() {

        final String name = extUsernameFM.getText().toString();
        final String mobile = extMobileFM.getText().toString();
        final String email = extEmailFM.getText().toString();
        final String location = extLocationFM.getText().toString();
        final String address = extAddressFM.getText().toString();
        final String gender = extGenderFM.getText().toString();
        final String password = extPasswordFM.getText().toString();


        if (name.isEmpty()) {
            extUsernameFM.setError("Name Can't Empty");
            extUsernameFM.requestFocus();
        } else if (mobile.length() != 11) {
            extMobileFM.setError("Invalid Mobile Number");
            extMobileFM.requestFocus();

        } else if (email.isEmpty()) {
            extEmailFM.setError("Email can't be empty");
            extEmailFM.requestFocus();
        } else if (address.isEmpty()) {
            extAddressFM.setError("Address can't be empty");
            extAddressFM.requestFocus();
        } else if (password.length() < 4) {
            extPasswordFM.setError("Password too short! or Invalid Password");
            extPasswordFM.requestFocus();
        } else {
            loading = new ProgressDialog(this);
            // loading.setIcon(R.drawable.wait_icon);
            // loading.setTitle("Update");
            loading.setMessage("Update...Please wait...");
            loading.show();

            String URL = Constant.PROFILE_FM_UPDATE_URL;


            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            //for track response in logcat
                            Log.d("RESPONSE", response);
                            // Log.d("RESPONSE", userCell);


                            //If we are getting success from server
                            if (response.equals("success")) {

                                loading.dismiss();
                                //Starting profile activity

                                Intent intent = new Intent(EditProfileFmActivity.this, ProfileFmActivity.class);
                                Toasty.success(EditProfileFmActivity.this, " Profile Successfully Updated!", Toast.LENGTH_SHORT).show();
                                startActivity(intent);

                            }


                            //If we are getting success from server
                            else if (response.equals("failure")) {

                                loading.dismiss();
                                //Starting profile activity

                                Intent intent = new Intent(EditProfileFmActivity.this, ProfileFmActivity.class);
                                Toasty.error(EditProfileFmActivity.this, " Profile Update fail!", Toast.LENGTH_SHORT).show();
                                //startActivity(intent);

                            } else {

                                loading.dismiss();
                                Toasty.error(EditProfileFmActivity.this, "Network Error", Toast.LENGTH_SHORT).show();

                            }

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want

                            Toasty.error(EditProfileFmActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
                            loading.dismiss();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request

                    // params.put(Constant.KEY_ID, getID);
                    params.put(Constant.KEY_NAME, name);
                    params.put(Constant.KEY_MOBILE, mobile);
                    params.put(Constant.KEY_EMAIL, email);
                    params.put(Constant.KEY_GENDER, gender);
                    params.put(Constant.KEY_LOCATION, location);
                    params.put(Constant.KEY_ADDRESS, address);
                    params.put(Constant.KEY_PASSWORD, password);


                    //Log.d("ID", getID);

                    //returning parameter
                    return params;
                }
            };


            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(EditProfileFmActivity.this);
            requestQueue.add(stringRequest);
        }

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
