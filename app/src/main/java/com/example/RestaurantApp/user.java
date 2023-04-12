package com.example.RestaurantApp;

public class user {
    private String tenTaiKhoan;
    private String hoTenNV;
    private String quyenHan;
    private String SDT;
    private String email;
    private String matKhau;
    private byte[] hinh;

    public user() {
    }

    public user(String tenTaiKhoan, String hoTenNV, String quyenHan, String SDT, String email, String matKhau, byte[] hinh) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.hoTenNV = hoTenNV;
        this.quyenHan = quyenHan;
        this.SDT = SDT;
        this.email = email;
        this.matKhau = matKhau;
        this.hinh = hinh;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getHoTenNV() {
        return hoTenNV;
    }

    public void setHoTenNV(String hoTenNV) {
        this.hoTenNV = hoTenNV;
    }

    public String getQuyenHan() {
        return quyenHan;
    }

    public void setQuyenHan(String quyenHan) {
        this.quyenHan = quyenHan;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }
}
