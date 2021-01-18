package com.project.eevent.Admin.ViewUsers;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.eevent.Admin.AdminMainActivity;
import com.project.eevent.Constant;
import com.project.eevent.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FmListActivity extends AppCompatActivity {
    ListView CustomList;
    Button btnSearch;
    EditText etxtSearch;
    private ImageView imgNoData;
    private ProgressDialog loading;
    int MAX_SIZE = 999;
    public String cusCell[] = new String[MAX_SIZE];
    public String userID[] = new String[MAX_SIZE];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fm_list);

        CustomList = findViewById(R.id.listView_user);
        btnSearch = findViewById(R.id.btnSearch_user);
        etxtSearch = findViewById(R.id.etxt_search_user);

        imgNoData = findViewById(R.id.img_no_data);

        imgNoData.setVisibility(View.INVISIBLE);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("Food Management List");


        //call function
        getData("");


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = etxtSearch.getText().toString().trim();

                if (searchText.isEmpty()) {
                    Toasty.error(FmListActivity.this, "Please input Food Management name!", Toast.LENGTH_SHORT).show();
                } else {
                    getData(searchText);
                }
            }
        });
    }


    private void getData(String s) {

        String getSearchText = s;
        //showing progress dialog
        loading = new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        if (!s.isEmpty()) {
            getSearchText = s;
        }


        String url = Constant.FM_LIST_URL + "?text=" + getSearchText;

        Log.d("URL", url);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Response", response);
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();
                        Toasty.error(FmListActivity.this, "Network Error!", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {


        Log.d("Response 2", response);

        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constant.JSON_ARRAY);

            if (result.length() == 0) {
                Toasty.error(FmListActivity.this, "No Food Management Found!", Toast.LENGTH_SHORT).show();
                imgNoData.setImageResource(R.drawable.nodatafound);
                imgNoData.setVisibility(View.VISIBLE);

            } else {
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);

                    String id = jo.getString(Constant.KEY_ID);
                    String name = jo.getString(Constant.KEY_NAME);
                    String gender = jo.getString(Constant.KEY_GENDER);
                    String location = jo.getString(Constant.KEY_LOCATION);
                    String email = jo.getString(Constant.KEY_EMAIL);
                    String address = jo.getString(Constant.KEY_ADDRESS);
                    String mobile = jo.getString(Constant.KEY_MOBILE);

                    cusCell[i] = mobile;
                    userID[i] = id;

                    HashMap<String, String> user_msg = new HashMap<>();
                    user_msg.put(Constant.KEY_NAME, name);
                    user_msg.put(Constant.KEY_GENDER, "Gender : " + gender);
                    user_msg.put(Constant.KEY_LOCATION, location);
                    user_msg.put(Constant.KEY_ADDRESS, "Full Address : " + address);
                    user_msg.put(Constant.KEY_EMAIL, "Email Address : " + email);
                    user_msg.put(Constant.KEY_MOBILE, mobile + "    ||    ");


                    list.add(user_msg);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                FmListActivity.this, list, R.layout.fm_list_item,
                new String[]{Constant.KEY_NAME, Constant.KEY_GENDER, Constant.KEY_MOBILE, Constant.KEY_EMAIL, Constant.KEY_LOCATION, Constant.KEY_ADDRESS,},
                new int[]{R.id.user_name, R.id.user_gender, R.id.user_mobile, R.id.user_email, R.id.user_division, R.id.user_address,});
        CustomList.setAdapter(adapter);

        CustomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {


                new AlertDialog.Builder(FmListActivity.this)
                        //.setMessage("Want to Call this Shop Now?")
                        .setMessage("Choose about this Food Management")
                        .setCancelable(false)
                        .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + cusCell[position]));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Delete User", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //write here
                                new androidx.appcompat.app.AlertDialog.Builder(FmListActivity.this)
                                        .setMessage("Want to delete user  ?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {


                                                // Perform Your Task Here--When Yes Is Pressed.
                                                deleteUser(userID[position]);
                                                dialog.cancel();


                                            }
                                        })


                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Perform Your Task Here--When No is pressed
                                                dialog.cancel();
                                            }
                                        }).show();


                                //end

                            }
                        })

                        .show();
            }
        });

    }

    //update contact method
    public void deleteUser(final String id) {


        loading = new ProgressDialog(this);
        // loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Update");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Constant.DELETE_FM_URL;


        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //for track response in logcat
                        Log.d("RESPONSE", response);
                        // Log.d("RESPONSE", userCell);

                        String getResponse = response.trim();

                        //If we are getting success from server
                        if (getResponse.equals("success")) {

                            loading.dismiss();
                            //Starting profile activity

                            Intent intent = new Intent(FmListActivity.this, AdminMainActivity.class);
                            Toasty.success(FmListActivity.this, " Deleted !", Toast.LENGTH_SHORT).show();


                            startActivity(intent);

                        }


                        //If we are getting success from server
                        else if (getResponse.equals("failure")) {

                            loading.dismiss();
                            //Starting profile activity

                            //  Intent intent = new Intent(UserActivity.this, FarmerOrderActivity.class);
                            Toast.makeText(FmListActivity.this, "Delete fail!", Toast.LENGTH_SHORT).show();
                            //startActivity(intent);

                        } else {

                            loading.dismiss();
                            Toast.makeText(FmListActivity.this, "Network Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                        Toast.makeText(FmListActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request

                params.put(Constant.KEY_ID, id);
                params.put(Constant.KEY_STATUS, "1"); //1 for approved


                //returning parameter
                return params;
            }
        };


        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(FmListActivity.this);
        requestQueue.add(stringRequest);


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



