package com.main.miniproject.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.main.miniproject.R;


public class ProgressDialogCustom {
    static ProgressDialog progressDialog;
    static LayoutInflater linflater;
    public static void showProgressDialog(Context context,String message){
        try {
            progressDialog = new ProgressDialog(context);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View myView = linflater.inflate(R.layout.progress_dialog_layout, null);
            TextView loading_msg=(TextView)myView.findViewById(R.id.loading_msg);
            loading_msg.setText(message);
            progressDialog.setContentView(myView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissProgressDialog(){
        try {
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean isProgressDialogVisible(){
        try {
            if(progressDialog.isShowing()){
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
