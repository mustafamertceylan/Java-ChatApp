package ChatServer;

import java.io.*;
import java.net.*;

public class ChatClient {
    // Sunucu IP adresi ve port numarası
    private static final String SERVER_IP = "192.168.1.36";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT); // Sunucuya bağlanmak için bir socket oluşturulur
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Sunucudan veri almak için kullanılır
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Sunucuya veri göndermek için kullanılır
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) { // Kullanıcıdan konsol girişini okumak için kullanılır


            System.out.println("Bağlandı: " + SERVER_IP + ":" + SERVER_PORT);
            System.out.println(in.readLine());
            out.println(consoleInput.readLine()); // Kullanıcı adını alır ve sunucuya gönderir

            // Sunucudan gelen mesajları dinleyen ayrı bir thread oluşturulur
            Thread listener = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            listener.start(); // Dinleyici thread başlatılır

            // Kullanıcıdan gelen girişleri sunucuya göndermek için bir döngü
            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                out.println(userInput); // Kullanıcının girdiği mesajı sunucuya gönderir
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
