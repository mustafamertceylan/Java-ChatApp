package com.example.chatdeneme3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class ChatClientGUI extends Application {
    private Stage primaryStage;
    // Uygulamanın ana penceresini temsil eden değişken.

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        // `start` metodu, JavaFX uygulaması başlatıldığında çağrılır.
        // Ana pencere referansı sınıfın `primaryStage` değişkenine atanır.

        showLoginScreen();
        // Uygulama başlarken giriş ekranını göster.
    }
    // Giriş ekranını yükleyen ve gösteren metot
    public void showLoginScreen() {
        try {
            // FXML dosyasını yüklemek için FXMLLoader oluşturulur
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/chatdeneme3/LoginScreen.fxml"));
            Scene scene = new Scene(loader.load());

            Image icon = new Image("Marmara_Üniversitesi_logo.png");
            // Uygulamanın penceresine bir simge (ikon) eklemek için resim yüklenir.
            primaryStage.getIcons().add(icon);

            primaryStage.setScene(scene);
            // Pencerenin sahnesi olarak giriş ekranı atanır.
            primaryStage.setTitle("Giriş");
            // Pencerenin başlığı ayarlanır.

            // FXML ile ilişkilendirilen kontrolcü (controller) alınır
            LoginScreenController controller = loader.getController();
            controller.setMainApp(this);
            // Ana uygulama referansı kontrolcüye verilir.

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // hata kontrolü yapılır. Eğer  hata alınırsa konsolda gösterilir.
        }
    }

    // Sohbet ekranını yükleyen ve gösteren metot
    public void showChatScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/chatdeneme3/ChatScreen.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Sohbet Ekranı");
            ChatScreenController controller = loader.getController();// FXML ile ilişkilendirilen kontrolcü (controller) alınır
            controller.setMainApp(this);
            // Ana uygulama referansı kontrolcüye verilir.
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
