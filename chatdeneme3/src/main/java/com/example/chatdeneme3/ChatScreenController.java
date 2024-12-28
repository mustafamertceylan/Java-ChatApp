package com.example.chatdeneme3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import com.example.chatdeneme3.RegisterUserController;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

public class ChatScreenController {

    private ChatClientGUI mainApp; // Ana uygulama referansı

    @FXML
    private Button StatusOnline;
    private  Boolean isOnline=true;
    int isOnlineCount=1;
    @FXML
    private void handleStatusOnlineButton(){
        if(isOnline){
            if(isOnlineCount==1){
                out.println(RegisterUserController.userNickName);
                isOnlineCount+=1;
            }
            else{
                out.println("CONNECTED");
            }
            messageField.setDisable(false);
            sendButton.setDisable(false);
            messageField.setPromptText("Type a message...");  // Mesaj yazılabilir hale gelir
            StatusOnline.setText("Çevrim içi");
            isOnline = false;
        }
        else{
            out.println("DISCONNECT");
            messageField.setDisable(true);
            messageField.setPromptText("Çevrim dışı oldun...");
            StatusOnline.setText("Çevrim dışı");
            isOnline=true;

        }

    }
    @FXML
    private Button exit;

    @FXML
    private TextArea messageArea; // Mesajların görüntülendiği alan

    @FXML
    private TextField messageField; // Mesaj girişi için alan


    @FXML
    private Button sendButton; // Mesaj gönderme butonu

    private PrintWriter out;
    private BufferedReader in;

    @FXML
    private void initialize() {
        setupConnection(); // Sunucu bağlantısını kur
        messageField.setOnAction(event -> handleSendMessage());
    }

    @FXML
    private void handleSendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            out.println(message); // Mesajı sunucuya gönder
            messageField.clear(); // Giriş alanını temizle
        }

    }
    @FXML
    private void handleShowUserInfo() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserInfo.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Kullanıcı Bilgileri");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleExitButton(ActionEvent event) {
        System.exit(0);
    }

    private void setupConnection() {
        try {
            Socket socket = new Socket("localhost", 12345); // Sunucuya bağlan
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            sendButton.setDisable(true); // Bağlantı başarılıysa butonu aktif yap
            messageField.setDisable(true);
            // Gelen mesajları dinleyen bir thread başlat
            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        appendMessage(message);
                    }
                } catch (IOException e) {
                    appendMessage("Connection error: " + e.getMessage());
                }
            }).start();

        } catch (IOException e) {
            appendMessage("Could not connect to server: " + e.getMessage());
        }
    }

    private void appendMessage(String message) {
        messageArea.appendText(message + "\n"); // Mesajı ekranda göster
    }

    public void setMainApp(ChatClientGUI mainApp) {
        this.mainApp = mainApp;
    }

}
