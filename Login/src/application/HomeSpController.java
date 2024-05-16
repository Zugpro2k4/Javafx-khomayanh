package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class HomeSpController implements Initializable {

	@FXML
	private ImageView doianh;
	
	@FXML
    private ImageView anhsp;
	
	@FXML
    private TextField masp1;

    @FXML
    private TextField tensp1;

    @FXML
    private TextField thuonghieu1;

    @FXML
    private TextField baohanh1;

    @FXML
    private TextField soluong1;

    @FXML
    private TextField giaban1;
    
    @FXML
    private TableView<Product> productListTV;

    @FXML
    private TableColumn<Product, String> masp;

    @FXML
    private TableColumn<Product, String> tensp;

    @FXML
    private TableColumn<Product, String> thuonghieu;

    @FXML
    private TableColumn<Product, String> baohanh;

    @FXML
    private TableColumn<Product, String> soluong; 

    @FXML
    private TableColumn<Product, String> giaban;

    @FXML
    private Label LoginedFullname;
    
    private ProductDAO um; 

    private User loginedUser;

    public User getLoginedUser() {
        return loginedUser;
    }
    
    //lấy dữ liệu ra bảng tableview
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        masp.setCellValueFactory(new PropertyValueFactory<>("masp"));
        tensp.setCellValueFactory(new PropertyValueFactory<>("tensp"));
        thuonghieu.setCellValueFactory(new PropertyValueFactory<>("thuonghieu"));
        baohanh.setCellValueFactory(new PropertyValueFactory<>("baohanh"));
        soluong.setCellValueFactory(new PropertyValueFactory<>("soluong"));
        giaban.setCellValueFactory(new PropertyValueFactory<>("giaban"));   
        productListTV.setItems(productList);
        loadProducts();
        
        productListTV.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                onClickRow();
            }
         });
    }
    
    //thêm sản phẩm
    @FXML
    void add(ActionEvent event) {
        try {
          
            String maSP = masp1.getText();
            String tenSP = tensp1.getText();
            String thuongHieu = thuonghieu1.getText();
            String baoHanh = baohanh1.getText();
            String soLuong = soluong1.getText();
            String giaBan = giaban1.getText();
            String imgPath = (String) anhsp.getUserData(); 
            Product newProduct = new Product(maSP, tenSP, thuongHieu, baoHanh, soLuong, giaBan, imgPath);
            ProductDAO dao = new ProductDAO();
            dao.addProduct(newProduct);
           
            productList.add(newProduct);
            clearFields(); 
        } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    //xoá sản phẩm
    @FXML
    void delete(ActionEvent event) {
        Product selectedProduct = productListTV.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận xoá");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn xoá sản phẩm này?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        ProductDAO dao = new ProductDAO();
                        dao.deleteProduct(selectedProduct.getMasp());
                        productList.remove(selectedProduct);
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    // sửa thông tin sản phẩm
    @FXML
    void update(ActionEvent event) {
        Product selectedProduct = productListTV.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                String tenSP = tensp1.getText();
                String thuongHieu = thuonghieu1.getText();
                String baoHanh = baohanh1.getText();
                String soLuong = soluong1.getText();
                String giaBan = giaban1.getText();
                String imgPath = (String) anhsp.getUserData(); 
                selectedProduct.setTensp(tenSP);
                selectedProduct.setThuonghieu(thuongHieu);
                selectedProduct.setBaohanh(baoHanh);
                selectedProduct.setSoluong(soLuong);
                selectedProduct.setGiaban(giaBan);
                selectedProduct.setImgPath(imgPath); 
                ProductDAO dao = new ProductDAO();
                dao.updateProduct(selectedProduct);
                int selectedIndex = productListTV.getSelectionModel().getSelectedIndex();
                productList.set(selectedIndex, selectedProduct);
                clearFields(); 
            } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    //đổi ảnh khác được lưu trong file img
    @FXML
    void doianh() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                String imgPath = selectedFile.toURI().toString();
                Image newImage = new Image(imgPath);
                anhsp.setImage(newImage);
                anhsp.setUserData(imgPath);
                Product selectedProduct = productListTV.getSelectionModel().getSelectedItem();
                if (selectedProduct != null) {
                    String maSP = selectedProduct.getMasp();
                    selectedProduct.setImgPath(imgPath);
                    ProductDAO dao = new ProductDAO();
                    dao.updateImagePath(maSP, imgPath);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    
    // chọn vào sản phẩm hiện lên ảnh vào thông tin
    @FXML
    public void onClickRow() {
        Product selectedProduct = productListTV.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            masp1.setText(selectedProduct.getMasp());
            tensp1.setText(selectedProduct.getTensp());
            thuonghieu1.setText(selectedProduct.getThuonghieu());
            baohanh1.setText(selectedProduct.getBaohanh());
            soluong1.setText(selectedProduct.getSoluong());
            giaban1.setText(selectedProduct.getGiaban());
            String imgPath = selectedProduct.getImgPath();
            if (imgPath != null) {
                Image image = new Image(imgPath);
                anhsp.setImage(image);
            } else {
                anhsp.setImage(null);
            }
        }
    }
    
    //tải lại dữ liệu lấy từ danh sách
    private void loadProducts() {
        try {
            ProductDAO dao = new ProductDAO();
            productList.addAll(dao.listAllProducts());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
  //xoá trắng các ô textField sau khi thêm
    private void clearFields() {
        masp1.clear();
        tensp1.clear();
        thuonghieu1.clear();
        baohanh1.clear();
        soluong1.clear();
        giaban1.clear();
    }
    //chức năng xoá trắng tự bấm
    @FXML
    void xoatrang(ActionEvent event) {
        clearFields();
    }
    
    //đi đến trang FB
    @FXML
    void tacgia(ActionEvent event) {
    	 String url = "https://www.facebook.com/jookoii/"; 
         try {
             Desktop.getDesktop().browse(new URI(url));
         } catch (IOException | URISyntaxException e) {
             e.printStackTrace();
         }
    }
    
    //lời chào tài khoản đăng nhập
    private ObservableList<Product> productList = FXCollections.observableArrayList();
    public void setLoginedUser(User user) {
        this.loginedUser = user;
        if (LoginedFullname != null && user != null) {
            LoginedFullname.setText("Xin chào " + user.getEmail()+"!");
        }
    }

    // thoát
    @FXML
    private void out(ActionEvent event) {
    	Platform.exit();
    }
}