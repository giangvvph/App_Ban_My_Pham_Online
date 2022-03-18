package com.example.duan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1.R;
import com.example.duan1.actyvity.GioHang;
import com.example.duan1.actyvity.MainActivity;
import com.example.duan1.model.Giohang;
import com.example.duan1.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> manggiohang;

    public GiohangAdapter(Context context, ArrayList<Giohang> manggiohang) {
        this.context = context;
        this.manggiohang = manggiohang;
    }

    @Override
    public int getCount() {
        return manggiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return manggiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
    public TextView tvTengiohang,tvGiagiohang;
    public ImageView imgGiohang;
    public Button btnmin,btnvalue,btnmax;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView ==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.tvTengiohang=convertView.findViewById(R.id.tvtengiohang);
            viewHolder.tvGiagiohang=convertView.findViewById(R.id.tvgiagiohang);
            viewHolder.btnmin=convertView.findViewById(R.id.btnmin);
            viewHolder.btnmax=convertView.findViewById(R.id.btnmax);
            viewHolder.btnvalue=convertView.findViewById(R.id.btnvalue);
            viewHolder.imgGiohang=convertView.findViewById(R.id.imggiohang);
            convertView.setTag(viewHolder);
        }
        else {
        viewHolder= (ViewHolder) convertView.getTag();
        }
        Giohang giohang= (Giohang) getItem(position);
        viewHolder.tvTengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.tvGiagiohang.setText(decimalFormat.format(giohang.getGiasp()) + "   Đ");
        Picasso.with(context).load(giohang.getHinhsp()).placeholder(R.drawable.anh1).error(R.drawable.anh2).into(viewHolder.imgGiohang);
        viewHolder.btnvalue.setText(giohang.getSoluongsp() +"");
        int sl= Integer.parseInt(viewHolder.btnvalue.getText().toString());
        if (sl>=10){
            viewHolder.btnmax.setVisibility(View.INVISIBLE);
            viewHolder.btnmin.setVisibility(View.VISIBLE);
        }
      else if (sl<=1){
          viewHolder.btnmin.setVisibility(View.INVISIBLE);
        }
      else if (sl>=1){
          viewHolder.btnmin.setVisibility(View.VISIBLE);
            viewHolder.btnmax.setVisibility(View.VISIBLE);
        }
        ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnmax.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              //int slnew =Integer.parseInt(viewHolder.btnvalue.getText().toString()) +1;
              int slnew=Integer.parseInt(finalViewHolder.btnvalue.getText().toString())+1;
              int slnow= MainActivity.manggiohang.get(position).getSoluongsp();
              long gianow=MainActivity.manggiohang.get(position).getGiasp();
              MainActivity.manggiohang.get(position).setSoluongsp(slnew);
              long gianewnhat=(gianow * slnew)/slnow;
              MainActivity.manggiohang.get(position).setGiasp(gianewnhat);
              DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
              finalViewHolder.tvGiagiohang.setText(decimalFormat.format(gianewnhat) + "   Đ");
              GioHang.EvenUntill();
              if (slnew>9){
                  finalViewHolder.btnmax.setVisibility(View.INVISIBLE);
                  finalViewHolder.btnmin.setVisibility(View.VISIBLE);
                  finalViewHolder.btnvalue.setText(String.valueOf(slnew));
              }
              else {
                  finalViewHolder.btnmax.setVisibility(View.VISIBLE);
                  finalViewHolder.btnmin.setVisibility(View.VISIBLE);
                  finalViewHolder.btnvalue.setText(String.valueOf(slnew));
              }
          }
      });
        viewHolder.btnmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slnew=Integer.parseInt(finalViewHolder.btnvalue.getText().toString())-1;
                int slnow= MainActivity.manggiohang.get(position).getSoluongsp();
                long gianow=MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluongsp(slnew);
                long gianewnhat=(gianow * slnew)/slnow;
                MainActivity.manggiohang.get(position).setGiasp(gianewnhat);
                DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                finalViewHolder.tvGiagiohang.setText(decimalFormat.format(gianewnhat) + "   Đ");
                GioHang.EvenUntill();
                if (slnew<2){
                    finalViewHolder.btnmin.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnmax.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(slnew));
                }
                else {
                    finalViewHolder.btnmax.setVisibility(View.VISIBLE);
                    finalViewHolder.btnmin.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(slnew));
                }

            }
        });
        return convertView;
    }
}
