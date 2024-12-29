package com.example.chatdeneme3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

public class ChatScreenController {
    private ChatClientGUI mainApp;
    // Ana uygulama referansını tutar. `ChatClientGUI` ile ilişkilendirilir.
    @FXML
    private Button StatusOnline;
    // Kullanıcının çevrim içi/çevrim dışı durumunu değiştiren buton.
    private Boolean isOnline = true;
    // Kullanıcının çevrim içi mi yoksa çevrim dışı mı olduğunu izler.
    int isOnlineCount = 1;
    @FXML//Kullanıcının butona basması ile aktiflik durumunu değiştiren buton kodu
    private void handleStatusOnlineButton() {
        if (isOnline) {
            if (isOnlineCount == 1) {
                out.println(RegisterUserController.userNickName);
                isOnlineCount += 1;
            } else {
                out.println("CONNECTED");
            }
            messageField.setDisable(false);// Mesaj giriş alanı aktif edilir.
            sendButton.setDisable(false);// Gönder butonu aktif edilir.
            messageField.setPromptText("Type a message...");// Kullanıcıya mesaj yazması gerektiğini belirten metin gösterilir.
            StatusOnline.setText("Çevrim içi");
            isOnline = false;
        } else {
            out.println("DISCONNECT");
            messageField.setDisable(true);// Mesaj giriş alanı devre dışı bırakılır.
            messageField.setPromptText("Çevrim dışı oldun...");
            StatusOnline.setText("Çevrim dışı");// Buton metni değiştirilir.
            isOnline = true;
        }
    }

    @FXML
    private Button exit;
    @FXML
    private TextArea messageArea;
    @FXML
    private TextField messageField;
    @FXML
    private Button sendButton;
    private PrintWriter out;// Mesajları sunucuya göndermek için kullanılan çıkış akışı
    private BufferedReader in;// Sunucudan gelen mesajları okumak için kullanılan giriş akışı.
    @FXML
    private void initialize() {
        setupConnection();// Sunucu ile bağlantı kurulmasını sağlar.
        messageField.setOnAction(event -> handleSendMessage());// Enter tuşuna basıldığında mesaj göndermek için `handleSendMessage` çağrılır.
    }

    @FXML
    private void handleSendMessage() {
        // Kullanıcının yazdığı mesajı sunucuya gönderir.
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            // Mesaj boş değilse işleme alınır.
            out.println(message);
            messageField.clear();
        }
    }

    @FXML
    private void handleShowUserInfo() {
        // Kullanıcı bilgilerini gösteren yeni bir pencere açar.
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
        // Çıkış butonuna basıldığında çalışır.
        ((Stage) messageField.getScene().getWindow()).close();
        // Mevcut pencereyi kapatır.
        mainApp.showLoginScreen();
        // Giriş ekranını yeniden gösterir.
    }

    private void setupConnection() {
        // Sunucu ile bağlantı kurar ve mesajları dinler.
        try {
            Socket socket = new Socket("localhost", 12345);
            // Sunucuya bağlanmak için soket oluşturulur.
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            sendButton.setDisable(true);// Gönder butonu başlangıçta devre dışıdır.
            messageField.setDisable(true);// Mesaj giriş alanı başlangıçta devre dışıdır.
            // Gelen mesajları dinleyen bir thread başlatılır.
            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        // Mesaj geldikçe okur ve ekrana yazar.
                        appendMessage(message);
                    }
                } catch (IOException e) {
                    appendMessage("Connection error: " + e.getMessage());
                }
            }).start();

        } catch (IOException e) {
            appendMessage("Could not connect to server: " + e.getMessage());
            // Bağlantı kurulamazsa hata mesajını gösterir.
        }
    }

    private void appendMessage(String message) {
        // Gelen mesajları mesaj alanına ekler ve imleci alt satıra atar
        messageArea.appendText(message + "\n");
    }

    public void setMainApp(ChatClientGUI mainApp) {
        // Ana uygulama referansını ayarlar.
        this.mainApp = mainApp;
    }
}
