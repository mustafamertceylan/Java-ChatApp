package com.example.chatdeneme3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginScreenController {
    private ChatClientGUI mainApp; // Ana uygulama referansı
    //public String username="Admin";
    //public String userPAsword="1234";
    @FXML
    private TextField Username;
    @FXML
    private TextField UserPasword;




    @FXML
    private void handleRegisterUserButton(ActionEvent event) {
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
    private void handleLogin(ActionEvent event) {
        User registeredUser=RegisterUserController.getUser();
        String username = Username.getText();
        String password = UserPasword.getText();
        if (registeredUser == null) {
            System.out.println("Henüz kayıtlı bir kullanıcı yok.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("Henüz kayıtlı bir kullanıcı yok. Lütfen önce kayıt olun.");
            alert.showAndWait();
            return;
        }
        if (username.equals(registeredUser.getUserName()) && password.equals(registeredUser.getUserPassword())) {
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
