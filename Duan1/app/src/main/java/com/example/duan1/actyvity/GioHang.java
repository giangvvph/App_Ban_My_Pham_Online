package com.example.duan1.actyvity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.duan1.R;
import com.example.duan1.adapter.GiohangAdapter;
import com.example.duan1.ultil.Checkconection;

import java.text.DecimalFormat;

public class GioHang extends AppCompatActivity {

    ListView lvgiohang;
TextView tvThongbao;
    static TextView tvTongTien;
Button btnthanhtoan,btntieptucmua;
Toolbar toolbargiohang;
GiohangAdapter giohangAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        Anhxa();
        Actiontoolbar();
        CheckData();
        EvenUntill();
        CactchonItemlv();
        SukienButon();
    }

    private void SukienButon() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size()>0){
                    Intent intent=new Intent(getApplicationContext(),ThongTinKhachhangActivity.class);
                    startActivity(intent);

                }
                else {
                    Checkconection.ShowToast_Short(getApplicationContext(),"Giỏ hàng của bạn chưa có sản phẩm để thanh toán");

                }
            }
        });
    }

    private void CactchonItemlv() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(GioHang.this);
                builder.setTitle("Xác Nhận Xóa Sản Phẩm");
                builder.setMessage( "Bạn Có Muốn Xóa Sản Phẩm Này");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                if (MainActivity.manggiohang.size()<=0){
                    tvThongbao.setVisibility(View.VISIBLE);
                }else {
                    MainActivity.manggiohang.remove(position);
                    giohangAdapter.notifyDataSetChanged();
                    EvenUntill();
                    if (MainActivity.manggiohang.size()<=0){
                        tvThongbao.setVisibility(View.VISIBLE);
                    }else {
                        tvThongbao.setVisibility(View.INVISIBLE);
                        giohangAdapter.notifyDataSetChanged();
                        EvenUntill();
                    }
                }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    giohangAdapter.notifyDataSetChanged();
                    EvenUntill();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EvenUntill() {
        long tongtien=0;
        for (int i=0;i<MainActivity.manggiohang.size();i++){
            tongtien +=MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        tvTongTien.setText(decimalFormat.format(tongtien)+"  Đ");
    }

    private void CheckData() {
        if (MainActivity.manggiohang.size() <=0){
            giohangAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        }else {
            giohangAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void Actiontoolbar() {
        setSupportActionBar(toolbargiohang);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        lvgiohang=findViewById(R.id.lvgiohang);
        tvThongbao=findViewById(R.id.tvthongbao);
        tvTongTien=findViewById(R.id.tvtongtien);
        btnthanhtoan=findViewById(R.id.btnthanhtoangiohang);
        btntieptucmua=findViewById(R.id.btntieptucmuahang);
        toolbargiohang=findViewById(R.id.toolbargiohang);
        giohangAdapter=new GiohangAdapter(GioHang.this,MainActivity.manggiohang);
        lvgiohang.setAdapter(giohangAdapter);


    }
}