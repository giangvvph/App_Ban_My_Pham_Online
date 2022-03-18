package com.example.duan1.actyvity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan1.R;
import com.example.duan1.adapter.SonAdapter;
import com.example.duan1.model.Giohang;
import com.example.duan1.model.SanPham;
import com.example.duan1.ultil.Checkconection;
import com.example.duan1.ultil.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SonAcityvity extends AppCompatActivity {
    Toolbar toolbarson;
    ListView lvSon;
    SonAdapter sonAdapter;
    ArrayList<SanPham>arrayListSon;
    int Iddt=0;
    int page=1;
    int id =0;
    String son="";
    int giaSon=0;
    String moTa="";
    String hinhanhSon="";
    int Ids=0;
    View footerview;
    boolean isloading=false;
    mHanler mHanler;
    boolean limitdata=false;


    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_son_acityvity);
        Anhxa();
        if (Checkconection .haveNetworkConnection(getApplicationContext())) {
            GetIdloaisanpham();
            ActionToolbar();
            Getdata(page);
            Loadmoredat();
        }
        else {
            Checkconection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra internet");
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.menugiohang:
                Intent intent=new Intent(getApplicationContext(),GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void Loadmoredat() {
        lvSon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(SonAcityvity.this,ChitietSanphamActivity.class);
                intent.putExtra("thongtinsanpham",arrayListSon.get(position));
                startActivity(intent);
            }
        });
        lvSon.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem +visibleItemCount==totalItemCount&& totalItemCount !=0 &&isloading==false && limitdata==false){
                    isloading= true;
                    thresData thresData=new thresData();
                    thresData.start();


                }
            }
        });
    }

    private void Getdata(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan= Sever.duongdanson+String.valueOf(Page);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response!=null && response.length() !=2){
                    lvSon.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        final int numberOfItemsInResp = jsonArray.length();
                        for (int i =0;i<numberOfItemsInResp;i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                          //  JSONObject jsonObject=jsonArray.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            son=jsonObject.getString("tensanpham");
                            giaSon=jsonObject.getInt("giasanpham");
                            hinhanhSon=jsonObject.getString("hinhanhsanpham");
                            moTa=jsonObject.getString("motasanpham");

                            Ids=jsonObject.getInt("idsanpham");
                            arrayListSon.add(new SanPham(id,son,giaSon,hinhanhSon,moTa,Ids));
                            sonAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitdata=true;
                    lvSon.removeFooterView(footerview);
                    Checkconection.ShowToast_Short(getApplicationContext(),"Đã hết dữ liệu");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String ,String > param=new HashMap<String, String>();
                param.put("idsanpham",String.valueOf(Iddt));

                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void ActionToolbar() {
        setSupportActionBar(toolbarson);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarson.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void GetIdloaisanpham() {
        Iddt=getIntent().getIntExtra("idloaisp",-1);
        Log.d("giatriloaisanpham",Iddt+"");
    }

    private void Anhxa() {
        toolbarson=findViewById(R.id.toolbarson);
        lvSon=findViewById(R.id.lvson);
        arrayListSon=new ArrayList<>();
        sonAdapter=new SonAdapter(getApplicationContext(),arrayListSon);
        lvSon.setAdapter(sonAdapter);
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview=inflater.inflate(R.layout.processbar,null);
        mHanler=new mHanler();
    }
    public class mHanler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvSon.addFooterView(footerview);
                    break;
                case 1:
                    Getdata(++page);
                    isloading=false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class thresData extends Thread{
        @Override
        public void run() {
            mHanler.sendEmptyMessage(0);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message=mHanler.obtainMessage(1);
            mHanler.sendMessage(message);
            super.run();
        }
    }
}