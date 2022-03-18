package com.example.duan1.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1.R;
import com.example.duan1.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SonAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayListSon;

    public SonAdapter(Context context, ArrayList<SanPham> arrayListSon) {
        this.context = context;
        this.arrayListSon = arrayListSon;
    }

    @Override
    public int getCount() {
        return arrayListSon.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListSon.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
    public TextView txtTenSon,txtGiaSon,txtMotaSon;
    public ImageView imageViewSon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_son,null);
            viewHolder.txtTenSon=convertView.findViewById(R.id.tvSon);
            viewHolder.txtGiaSon=convertView.findViewById(R.id.tvgiaSon);
            viewHolder.txtMotaSon=convertView.findViewById(R.id.tvmotaSon);
            viewHolder.imageViewSon=convertView.findViewById(R.id.imgSon);
            convertView.setTag(viewHolder);

        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        SanPham sanPham= (SanPham) getItem(position);
        viewHolder.txtTenSon.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtGiaSon.setText("Giá :"+ decimalFormat.format(sanPham.getGiasanpham())+"  Đồng");
        viewHolder.txtMotaSon.setMaxLines(2);
        viewHolder.txtMotaSon.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotaSon.setText(sanPham.getMotasanpham());
        Picasso.with(context).load(sanPham.getHinhanhsanpham()).placeholder(R.drawable.anh1).error(R.drawable.anh2)
                .into(viewHolder.imageViewSon);
        return convertView;
    }
}
