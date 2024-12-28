package ChatServer;

import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_IP = "192.168.1.36"; // Sunucunun yerel IP adresini buraya yazın
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Bağlandı: " + SERVER_IP + ":" + SERVER_PORT);
            System.out.println(in.readLine()); // Kullanıcı adı istemi
            out.println(consoleInput.readLine()); // Kullanıcı adını gönder

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
            listener.start();

            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                out.println(userInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
