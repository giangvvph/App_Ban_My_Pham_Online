package com.example.duan1.actyvity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duan1.R;

public class Manhinhdangnhap extends AppCompatActivity {

    EditText edUser, edPass;
    CheckBox checkBox;
    Button btnOk;
    Intent intent;
    String stUser, stPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhdangnhap);

        startActivity(intent = new Intent(Manhinhdangnhap.this, MainActivity.class));
        Anhxa();
        SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        edUser.setText(preferences.getString("USERNAME", ""));
        edPass.setText(preferences.getString("PASSWORD", ""));
        checkBox.setChecked(preferences.getBoolean("REMEMBER", false));

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
    }

    private void Anhxa(){
        edUser = findViewById(R.id.id_name);
        edPass = findViewById(R.id.id_pass);
        btnOk = findViewById(R.id.btndangnhap);
        checkBox = findViewById(R.id.checkboxpass);
    }
 private void logIn(){

        stUser = edUser.getText().toString();
        stPass = edPass.getText().toString();
        if (stUser.isEmpty() || stPass.isEmpty()){
            Toast.makeText(getApplicationContext(), "Tên đăng nhập và mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
        }else {
            if (stUser.equals("admin") && stPass.equals("admin")){
                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                intent = new Intent(Manhinhdangnhap.this,MainActivity.class);
                intent.putExtra("user", stUser);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(getApplicationContext(), "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
 }
 private void remember(String u, String p, boolean status){
        SharedPreferences prefe = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefe.edit();
        if (!status){
            editor.clear();
        }else {
            editor.putString("USERNAME", u);
            editor.putString("PASSWORD", p);
            editor.putBoolean("REMEMBER", true);
        }
        editor.commit();
 }
}