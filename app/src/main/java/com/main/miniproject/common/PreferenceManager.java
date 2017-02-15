package com.main.miniproject.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.main.miniproject.pojo.EmployeeBean;


public class PreferenceManager {

    private static String PREFSEMP_NAME ="Emp";
    private static String Emp_RESPONSE ="Emp Response";

    public PreferenceManager() {
        super();
    }

    public static void saveEmpResponse(Context context, EmployeeBean[] employeeresponse) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = context.getSharedPreferences(PREFSEMP_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(employeeresponse);
        editor.putString(Emp_RESPONSE, json);
        editor.commit();
    }

    public static EmployeeBean[] getEmpResponse(Context context) {
        SharedPreferences settings;
        settings = context.getSharedPreferences(PREFSEMP_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = settings.getString(Emp_RESPONSE, "");
        EmployeeBean[] employeeresponse = gson.fromJson(json, EmployeeBean[].class);
        return employeeresponse;
    }
}
