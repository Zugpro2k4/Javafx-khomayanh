package application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    TextField emailTF;
    @FXML
    TextField fullnameTF;
    @FXML
    Label msg;
    
    LoginModel loginModel = new LoginModel();
    
    @FXML
    public void onClickRegisterButton() {
        String email = emailTF.getText();
        String fullname = fullnameTF.getText();

        if (email.isEmpty() || fullname.isEmpty()) {
            msg.setText("Vui lòng điền đầy đủ thông tin.");
        } else {
            if (loginModel.checkExist(email)) {
                User user = loginModel.getUser(email);
                if (user.getfullname().equals(fullname)) {
                    msg.setText("Đăng nhập thành công");
                    try {
                        goHomeScreen(user);
                        emailTF.getScene().getWindow().hide();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    msg.setText("Sai thông tin đăng nhập.");
                }
            } else {
                msg.setText("Sai thông tin đăng nhập.");
            }
        }
    }

    
    @FXML
    private void goblog(ActionEvent event) {
        String url = "https://www.facebook.com/jookoii/"; 
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void goHomeScreen(User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomeSP.fxml"));
        Parent root = fxmlLoader.load();
        HomeSpController controller = fxmlLoader.getController();
        controller.setLoginedUser(user);
        fxmlLoader.setController(controller);
        Stage homeStage = new Stage();
        homeStage.setTitle("Kho quản lý");
        homeStage.setScene(new Scene(root));
        homeStage.show();
    }
}
