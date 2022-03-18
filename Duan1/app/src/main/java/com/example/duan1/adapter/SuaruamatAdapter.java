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

public class SuaruamatAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayListSuaruamat;

    public SuaruamatAdapter(Context context, ArrayList<SanPham> arrayListSuaruamat) {
        this.context = context;
        this.arrayListSuaruamat = arrayListSuaruamat;
    }

    @Override
    public int getCount() {
        return arrayListSuaruamat.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListSuaruamat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txtTenSuaruamat,txtGiaSuaruamat,txtMotaSuaruamat;
        public ImageView imageViewSuaruamat;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_suaruamat,null);
            viewHolder.txtTenSuaruamat=convertView.findViewById(R.id.tvSuaruamat);
            viewHolder.txtGiaSuaruamat=convertView.findViewById(R.id.tvgiaSuaruamat);
            viewHolder.txtMotaSuaruamat=convertView.findViewById(R.id.tvmotaSuaruamat);
            viewHolder.imageViewSuaruamat=convertView.findViewById(R.id.imgSuaruamat);
            convertView.setTag(viewHolder);

        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        SanPham sanPham= (SanPham) getItem(position);
        viewHolder.txtTenSuaruamat.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtGiaSuaruamat.setText("Giá :"+ decimalFormat.format(sanPham.getGiasanpham())+"  Đồng");
        viewHolder.txtMotaSuaruamat.setMaxLines(2);
        viewHolder.txtMotaSuaruamat.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotaSuaruamat.setText(sanPham.getMotasanpham());
        Picasso.with(context).load(sanPham.getHinhanhsanpham()).placeholder(R.drawable.anh1).error(R.drawable.anh2)
                .into(viewHolder.imageViewSuaruamat);
        return convertView;
    }
}
