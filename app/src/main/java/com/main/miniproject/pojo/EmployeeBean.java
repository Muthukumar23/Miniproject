package com.main.miniproject.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeBean implements Parcelable{

    @SerializedName("employee")
    @Expose
    private List<Employee> employee;

    protected EmployeeBean(Parcel in) {
    }

    public static final Creator<EmployeeBean> CREATOR = new Creator<EmployeeBean>() {
        @Override
        public EmployeeBean createFromParcel(Parcel in) {
            return new EmployeeBean(in);
        }

        @Override
        public EmployeeBean[] newArray(int size) {
            return new EmployeeBean[size];
        }
    };

    public List<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(List<Employee> employee) {
        this.employee = employee;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}