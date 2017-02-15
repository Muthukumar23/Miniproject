package com.main.miniproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.main.miniproject.common.PreferenceManager;
import com.main.miniproject.common.Util;
import com.main.miniproject.pojo.Employee;
import com.main.miniproject.pojo.EmployeeBean;
import com.main.miniproject.pojo.Skill;

import java.util.ArrayList;

/**
 * Created by capricorn on 15/2/17.
 */

public class EmployeeDetailsActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private String empid;
    ImageView user_image;
    ArrayList<Employee> emparraylist;
    Employee item;
    TextView firstname_textview, lastname_textview, address_textview, gender_textview,
            dob_textview, mobile_textview, email_textview, nationality_textview,
            language_textview, technical_textview, extra_textview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empdetails_layout);
        mContext = this;
        mActivity = this;

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialization();

        empid = getIntent().getStringExtra("emp_id");

        setValueMethod();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try{
            switch (item.getItemId()) {
                case android.R.id.home:
                    this.finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.booking_clearmenu, menu);
        return true;
    }

    private void initialization() {
        user_image = (ImageView) findViewById(R.id.user_image);
        firstname_textview = (TextView) findViewById(R.id.first_textview);
        lastname_textview = (TextView) findViewById(R.id.lastname_textview);
        address_textview = (TextView) findViewById(R.id.address_textview);
        gender_textview = (TextView) findViewById(R.id.gender_textview);
        dob_textview = (TextView) findViewById(R.id.dob_textview);
        mobile_textview = (TextView) findViewById(R.id.mobile_textview);
        email_textview = (TextView) findViewById(R.id.email_textview);
        nationality_textview = (TextView) findViewById(R.id.nationality_textview);
        language_textview = (TextView) findViewById(R.id.language_textview);
        technical_textview = (TextView) findViewById(R.id.technical_textview);
        extra_textview = (TextView) findViewById(R.id.extra_textview);

    }

    private void setValueMethod() {
        String t_skill ="";
        String e_skill ="";
        // Get EmployeeDetail Values
        EmployeeBean[] employeeResponse= PreferenceManager.getEmpResponse(mContext);
        emparraylist = new ArrayList<>();
        if(employeeResponse!=null) {
            if (employeeResponse[0].getEmployee() != null && employeeResponse[0].getEmployee().size() != 0) {
                if (employeeResponse[0].getEmployee().size() > 0) {
                    for (int j = 0; j < employeeResponse[0].getEmployee().size(); j++) {
                        item = employeeResponse[0].getEmployee().get(j);
                        emparraylist.add(item);
                    }
                }
            }
        }
        for(int k=0;k<emparraylist.size();k++) {
            if (emparraylist.get(k).getId().equals(empid)) {
                getSupportActionBar().setTitle(emparraylist.get(k).getFirstName());
                if (Util.isValidString(emparraylist.get(k).getImageURL())) {
                    Ion.with(mActivity).load(emparraylist.get(k).getImageURL()).withBitmap().asBitmap()
                            .setCallback(new FutureCallback<Bitmap>() {
                                @Override
                                public void onCompleted(Exception e, Bitmap bm) {
                                    if (bm != null) {
                                        user_image.setDrawingCacheEnabled(true);
                                        user_image.buildDrawingCache();
                                        user_image.setImageBitmap(bm);
                                    }
                                }
                            });
                } else {
                    user_image.setImageResource(R.mipmap.ic_account_circle_black);
                }
                firstname_textview.setText(emparraylist.get(k).getFirstName());
                lastname_textview.setText(emparraylist.get(k).getLastName());
                address_textview.setText(emparraylist.get(k).getAddress());
                gender_textview.setText(emparraylist.get(k).getGender());
                dob_textview.setText(emparraylist.get(k).getDob());
                mobile_textview.setText(emparraylist.get(k).getMobile());
                email_textview.setText(emparraylist.get(k).getEmail());
                nationality_textview.setText(emparraylist.get(k).getNationality());
                language_textview.setText(emparraylist.get(k).getLanguage());
                if (emparraylist.get(k).getSkills() != null && emparraylist.get(k).getSkills().size() != 0) {
                    for (int i = 0; i < emparraylist.get(k).getSkills().get(0).getTechnical().size(); i++) {
                        if(i==0){
                            t_skill = emparraylist.get(k).getSkills().get(0).getTechnical().get(i)+",";
                        }else{
                            t_skill += emparraylist.get(k).getSkills().get(0).getTechnical().get(i)+",";
                        }

                        technical_textview.setText(t_skill);
                    }
                }
                if (emparraylist.get(k).getSkills() != null && emparraylist.get(k).getSkills().size() != 0) {
                    for (int i = 0; i < emparraylist.get(k).getSkills().get(0).getExtraCurricular().size(); i++) {
                        if(i==0){
                            e_skill =  emparraylist.get(k).getSkills().get(0).getExtraCurricular().get(i)+",";
                        }else{
                            e_skill += emparraylist.get(k).getSkills().get(0).getExtraCurricular().get(i)+",";
                        }
                        extra_textview.setText(e_skill);
                    }
                }
            }
        }
    }
}
