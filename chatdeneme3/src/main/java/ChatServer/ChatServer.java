package ChatServer;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345; // Sunucu portu
    private static Map<String, PrintWriter> clientWriters = new HashMap<>(); // Kullanıcı adı -> PrintWriter

    public static void main(String[] args) {
        try {
            // Yerel IP adresi alınır
            String localIp = InetAddress.getLocalHost().getHostAddress();
            System.out.println("Sunucu başlatıldı: " + localIp + ":" + PORT);

            // Sunucu belirtilen IP ve port üzerinde başlatılır
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Bağlantılar bekleniyor...");

            // İstemciler kabul ediliyor
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String userName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                // Giriş ve çıkış akışları oluşturulur
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Kullanıcı adı alınır
                synchronized (clientWriters) {
                    while (true) {
                        out.println("Lütfen kullanıcı adınızı giriniz:");
                        userName = in.readLine();

                        if (userName == null || userName.trim().isEmpty()) {
                            out.println("Geçersiz kullanıcı adı. Tekrar deneyin.");
                        } else if (clientWriters.containsKey(userName)) {
                            out.println("Bu kullanıcı adı zaten kullanımda. Başka bir isim seçin.");
                        } else {
                            clientWriters.put(userName, out);
                            out.println("Kullanıcı adı kabul edildi: " + userName);
                            break;
                        }
                    }
                }

                System.out.println(userName + " bağlandı.");
                broadcastMessage("Server: " + userName + " sohbete katıldı.");

                // Gelen mesajları dinler
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("@")) {
                        // Özel mesaj gönderme
                        String[] parts = message.split(" ", 2);
                        if (parts.length < 2) {
                            out.println("Hatalı mesaj formatı. Örnek: @kullanıcı_adi mesaj");
                            continue;
                        }
                        String targetUser = parts[0].substring(1);
                        String msgText = parts[1];

                        if (clientWriters.containsKey(targetUser)) {
                            clientWriters.get(targetUser).println(userName + " (özel): " + msgText);
                        } else {
                            out.println("Kullanıcı bulunamadı: " + targetUser);
                        }
                    } else {
                        // Tüm kullanıcılara mesaj gönderme
                        broadcastMessage(userName + ": " + message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Kullanıcı ayrıldığında temizleme işlemleri
                if (userName != null) {
                    synchronized (clientWriters) {
                        clientWriters.remove(userName);
                    }
                    broadcastMessage("Server: " + userName + " sohbetten ayrıldı.");
                    System.out.println(userName + " bağlantıyı kapattı.");
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void broadcastMessage(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters.values()) {
                    writer.println(message);
                }
            }
        }
    }
}
