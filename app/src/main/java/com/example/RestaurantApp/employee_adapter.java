package com.example.RestaurantApp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

public class employee_adapter extends BaseAdapter {
    thongTinNhanVien context;
    int layout;
    List<user> employeeList;

    public employee_adapter(thongTinNhanVien context, int layout, List<user> employeeList) {
        this.context = context;
        this.layout = layout;
        this.employeeList = employeeList;
    }

    @Override
    public int getCount() {
        return employeeList.size();
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {

        TextView txtHoTen, txtQuyen, txTSDT, txtMail;
        ImageButton btnOption;
        ImageView imghinh;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtHoTen = (TextView) convertView.findViewById(R.id.textViewDongHoTen);
            holder.txtQuyen = (TextView) convertView.findViewById(R.id.textViewDongQuyenHan);
            holder.txTSDT = (TextView) convertView.findViewById(R.id.textViewDongSDT);
            holder.txtMail = (TextView) convertView.findViewById(R.id.textViewDongMail);
            holder.btnOption = (ImageButton) convertView.findViewById(R.id.buttonOption);
            holder.imghinh = (ImageView) convertView.findViewById(R.id.imgHinhDong);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        user nv = employeeList.get(position);
        holder.txtHoTen.setText(nv.getHoTenNV());
        holder.txtQuyen.setText(nv.getQuyenHan());
        holder.txTSDT.setText(nv.getSDT());
        holder.txtMail.setText(nv.getEmail());
        byte[] hinhAnh = nv.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
        holder.imghinh.setImageBitmap(bitmap);


        holder.btnOption.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, holder.btnOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_xoasua);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_sua:
                                context.editEmployee(position);
                                break;
                            case R.id.menu_xoa:
                                context.removeEmployee(position);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });

        return convertView;

    }




}


