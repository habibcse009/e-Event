package com.project.eevent.EventDecorator.Profile;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

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

public class EditProfileEdActivity extends AppCompatActivity {
    EditText extUsernameED, extMobileED, extEmailED, extLocationED, extAddressED, extGenderED, extPasswordED;
    ImageView imgProfile;

    ProgressDialog loading;
    TextView txtNameED;
    Button btnUpdateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_ed);

        extUsernameED = findViewById(R.id.txt_editusernameED);
        extMobileED = findViewById(R.id.txt_editmobileED);
        extEmailED = findViewById(R.id.txt_editemailED);
        extGenderED = findViewById(R.id.txt_editgenderED);
        extLocationED = findViewById(R.id.txt_editlocationED);
        extAddressED = findViewById(R.id.txt_editaddressED);
        extPasswordED = findViewById(R.id.txt_editpasswordED);
        txtNameED = findViewById(R.id.txteditNameED);
        btnUpdateProfile = findViewById(R.id.btn_updateED);
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


        extUsernameED.setText(getName);
        txtNameED.setText(getName);
        extMobileED.setText(getMobile);
        extMobileED.setEnabled(false);
        extEmailED.setText(getEmail);


        extLocationED.setText(getLocation);
        extAddressED.setText(getAddress);
        extGenderED.setText(getGender);
        //extPasswordED.setText("*******");


//        if(getGender.equals("Female"))
//        {
//            imgProfile.setImageResource(R.drawable.girl);
//        }

        extLocationED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] cityList = {"Dhaka", "Chittagong", "Sylhet", "Rajshahi", "Barishal", "Khulna", "Rangpur", "Mymensingh"};

                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileEdActivity.this);
                builder.setTitle("SELECT DIVISION");
                builder.setIcon(R.drawable.ic_location);


                builder.setCancelable(false);
                builder.setItems(cityList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:

                                extLocationED.setText("Dhaka");
                                break;

                            case 1:

                                extLocationED.setText("Chittagong");
                                break;

                            case 2:

                                extLocationED.setText("Sylhet");
                                break;

                            case 3:

                                extLocationED.setText("Rajshahi");
                                break;

                            case 4:

                                extLocationED.setText("Barishal");
                                break;

                            case 5:

                                extLocationED.setText("Khulna");
                                break;

                            case 6:

                                extLocationED.setText("Rangpur");
                                break;

                            case 7:

                                extLocationED.setText("Mymensingh");
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


        extGenderED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] genderList = {"Male", "Female"};

                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileEdActivity.this);
                builder.setTitle("SELECT GENDER");


                builder.setCancelable(false);
                builder.setItems(genderList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                extGenderED.setText(genderList[position]);
                                break;

                            case 1:
                                extGenderED.setText(genderList[position]);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileEdActivity.this);
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

        final String name = extUsernameED.getText().toString();
        final String mobile = extMobileED.getText().toString();
        final String email = extEmailED.getText().toString();
        final String location = extLocationED.getText().toString();
        final String address = extAddressED.getText().toString();
        final String gender = extGenderED.getText().toString();
        final String password = extPasswordED.getText().toString();


        if (name.isEmpty()) {
            extUsernameED.setError("Name Can't Empty");
            extUsernameED.requestFocus();
        } else if (mobile.length() != 11) {
            extMobileED.setError("Invalid Mobile Number");
            extMobileED.requestFocus();

        } else if (email.isEmpty()) {
            extEmailED.setError("Email can't be empty");
            extEmailED.requestFocus();
        } else if (address.isEmpty()) {
            extAddressED.setError("Address can't be empty");
            extAddressED.requestFocus();
        } else if (password.length() < 4) {
            extPasswordED.setError("Password too short! or Invalid Password");
            extPasswordED.requestFocus();
        } else {
            loading = new ProgressDialog(this);
            // loading.setIcon(R.drawable.wait_icon);
            // loading.setTitle("Update");
            loading.setMessage("Update...Please wait...");
            loading.show();

            String URL = Constant.PROFILE_ED_UPDATE_URL;


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

                                Intent intent = new Intent(EditProfileEdActivity.this, ProfileEdActivity.class);
                                Toasty.success(EditProfileEdActivity.this, " Profile Successfully Updated!", Toast.LENGTH_SHORT).show();
                                startActivity(intent);

                            }


                            //If we are getting success from server
                            else if (response.equals("failure")) {

                                loading.dismiss();
                                //Starting profile activity

                                Intent intent = new Intent(EditProfileEdActivity.this, ProfileEdActivity.class);
                                Toasty.error(EditProfileEdActivity.this, " Profile Update fail!", Toast.LENGTH_SHORT).show();
                                //startActivity(intent);

                            } else {

                                loading.dismiss();
                                Toasty.error(EditProfileEdActivity.this, "Network Error", Toast.LENGTH_SHORT).show();

                            }

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want

                            Toasty.error(EditProfileEdActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
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
            RequestQueue requestQueue = Volley.newRequestQueue(EditProfileEdActivity.this);
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
