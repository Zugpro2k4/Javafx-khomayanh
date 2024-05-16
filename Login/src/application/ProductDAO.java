package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class ProductDAO {

    private String jdbcURL = "jdbc:ucanaccess://C:/Users/ThinkPad/OneDrive/Desktop/Login/lib/QLNS.accdb";
    private String dbUser = "";
    private String dbPassword = "";

    
    //lấy dữ liệu từ bảng ra danh sách
    public List<Product> listAllProducts() throws SQLException, ClassNotFoundException {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            String sql = "SELECT * FROM tblsp";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String masp = resultSet.getString("masp");
                String tensp = resultSet.getString("tensp");
                String thuonghieu = resultSet.getString("thuonghieu");
                String baohanh = resultSet.getString("baohanh");
                String soluong = resultSet.getString("soluong");
                String giaban = resultSet.getString("giaban");
                String imgPath = resultSet.getString("img_path"); 

                if (imgPath != null) {
                    Product product = new Product(masp, tensp, thuonghieu, baohanh, soluong, giaban, imgPath);
                    products.add(product);
                } else {
                    imgPath = "đường dẫn mặc định"; 
                    Product product = new Product(masp, tensp, thuonghieu, baohanh, soluong, giaban, imgPath);
                    products.add(product);
                }
            }
        }

        return products;
    }
    
    //thêm sản phẩm
    public void addProduct(Product product) throws SQLException, ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            String sql = "INSERT INTO tblsp (masp, tensp, thuonghieu, baohanh, soluong, giaban, img_path) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, product.getMasp());
                statement.setString(2, product.getTensp());
                statement.setString(3, product.getThuonghieu());
                statement.setString(4, product.getBaohanh());
                statement.setString(5, product.getSoluong());
                statement.setString(6, product.getGiaban());
                if (product.getImgPath() != null) {
                    statement.setString(7, product.getImgPath());
                } else {
                    statement.setString(7, "đường dẫn mặc định"); 
                }
                statement.executeUpdate();
            }
        }
    }
    
    //xoá dữ liệu sản phẩm khỏi bảng
    public void deleteProduct(String masp) throws SQLException, ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            String sql = "DELETE FROM tblsp WHERE masp=?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, masp);
                statement.executeUpdate();
            }
        }
    }

    //cập nhập sản phẩm
    public void updateProduct(Product product) throws SQLException, ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            String sql = "UPDATE tblsp SET tensp=?, thuonghieu=?, baohanh=?, soluong=?, giaban=?, img_path=? WHERE masp=?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, product.getTensp());
                statement.setString(2, product.getThuonghieu());
                statement.setString(3, product.getBaohanh());
                statement.setString(4, product.getSoluong());
                statement.setString(5, product.getGiaban());
                
                if (product.getImgPath() != null) {
                    statement.setString(6, product.getImgPath());
                } else {
                    statement.setString(6, "đường dẫn mặc định"); 
                }
                
                statement.setString(7, product.getMasp());
                statement.executeUpdate();
            }
        }
    }

    //cập nhập link ảnh vào bảng tblsp
    public void updateImagePath(String maSP, String imgPath) throws SQLException {
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            String query = "UPDATE tblsp SET img_path = ? WHERE masp = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                
            	if (imgPath != null) {
                    statement.setString(1, imgPath);
                } else {
                    statement.setString(1, "đường dẫn mặc định"); 
                }
                statement.setString(2, maSP);
                statement.executeUpdate();
            }
        }
    }
    
    //thông báo lấy dữ liệu
    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        System.out.println("Lấy dữ liệu thành công! ooh yêhehh");
    }
}
