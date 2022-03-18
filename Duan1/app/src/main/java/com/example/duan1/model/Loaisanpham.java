package com.example.duan1.model;

public class Loaisanpham {
    public int id;
    public String TenloaiSanpham;
    public String HinhanhSanpham;

    public Loaisanpham(int id, String tenloaiSanpham, String hinhanhSanpham) {
        this.id = id;
        TenloaiSanpham = tenloaiSanpham;
        HinhanhSanpham = hinhanhSanpham;
    }

    public Loaisanpham() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloaiSanpham() {
        return TenloaiSanpham;
    }

    public void setTenloaiSanpham(String tenloaiSanpham) {
        TenloaiSanpham = tenloaiSanpham;
    }

    public String getHinhanhSanpham() {
        return HinhanhSanpham;
    }

    public void setHinhanhSanpham(String hinhanhSanpham) {
        HinhanhSanpham = hinhanhSanpham;
    }
}
