package com.example.duan1.actyvity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan1.R;
import com.example.duan1.adapter.LoaisanphamAdapter;
import com.example.duan1.adapter.SanPhamAdapter;
import com.example.duan1.model.Giohang;
import com.example.duan1.model.Loaisanpham;
import com.example.duan1.model.SanPham;
import com.example.duan1.ultil.Checkconection;
import com.example.duan1.ultil.Sever;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewManhinhchinh;
    ArrayList<Loaisanpham> mangloaisanpham;
    LoaisanphamAdapter loaisanphamAdapter;
    int id =0;
    String  tenloaisp="";
    String hinhanhsp="";
    ArrayList<SanPham>mangsanpham;
    SanPhamAdapter sanPhamAdapter;

    public static ArrayList<Giohang> manggiohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        if (Checkconection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewLiper();
            GetDulieuSanpham();
            GetDulieuSanphamNew();
            CatchItemListview();
        }
        else {
            Checkconection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối");
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

    private void CatchItemListview() {
        listViewManhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if (Checkconection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this ,MainActivity.class);
                           // intent.putExtra("thongtinsanpham",mangsanpham.get(position));
                            startActivity(intent);
                        }else {
                            Checkconection.ShowToast_Short(getApplicationContext(),"Bạn Kiểm Tra Lại Kết Nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (Checkconection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this ,SonAcityvity.class);
                            intent.putExtra("idloaisanpham",mangloaisanpham.get(position).getId());
                            startActivity(intent);
                        }else {
                            Checkconection.ShowToast_Short(getApplicationContext(),"Bạn Kiểm Tra Lại Kết Nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (Checkconection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this ,SuaRuaMatActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisanpham.get(position).getId());
                            startActivity(intent);
                        }else {
                            Checkconection.ShowToast_Short(getApplicationContext(),"Bạn Kiểm Tra Lại Kết Nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (Checkconection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this ,LienHeActyvity.class);
                            startActivity(intent);
                        }else {
                            Checkconection.ShowToast_Short(getApplicationContext(),"Bạn Kiểm Tra Lại Kết Nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (Checkconection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this ,ThongTinActivity.class);
                            startActivity(intent);
                        }else {
                            Checkconection.ShowToast_Short(getApplicationContext(),"Bạn Kiểm Tra Lại Kết Nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDulieuSanphamNew() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Sever.duongdansanphamNew, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            if (response !=null){

                for (int i=0;i<response.length();i++){
                    try {
                        int ID=0;
                        String tensanpham="";
                        Integer Giasanpham=0;
                        String Hinhanhsanpham="";
                        String Motasanpham="";
                        int IDsanpham=0;
                        JSONObject jsonObject=response.getJSONObject(i);
                        ID =jsonObject.getInt( "id");
                        tensanpham=jsonObject.getString("tensanpham");
                        Giasanpham=jsonObject.getInt("giasanpham");
                        Hinhanhsanpham=jsonObject.getString("hinhanhsanpham");
                        Motasanpham=jsonObject.getString("motasanpham");
                        IDsanpham=jsonObject.getInt("idsanpham");
                        mangsanpham.add(new SanPham(ID,tensanpham,Giasanpham,Hinhanhsanpham,Motasanpham,IDsanpham));
                        sanPhamAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDulieuSanpham() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Sever.duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            if (response != null){
            for (int i=0;i <response.length();i++){
                try {
                    JSONObject jsonObject=response.getJSONObject(i);
                    id=jsonObject.getInt("id");
                    tenloaisp=jsonObject.getString("tenloaisanpham");
                    hinhanhsp=jsonObject.getString("hinhanhsanpham");
                    mangloaisanpham.add(new Loaisanpham(id,tenloaisp,hinhanhsp));
                    loaisanphamAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            mangloaisanpham.add(3,new Loaisanpham(0,"Liên Hệ","https://img4.thuthuatphanmem.vn/uploads/2020/12/26/anh-cuon-sach-mo-ra-cuc-dep_051456080.jpg"));
            mangloaisanpham.add(4,new Loaisanpham(0,"Thông Tin","https://img4.thuthuatphanmem.vn/uploads/2020/12/26/anh-cuon-sach-mo-ra-cuc-dep_051456080.jpg"));
            }

            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                 Checkconection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        }
                );
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewLiper() {
        ArrayList<String>mangquangcao=new ArrayList<>();
        mangquangcao.add("https://lh3.googleusercontent.com/zkyDy-9tQ3EAgZHcGjRmDsD4rEPlhNi9j_RGozFZBPdK85nT2XyYKDSxNqPG7uI8JEPq_cfVIOpIHZxZj9B0xCQaTf9cqtdxitDInUWSORXT121rjvBa5isTULdRDP-Zg9XXR8XxOonv5Cjjuk8GbHrrPtJur3dKRtDe44ldAy1qRL31d9T9puYi9XDkA8KcZ0WNLVkM023PYWIhiKGXbPAOU4vwi65mGlUAj8LYQtXN5KodKJkkgjkWHEBTMu-Ht-obWRmY9VYUeLZyhiSKETxR9upfFuRKTgHDCwy5_2vVDBBqIpqrpqsGjz0u9UNvH3fbLu5gkL5GnT8DoWpbSIs9OEAVGfMO8PNjTnWV33P5WlQj0wGsa1e19sUT1QFjkNDLDnXlwmjpfRC-jxBulpWCCnV3PRAtoJk88VeOhB_vKARUNau0KpHUk8FmmwRiM73zVOL_kZ_qX_631aDC2awFYw8WVKpyyynIkScOdWdiIHKptGUgYzc1RIf2E8R_Rbt7YdSjywySwkq4SksXbNYNiIgg70_OOz5cU2tss6Zj0TNH0BVyw57k_O7BkWv0_CXYSeLc4XRllzb931m_QKjVOEpWSajRDy5XhG8Coyo8haiArO5RXye4vG8_0cFJX0Mw9wN9BcFJ1VURsgQkhgjxmIJ5JTdgXi8kW7GsNP1XWqQYwp2CNik_OBCr4_P2U1c7LFwIvFFkPW09A3PWLbcl=s453-no?authuser=1");
        mangquangcao.add("https://lh3.googleusercontent.com/TzLVLqUTdu0AieAfM5_tVSOpNK5e9C11-E-dk5xL1MrrxFJnK91RZ_oynL4YWYw0I_ZBfL5lSlEqgy0Gh9ibT_TraxHLKZfFblFIiwkSCeKqqXJbYNojF892CNJtgvaR4BPgQr4POWRAgqeOrSnZOGLtcmcp44wVhiSEConiAp5A5mQjm3telNgnQ3wirmAgdOdLDyBNVVoJcm2ZkViXn4QWbnrn473FZ80-oMMQjbZtGMz3xKEu12TGmeASkHIzRC9Ssnek6ZnIeqF-gooiebaEaSsLMBqToq3e7ggoA7UVqaNzw304dl4t2oToj9CzmAKirQiHFSi5Qx5OOzIu85ZSp4ojPHsnnDhJjvKYCi8BERinG9cyWA6jnHk_0qB1koLuOahqHSws8Mamd2mKbvsMymXTxgr6D-N3G2ebrKiyibkX2cqeu2P3TJQQOINjhlT4vWTcVmqaONvp6GGL5OL4YLH7MlY_7G36oNMwi2fDftlakmOBKA4A57M8M7HvZnbJgDM9vmx8qGvoo-vb0wNlDUBeuKay1DvoGplqZIMs-rHiic3t9H3mytsxMxsJouhwl-L0HK38HXGT-CVe2cd_V8vAsQ6BOFJgYEY2nXRiJmFwvSvA0vuwr-TCzc2wbsqylmZ97dXuHa4dBJwZpMVWfnQdxj-fSc2OFPLOEa5bK87FT1-ectXfVr0DTnLykxT4Q3xOcxAQbyzH48jni4J0=s453-no?authuser=1");
        mangquangcao.add("https://lh3.googleusercontent.com/IMcEU8-ZiOQqd8PmAZjLW5l5z_8Zg5OjbMR_rLWvKef978rQMcMs09SB4vcwhb2L9fvWoEc4htdgUS60sgee4jRXNoCXvzC2oza_qqOIfSV4bzgE9-B9wT-uLZHUc01tuAXKpivWVFj6JwagJkNfAL0iFIY9TsDGr3dz1mbI1F3PFyX29RjfV4gBLTualNrk4XLaPUlMyrXMXaDPFeiLGiMCurZSnMlwVobW0GW6gFwY0Ol5I-MmJ5hOxRhGlNVikBQtVmWF5ggoUAi24pFc5eZl7jKCz9H41njtgzv_BRSxsmqWlJSGpRpuPIPpQvti74kfnY3cMmzIGpq4wD6Qigj8P1BnfVVTjLNcLLzVthU3uRjvplNa-5z6qv3bvVRtfaPJKacQY71joykKPBoj5MXWJ7mHFnKXUPenTRFDIVW0-ZeKGrMBaFgn6bWf2Cr-rgr_5a8IL49e1TEi2DZ4JqNF_EPBRa5WvK31YPx6MFaAyIGrCrCm19rMZC5MemTns3hjhpgNgBujCiHv4AOKbjXu84HEsTwbxBCqk1US61wuEZPs85ccj7tmuVVpmmQj9jnK-6Pwb14S3_dgeTGUJZG2RwyBuGwV8XiprhxupiLoaDhIiNgT2YHDixJOox395ny-6oeNBTRvEneDcYrKepgzBqgDHPuBZ4eM0iQd-uRkJ3j6WJkoxf2-lm3WQSW6mMqaMm8R5OlvWPpTRobCeCBN=s453-no?authuser=1");
       mangquangcao.add("https://lh3.googleusercontent.com/bQLrtsNl1dDfQlJfFqBNF-kZMsT02t99s7mYzQ_-3Gr6urtKNNLifKRzcr-2w5rQZat8o76HbwuMXuOxbTd-eSdoFon-89M9k1m4s4QZ1E_FzS0U6xW8Iyv0tZWAA9VvUp-vik8C0MKFaBhqoYrM8sUsm7wJNY_FL_KLCg0EhPvjINdpt41SqtOYZ4_-kVKUXkgKFhm_gcPIrl-UcLYMwPGqFvzmQoRgPEWWand-Zuu4DB0EHOTX4_--uofFS8GoM-mDTrEzqdtV-N4bSyTP8XfApUkphAcQ7wyk4KtqeZ-YBKuWGqnCISB5ibdn1U0vHpoYMvrbSQN1WQxSkg8hUZi8yRGOI9pKKCwwNDqwB7RNzO2xOtEbtPNyWopsyku1wDMZglvaMx6ebUauotOTzDBWWe6l6r8wkGgCaQCAxLfWPFZQ9UqmfashZVv93uXTv9mgGYGLnIi9IV27EZkmm_rW6y9wvk6uiYx3a-okd9ICO5f_ELTwjd6Ng-I-_-_vF7N8NvNH8CeiLUE1k1uW11EhblNssUTVb0Gj0nVvPqxMHAqYhUFBmb2s9ZixzKSM9okOKR_3L7oA82KUOfzTNXnPEfpVwe8iHAjYPX8359huDKmACdOtLEEC9c1Wd-VZJaXBYqd7UR437-Cy4zJEIeE3FyCu51TPiQP4iLBwCMDtS7iFBmWnn7VLpTRBfg9ALWKQoBBLIdWlSNQ4Jy4YV4lq=s453-no?authuser=1");
        for (int i=0;i<mangquangcao.size();i++){
            ImageView imageView=new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);

        }
        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setInAnimation(animation_slide_out);


    }

    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
    }
    private void AnhXa(){
        drawerLayout=findViewById(R.id.drawwelayout);
        toolbar=findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper=findViewById(R.id.viewflipper);
        recyclerViewmanhinhchinh=findViewById(R.id.rycyclerview);
        navigationView=findViewById(R.id.navigation);
        listViewManhinhchinh=findViewById(R.id.lvmanhinhchinh);
        mangloaisanpham=new ArrayList<>();
        mangloaisanpham.add(0,new Loaisanpham(0,"Trang Chính","https://img.thuthuattinhoc.vn/uploads/2019/01/13/100-hinh-anh-de-thuong-nhat_104522227.jpg"));
        loaisanphamAdapter=new LoaisanphamAdapter(getApplicationContext(), mangloaisanpham);
        listViewManhinhchinh.setAdapter(loaisanphamAdapter);
        mangsanpham=new ArrayList<>();
        sanPhamAdapter=new SanPhamAdapter(getApplicationContext(),mangsanpham);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewmanhinhchinh.setAdapter(sanPhamAdapter);
        if (manggiohang !=null){

        }else {
            manggiohang=new ArrayList<>();
        }
    }
}