package com.example.chatdeneme3;


import ChatServer.ChatServer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ChatServer.ChatServer.ClientHandler;
//Kullanıcı bilgilerinin gösterildiği FXML dosyasının controllerı
public class UserInfoController {
    @FXML
    private TextField nickNameField;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField userLastNameField;
    @FXML
    private TextField userPasswordField;

    private String nickName;
    private String nickname1;
    private String userName;
    private String userLastName;
    private String userPassword;

    @FXML//Oluşturulan user nesnesinden bilgiler alınır ve ekrandaki yerlerine yazdırılır
    private void initialize() {
        nickName = RegisterUserController.getRegisterUser().getNickName();
        userName = RegisterUserController.getRegisterUser().getUserName();
        userLastName = RegisterUserController.getRegisterUser().getUserLastName();
        userPassword = RegisterUserController.getRegisterUser().getUserPassword();

        nickname1=nickName;//Kullanıcının kullanıcı ismini değiştirmesi durumunda ilk kullanıcı ismini saklar

        nickNameField.setText(nickName);
        userNameField.setText(userName);
        userLastNameField.setText(userLastName);
        userPasswordField.setText(userPassword);
    }

    @FXML//Eğer kullanıcı bilgilerini değiştirirse yeni bilgileri user nesnesine kaydeder
    private void handleSaveInfo() {
        // İlgili alanları silip boş bırakıması durumunda hata mesajı bastırır
        if (userNameField.getText().isEmpty() || userLastNameField.getText().isEmpty()
                || nickNameField.getText().isEmpty() || userPasswordField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen tüm alanları doldurun.");
            alert.showAndWait();
            return;
        }

        // Kullanıcı bilgilerini güncelle

        RegisterUserController.getRegisterUser().setUserName(userNameField.getText());
        RegisterUserController.getRegisterUser().setUserLastName(userLastNameField.getText());
        RegisterUserController.getRegisterUser().setUserPassword(userPasswordField.getText());

        // Eski ve yeni kullanıcı adlarını kontrol et
        String oldNickname = nickname1;
        String newNickname = nickNameField.getText();

        if (!oldNickname.equals(newNickname)) {
            boolean result = ChatServer.ClientHandler.updateUserName(oldNickname, newNickname);

            if (result) {
                // Kullanıcı adı güncellenemedi
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText(null);
                alert.setContentText("Kullanıcı adı güncellenemedi. Lütfen farklı bir ad deneyin.");
                alert.showAndWait();
            } else {
                // Kullanıcı adı başarıyla güncellendi
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Başarılı");
                alert.setHeaderText(null);
                alert.setContentText("Kullanıcı adı başarıyla güncellendi!");
                alert.showAndWait();
                RegisterUserController.getRegisterUser().setNickName(nickNameField.getText());

                System.out.println("Kullanıcı adı güncellendi: " + oldNickname + " -> " + newNickname);
            }
        }

        // Pencereyi kapat
        ((Stage) userNameField.getScene().getWindow()).close();
    }

}