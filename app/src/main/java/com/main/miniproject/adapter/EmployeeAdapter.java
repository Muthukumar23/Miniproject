package com.main.miniproject.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.main.miniproject.R;
import com.main.miniproject.common.Util;
import com.main.miniproject.pojo.Employee;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private Activity mActivity;
    private List<Employee> empAdapterlist;
    OnItemClickListener mOnitemClickListener;

    public void SetOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnitemClickListener = onItemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout main_layout;
        CircleImageView emp_img;
        TextView firstName;
        TextView lastName;
        TextView address;

        public ViewHolder(View itemView) {
            super(itemView);
            main_layout = (RelativeLayout) itemView.findViewById(R.id.main_layout);
            emp_img = (CircleImageView) itemView.findViewById(R.id.empImage);
            firstName = (TextView) itemView.findViewById(R.id.firstName);
            lastName = (TextView) itemView.findViewById(R.id.lastName);
            address = (TextView) itemView.findViewById(R.id.address);
        }
    }

    public EmployeeAdapter(Activity mActivity, List<Employee> empAdapterlist) {
        this.mActivity = mActivity;
        this.empAdapterlist = empAdapterlist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empadapter, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Employee employeelist = empAdapterlist.get(position);
        holder.firstName.setText("First Name: "+employeelist.getFirstName());
        holder.lastName.setText("Last Name: "+employeelist.getLastName());
        holder.address.setText("Address: "+employeelist.getAddress());
        if(Util.isValidString(employeelist.getImageURL())) {
            Ion.with(mActivity).load(employeelist.getImageURL()).withBitmap().asBitmap()
                    .setCallback(new FutureCallback<Bitmap>() {
                        @Override
                        public void onCompleted(Exception e, Bitmap bm) {
                            if (bm != null) {
                                holder.emp_img.setDrawingCacheEnabled(true);
                                holder.emp_img.buildDrawingCache();
                                holder.emp_img.setImageBitmap(bm);
                            }
                        }
                    });
        }else{
            holder.emp_img.setImageResource(R.mipmap.ic_account_circle_black);
        }
        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnitemClickListener.onItemClick(view, employeelist.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return empAdapterlist.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view,String id);
    }
}
