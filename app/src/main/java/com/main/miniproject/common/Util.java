package com.main.miniproject.common;


import org.json.JSONObject;

public class Util {

    public static int KEY_SERVICE_TIME_OUT=600000;

    public static boolean isValidString(String response) {

        if (response != null && !response.equalsIgnoreCase("null") && response.trim().length() >0) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isValidJson(JSONObject response) {
        if (response != null) {
            return true;
        } else {
            return false;
        }
    }

}
