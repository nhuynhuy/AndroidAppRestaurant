package com.example.RestaurantApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment {

 LinearLayout layoutthongtinnhanvien;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        layoutthongtinnhanvien = view.findViewById(R.id.layoutthongtinnhanvien);
        layoutthongtinnhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(),thongTinNhanVien.class);
                startActivity(intent);
            }
        });
        return view;
    }
}