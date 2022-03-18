package com.example.duan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1.R;
import com.example.duan1.model.Loaisanpham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaisanphamAdapter extends BaseAdapter {
    Context context;
    ArrayList<Loaisanpham> arrayListloaisanpham;

    public LoaisanphamAdapter(Context context, ArrayList<Loaisanpham> arrayListloaisanpham) {
        this.context = context;
        this.arrayListloaisanpham = arrayListloaisanpham;
    }

    @Override
    public int getCount() {
        return arrayListloaisanpham.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListloaisanpham.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public  class ViewHolder{
    TextView tvtenloaisanpham;
    ImageView imgloaisanpham;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater
                    = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView=inflater.inflate(R.layout.dong_lv,null);
           viewHolder.tvtenloaisanpham=convertView.findViewById(R.id.tvloaisanpham);
           viewHolder.imgloaisanpham=convertView.findViewById(R.id.imgloaisp);
           convertView.setTag(viewHolder);

        }
        else {
            viewHolder= (ViewHolder) convertView.getTag();

        }
        Loaisanpham  loaisanpham= (Loaisanpham) getItem(position);
        viewHolder.tvtenloaisanpham.setText(loaisanpham.getTenloaiSanpham());
        Picasso.with(context).load(loaisanpham.getHinhanhSanpham())
                .placeholder(R.drawable.anh1).error(R.drawable.anh2).into(viewHolder.imgloaisanpham);
        return convertView;
    }
}
