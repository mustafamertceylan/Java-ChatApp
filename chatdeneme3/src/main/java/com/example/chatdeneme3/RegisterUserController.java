package com.example.chatdeneme3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterUserController {
    @FXML
    private TextField Name;
    @FXML
    private TextField LastName;
    @FXML
    private  TextField Username;
    @FXML
    private TextField UsernPassword;

    public static String userNickName="User";
    @FXML
    private void handleRegisterUserButton(ActionEvent event){
        userNickName=Username.getText();
        User user = new User(Name.getText(),LastName.getText(),UsernPassword.getText(),Username.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kayıt Başarılı");
        alert.setHeaderText(null);
        alert.setContentText("Kayıt işleminiz başarılı! Şimdi giriş yapabilirsiniz.");
        alert.showAndWait(); // Kullanıcı mesajı kapatana kadar bekle
        ((Stage) Name.getScene().getWindow()).close();
    }

}
