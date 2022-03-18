package com.example.duan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    Context context;
    ArrayList<SanPham>arrayListsanpham;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arrayListsanpham) {
        this.context = context;
        this.arrayListsanpham = arrayListsanpham;
    }

    @NonNull

    @Override
    public ItemHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanpham,null);
        ItemHolder itemHolder=new ItemHolder(view);


        return itemHolder;
    }

    @Override
    public void onBindViewHolder( SanPhamAdapter.ItemHolder holder, int position) {
        SanPham sanPham=arrayListsanpham.get(position);
        holder.txttensanpham.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText("Giá :"+ decimalFormat.format(sanPham.getGiasanpham())+"  Đồng");
        Picasso.with(context).load(sanPham.getHinhanhsanpham()).placeholder(R.drawable.anh1).
                error(R.drawable.anhchao).into(holder.imghinhanhsanpham);

    }

    @Override
    public int getItemCount() {
        return arrayListsanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhanhsanpham;
         public TextView txttensanpham,txtgiasanpham;

        public ItemHolder(View itemView) {
            super(itemView);
            imghinhanhsanpham=itemView.findViewById(R.id.imgsanpham);
            txttensanpham=itemView.findViewById(R.id.tvtensanpham);
            txtgiasanpham=itemView.findViewById(R.id.tvgiasanpham);

        }
    }
}
