package com.example.chatdeneme3;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
//Kayıt olma ekranının controller sınıfı
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
    //Kullanıcı nickName kısmınınboş olması durumunda default olarak kullanıcı adı atanır

    //Tek bir User nesnesi oluşturulmasını sağlar
    private static User user;
    public static User getRegisterUser(){
        return user;
    }

    @FXML//Girilen bilgilerin user nesnesine kaydedilmesini sağlayan buton metodu
    private void handleRegisterUserButton(){
        if (Name.getText().isEmpty() || LastName.getText().isEmpty()  || UsernPassword.getText().isEmpty()) {
            //Gerekli alanların boş olmaması gerektiini ifade eder
            //Eğer boş alan varsa hata döndürür
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen tüm alanları doldurun.");
            alert.showAndWait();
            return;
        }
        userNickName=Username.getText();
        user = new User(Name.getText(),LastName.getText(),UsernPassword.getText(),Username.getText());
        //Girilen verileri user nesnesine atar
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kayıt Başarılı");
        alert.setHeaderText(null);
        alert.setContentText("Kayıt işleminiz başarılı! Şimdi giriş yapabilirsiniz.");
        alert.showAndWait(); // Kullanıcı mesajı kapatana kadar bekle
        ((Stage) Name.getScene().getWindow()).close();//Kayıt olma ekranını kapatır
    }

}
