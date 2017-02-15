package com.main.miniproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.main.miniproject.adapter.EmployeeAdapter;
import com.main.miniproject.common.InternetConnection;
import com.main.miniproject.common.PreferenceManager;
import com.main.miniproject.common.ProgressDialogCustom;
import com.main.miniproject.common.Util;
import com.main.miniproject.pojo.Employee;
import com.main.miniproject.pojo.EmployeeBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Context mContext;
    RelativeLayout activity_main;
    ProgressDialogCustom progressDialog;
    RecyclerView mRecyclerView;
    ArrayList<Employee> emparraylist;
    EmployeeAdapter mEmployeeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Employee List");
        activity_main = (RelativeLayout)findViewById(R.id.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);

        if(InternetConnection.isConnected(mContext)) {
            loadEmployeeDetails();
        }else{
            showsnackbar(getString(R.string.no_internet));
            getEmployeeDetailsMethod();
        }
    }

    /**
     * Get Booking vehicle Response list Method
     * */
    private void getEmployeeDetailsMethod() {
        try{
            // Get EmployeeDetail Values
            EmployeeBean[] employeeResponse= PreferenceManager.getEmpResponse(mContext);
            emparraylist = new ArrayList<>();
            if(employeeResponse!=null) {
                if (employeeResponse[0].getEmployee() != null && employeeResponse[0].getEmployee().size() != 0) {
                    if (employeeResponse[0].getEmployee().size() > 0) {
                        for (int j = 0; j < employeeResponse[0].getEmployee().size(); j++) {
                            Employee listt = employeeResponse[0].getEmployee().get(j);
                            emparraylist.add(listt);
                        }
                    }
                }
            }
            if(emparraylist.size()>0) {
                mRecyclerView.setVisibility(View.VISIBLE);
                mEmployeeAdapter = new EmployeeAdapter(this, emparraylist);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mRecyclerView.setAdapter(mEmployeeAdapter);

                // Vehicle Item Select List
                mEmployeeAdapter.SetOnItemClickListener(new EmployeeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, String empid) {
                        Intent intent = new Intent(getApplicationContext(), EmployeeDetailsActivity.class);
                        intent.putExtra("emp_id",empid);
                        startActivity(intent);
                    }
                });
            }else{
                mRecyclerView.setVisibility(View.GONE);
            }


        }catch (Exception e){e.printStackTrace();}
    }

    private void loadEmployeeDetails() {
        try{
            progressDialog.showProgressDialog(mContext, getString(R.string.pleasewait));
            String employeeurl = "https://private-2a004-androidtest3.apiary-mock.com/employeesList";
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, employeeurl, new
                    Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progressDialog.dismissProgressDialog();

                                if (Util.isValidString(response.replace("\n",""))) {
                                    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                                    //EmployeeBean employeeResponse = gson.fromJson(response, type);
                                    EmployeeBean[] employeeResponse = gson.fromJson(response.replace("\n",""), EmployeeBean[].class);
                                    // Saved Preference class
                                    PreferenceManager.saveEmpResponse(mContext,employeeResponse);
                                    getEmployeeDetailsMethod();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                //progressDialog.dismissProgressDialog();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismissProgressDialog();
                    Toast.makeText(mContext, "onErrorResponse", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    int mStatusCode = response.statusCode;
                    Log.d("Response code>>>",""+mStatusCode);
                    return super.parseNetworkResponse(response);
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    Util.KEY_SERVICE_TIME_OUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showsnackbar(String message) {
        try {
            Snackbar snackbar = Snackbar.make(activity_main, message, Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            TextView tv = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.YELLOW);
            snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
