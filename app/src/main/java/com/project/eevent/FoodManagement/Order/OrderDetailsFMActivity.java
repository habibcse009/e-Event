package com.project.eevent.FoodManagement.Order;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.project.eevent.ConventionHall.ChMainActivity;
import com.project.eevent.R;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class OrderDetailsFMActivity extends AppCompatActivity {
    String name, getStatus, id, date, time, price, program_date, status, cus_cell, bkash_tex, address;

    TextView txtTitle, txtRetailerCell, txtOrderStatus, txtTimeDate, txtOrderId, txtProductName, txtProductPrice, txtProgramDate, txtFullAddress, txtBkashTexId;
    Button txtConfirmOrder, txtCancelOrder;
    ProgressDialog loading;
    String cell, getType, getCell;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_f_m);
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("Order Details");

        txtProductName = findViewById(R.id.txt_product_nameFM_OderDetails);
        txtProductPrice = findViewById(R.id.txt_product_priceFM_OderDetails);
        txtProgramDate = findViewById(R.id.txt_program_dateFM_OderDetails);
        txtRetailerCell = findViewById(R.id.txt_call_retailerFM_OderDetails);
        txtOrderId = findViewById(R.id.txt_order_idFM_OderDetails);
        txtFullAddress = findViewById(R.id.txt_full_addressFM_OderDetails);
        txtBkashTexId = findViewById(R.id.txt_bkash_text_idFM_OderDetails);
        txtTimeDate = findViewById(R.id.txt_time_dateFM_OderDetails);
        txtOrderStatus = findViewById(R.id.txt_order_statusFM_OderDetails);
        txtConfirmOrder = findViewById(R.id.txt_confirm_orderFM_OderDetails);
        txtCancelOrder = findViewById(R.id.txt_cancel_orderFM_OderDetails);
        txtTitle = findViewById(R.id.txtOrderInfoTitleFM_OderDetails);


        Typeface tf = Typeface.createFromAsset(getAssets(), "KaramellDemo-vmRDM.ttf");
//        Typeface tf = Typeface.createFromAsset(getAssets(), "MachowDemo-6Yjoq.ttf");
        //Typeface tf = Typeface.createFromAsset(getAssets(), "Quicksand-Regular.otf");
        txtTitle.setTypeface(tf);

        getType = getIntent().getExtras().getString("type");

        id = getIntent().getExtras().getString("id");
        name = getIntent().getExtras().getString("name");
        program_date = getIntent().getExtras().getString("program_date");
        price = getIntent().getExtras().getString("price");
        status = getIntent().getExtras().getString("status");
        cus_cell = getIntent().getExtras().getString("cus_cell");
        //bkash_tex=getIntent().getExtras().getString("bkash_tex");
        bkash_tex = getIntent().getExtras().getString("bkash_tex");
        address = getIntent().getExtras().getString("address");
        date = getIntent().getExtras().getString("date");
        time = getIntent().getExtras().getString("time");


        txtOrderId.setText("Order ID" + " : " + id);

        txtProductName.setText(name);
        txtProductPrice.setText("Tk. " + price);
        txtProgramDate.setText(program_date);
        txtFullAddress.setText(address);
        txtBkashTexId.setText(bkash_tex);
        txtTimeDate.setText("Time : " + time + " Date : " + date);


        if (status.equals("0")) {
            txtOrderStatus.setText("Order Pending");

        } else if (status.equals("1")) {


            txtOrderStatus.setText("Order Confirmed");
            txtCancelOrder.setVisibility(View.GONE);
            txtConfirmOrder.setVisibility(View.GONE);
        } else if (status.equals("2")) {
            txtOrderStatus.setText("Cancel");
            txtCancelOrder.setVisibility(View.GONE);
            txtConfirmOrder.setVisibility(View.GONE);
            txtRetailerCell.setVisibility(View.GONE);
        }


        txtRetailerCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + cus_cell));
                startActivity(intent);

            }
        });


        txtConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailsFMActivity.this);
                builder.setMessage("Want to confirmed order ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                // Perform Your Task Here--When Yes Is Pressed.
                                UpdateOrder("1");
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

        txtCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailsFMActivity.this);
                builder.setMessage("Want to cancel order ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                // Perform Your Task Here--When Yes Is Pressed.
                                UpdateOrder("2");
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
    public void UpdateOrder(String s) {

        getStatus = s;


        loading = new ProgressDialog(this);
        // loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Update");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Constant.UPDATE_ORDER_FM_URL;


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

                            Intent intent = new Intent(OrderDetailsFMActivity.this, ChMainActivity.class);

                            if (getStatus.equals("1"))
                                Toasty.success(OrderDetailsFMActivity.this, " Order Successfully Confirmed!", Toast.LENGTH_SHORT).show();
                            else if (getStatus.equals("2"))
                                Toasty.error(OrderDetailsFMActivity.this, " Order Cancel!", Toast.LENGTH_SHORT).show();


                            startActivity(intent);

                        }


                        //If we are getting success from server
                        else if (getResponse.equals("failure")) {

                            loading.dismiss();
                            //Starting profile activity

                            Intent intent = new Intent(OrderDetailsFMActivity.this, ChMainActivity.class);
                            Toast.makeText(OrderDetailsFMActivity.this, " Update fail!", Toast.LENGTH_SHORT).show();
                            //startActivity(intent);

                        } else {

                            loading.dismiss();
                            Toast.makeText(OrderDetailsFMActivity.this, "Network Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                        Toast.makeText(OrderDetailsFMActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request

                params.put(Constant.KEY_ID, id);
                params.put(Constant.KEY_STATUS, getStatus);


                //returning parameter
                return params;
            }
        };


        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(OrderDetailsFMActivity.this);
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
