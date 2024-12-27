package com.example.chatdeneme3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.PrintWriter;

public class ChatClientGUI extends Application {
    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLoginScreen(); // Uygulama başlarken Login ekranını göster
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/chatdeneme3/LoginScreen.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login Screen");

            // Controller ile ilişki kur ve ana uygulamayı bağla
            LoginScreenController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showChatScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/chatdeneme3/ChatScreen.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Chat Screen");

            // ChatScreenController ile ilişki kur
            ChatScreenController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
