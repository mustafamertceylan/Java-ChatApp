package com.example.chatdeneme3;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    private static User user;
    public static User getRegisterUser(){
        return user;
    }

    @FXML
    private void handleRegisterUserButton(){
        if (Name.getText().isEmpty() || LastName.getText().isEmpty() || Username.getText().isEmpty() || UsernPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen tüm alanları doldurun.");
            alert.showAndWait();
            return;
        }
        userNickName=Username.getText();
        user = new User(Name.getText(),LastName.getText(),UsernPassword.getText(),Username.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kayıt Başarılı");
        alert.setHeaderText(null);
        alert.setContentText("Kayıt işleminiz başarılı! Şimdi giriş yapabilirsiniz.");
        alert.showAndWait(); // Kullanıcı mesajı kapatana kadar bekle
        ((Stage) Name.getScene().getWindow()).close();
    }

}
