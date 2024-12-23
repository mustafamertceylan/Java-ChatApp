package com.example.chatdeneme2;
import test;
import sandıoasndoıa;
//dsf
//g


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

public class ChatClientGUI extends Application {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private PrintWriter out;
    private BufferedReader in;

    private TextArea messageArea;
    private TextField messageField;
    private TextField usernameField;
    private Button sendButton;
    private String userName;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Başlangıç arayüzü
        messageArea = new TextArea();
        messageArea.setEditable(false); // Mesajlar sadece okuma için olacak
        messageArea.setWrapText(true);  // Mesajlar satır sonlarına sarmalı

        usernameField = new TextField();
        usernameField.setPromptText("Enter your username...");

        sendButton = new Button("Send");
        sendButton.setDisable(true);  // Kullanıcı adı girilmeden mesaj gönderilemez
        sendButton.setOnAction(e -> sendMessage());  // Mesaj gönder butonu

        messageField = new TextField();
        messageField.setPromptText("Type a message...");
        messageField.setDisable(true);  // Kullanıcı adı girilmeden mesaj yazılamaz
        messageField.setOnAction(e -> sendMessage());  // Enter tuşuna basıldığında mesaj gönder

        Button setUsernameButton = new Button("Set Username");
        setUsernameButton.setOnAction(e -> setUsername());  // Kullanıcı adı belirle butonu

        VBox layout = new VBox(10, usernameField, setUsernameButton, messageArea, messageField, sendButton);
        layout.setAlignment(Pos.CENTER);  // Bileşenleri ortalar
        layout.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chat Client");
        primaryStage.show();

        // Sunucuya bağlan
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Gelen mesajları dinle
            new Thread(new IncomingMessages()).start();

        } catch (IOException e) {
            messageArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }

    private void setUsername() {
        String enteredUsername = usernameField.getText().trim();
        if (!enteredUsername.isEmpty()) {
            userName = enteredUsername;
            out.println(userName);  // Kullanıcı adı sunucuya gönderiliyor

            // Kullanıcı adı girildikten sonra diğer bileşenler aktif olacak
            usernameField.setDisable(true);
            sendButton.setDisable(false);
            messageField.setDisable(false);
            messageField.setPromptText("Type a message...");  // Mesaj yazılabilir hale gelir
        } else {
            messageArea.appendText("Username cannot be empty.\n");
        }
    }

    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            out.println(message);  // Mesajı sunucuya gönder
            messageField.clear();  // Mesaj yazma kutusunu temizle
        }
    }

    private class IncomingMessages implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    // Gelen mesajı TextArea'ya yazdır
                    messageArea.appendText(message + "\n");
                }
            } catch (IOException e) {
                messageArea.appendText("Error: " + e.getMessage() + "\n");
            }
        }
    }
}
