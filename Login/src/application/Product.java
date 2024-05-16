package application;

import javafx.scene.image.Image;

public class Product {
    private String masp;
    private String tensp;
    private String thuonghieu;
    private String baohanh;
    private String soluong;
    private String giaban;
    private String imgPath;

    public Product(String masp, String tensp, String thuonghieu, String baohanh, String soluong, String giaban, String imgPath) {
        this.masp = masp;
        this.tensp = tensp;
        this.thuonghieu = thuonghieu;
        this.baohanh = baohanh;
        this.soluong = soluong;
        this.giaban = giaban;
        this.imgPath = imgPath;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getThuonghieu() {
        return thuonghieu;
    }

    public void setThuonghieu(String thuonghieu) {
        this.thuonghieu = thuonghieu;
    }

    public String getBaohanh() {
        return baohanh;
    }

    public void setBaohanh(String baohanh) {
        this.baohanh = baohanh;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getGiaban() {
        return giaban;
    }

    public void setGiaban(String giaban) {
        this.giaban = giaban;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
