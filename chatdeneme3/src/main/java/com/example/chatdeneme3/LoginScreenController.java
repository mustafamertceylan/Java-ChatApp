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
    private ChatClientGUI mainApp;
    // Ana uygulamayı temsil eden `ChatClientGUI` referansı.
    // Bu referans, uygulamanın diğer ekranlarına geçişi sağlar.

    @FXML
    private TextField Username;
    @FXML
    private TextField UserPasword;
    @FXML
    private void handleRegisterUserButton() {
        // "Kayıt Ol" butonuna tıklandığında çalışır. Yeni kullanıcı kaydı için ekran açar.
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
        // "Giriş Yap" butonuna tıklandığında çalışır.
        User registeredUser = RegisterUserController.getRegisterUser();
        // Kayıtlı kullanıcı bilgilerini alır.
        String usernamefield = Username.getText();
        String passwordfield = UserPasword.getText();
        if (registeredUser == null) {
            // Eğer kayıtlı kullanıcı yoksa hata mesajı göster.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("Henüz kayıtlı bir kullanıcı yok. Lütfen önce kayıt olun.");
            alert.showAndWait();
            return;
        }

        if (usernamefield.equals(registeredUser.getNickName()) && passwordfield.equals(registeredUser.getUserPassword())) {
            // Kullanıcı adı ve şifre doğruysa:
            try {
                mainApp.showChatScreen(); // Sohbet ekranına geçiş yap.
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Eğer giriş bilgileri hatalıysa:
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hatalı Giriş");
            alert.setHeaderText(null);
            alert.setContentText("Kullanıcı adı veya şifre hatalı. Lütfen tekrar deneyin!");
            alert.showAndWait();
        }
    }

    // Ana uygulama referansını ayarlayan yöntem.
    public void setMainApp(ChatClientGUI mainApp) {
        this.mainApp = mainApp;
    }
}

