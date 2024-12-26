package com.example.chatdeneme3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginScreenController {
    private ChatClientGUI mainApp; // Ana uygulama referansı

    @FXML
    private TextField Username;
    @FXML
    private TextField UserPasword;

    @FXML
    private void handleRegisterUserButton() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegisterUser.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Kayıt Ol");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleLogin() {
        User registeredUser=RegisterUserController.getRegisterUser();
        String usernamefield = Username.getText();
        String passwordfield = UserPasword.getText();

        if (registeredUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("Henüz kayıtlı bir kullanıcı yok. Lütfen önce kayıt olun.");
            alert.showAndWait();
            return;
        }

        if (usernamefield.equals(registeredUser.getUserName()) && passwordfield.equals(registeredUser.getUserPassword())) {
            try {
                mainApp.showChatScreen(); // Başarılı giriş sonrası Chat ekranına geç
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Hatalı giriş mesajı
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hatalı Giriş");
            alert.setHeaderText(null);
            alert.setContentText("Kullanıcı adı veya şifre hatalı. Lütfen tekrar deneyin!");
            alert.showAndWait();
        }
    }

    // Ana uygulama referansını ayarlama
    public void setMainApp(ChatClientGUI mainApp) {
        this.mainApp = mainApp;
    }
}
