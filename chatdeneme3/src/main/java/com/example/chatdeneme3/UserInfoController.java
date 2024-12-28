package com.example.chatdeneme3;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private String userName;
    private String userLastName;
    private String userPassword;

    @FXML
    private void initialize() {
        nickName = RegisterUserController.userNickName;
        userName = RegisterUserController.getRegisterUser().getUserName();
        userLastName = RegisterUserController.getRegisterUser().getUserLastName();
        userPassword = RegisterUserController.getRegisterUser().getUserPassword();


        nickNameField.setText(nickName);
        userNameField.setText(userName);
        userLastNameField.setText(userLastName);
        userPasswordField.setText(userPassword);
    }

    @FXML
    private void handleSaveInfo() {
        if (userNameField.getText().isEmpty() || userLastNameField.getText().isEmpty() || nickNameField.getText().isEmpty() || userPasswordField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen tüm alanları doldurun.");
            alert.showAndWait();
            return;
        }
        RegisterUserController.userNickName = nickNameField.getText();
        RegisterUserController.getRegisterUser().setUserName(userNameField.getText());
        RegisterUserController.getRegisterUser().setUserLastName(userLastNameField.getText());
        RegisterUserController.getRegisterUser().setUserPassword(userPasswordField.getText());

        ((Stage) userNameField.getScene().getWindow()).close();
    }
}